package com.bizshield.bizshield_ai.controller;

import com.bizshield.bizshield_ai.model.Product;
import com.bizshield.bizshield_ai.model.Sale;
import com.bizshield.bizshield_ai.repository.ProductRepository;
import com.bizshield.bizshield_ai.repository.SaleRepository;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/sales")
public class SaleController {

    private final SaleRepository saleRepository;
    private final ProductRepository productRepository;

    public SaleController(
            SaleRepository saleRepository,
            ProductRepository productRepository) {

        this.saleRepository = saleRepository;
        this.productRepository = productRepository;
    }


    // Get all sales
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'BUSINESS_USER', 'VIEWER')")
    public List<Sale> getAllSales() {

        return saleRepository.findAll();
    }


    // Get a sale by ID
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'BUSINESS_USER', 'VIEWER')")
    public Sale getSaleById(@PathVariable Long id) {

        return saleRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Sale not found"));
    }


    // Create a sale and update inventory
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'BUSINESS_USER')")
    @ResponseStatus(HttpStatus.CREATED)
    @Transactional
    public Sale createSale(@Valid @RequestBody Sale sale) {

        // 1. Validate product name
        if (sale.getProductName() == null
                || sale.getProductName().isBlank()) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Product name is required");
        }


        // 2. Validate quantity
        if (sale.getQuantity() == null
                || sale.getQuantity() <= 0) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Sale quantity must be greater than zero");
        }


        // 3. Validate unit price
        if (sale.getUnitPrice() == null
                || sale.getUnitPrice().compareTo(BigDecimal.ZERO) < 0) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Unit price cannot be negative or empty");
        }


        // 4. Find the product
        String productName = sale.getProductName().trim();

        Product product = productRepository.findAll()
                .stream()
                .filter(p -> p.getName() != null
                        && p.getName().equalsIgnoreCase(productName))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Product not found in inventory"));


        // 5. Validate current stock
        if (product.getQuantity() == null
                || product.getQuantity() < 0) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Product stock quantity is invalid");
        }


        // 6. Prevent overselling
        if (product.getQuantity() < sale.getQuantity()) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Insufficient stock. Available quantity: "
                            + product.getQuantity());
        }


        // 7. Calculate remaining stock
        int oldQuantity = product.getQuantity();
        int soldQuantity = sale.getQuantity();
        int newQuantity = oldQuantity - soldQuantity;


        // 8. Update inventory
        product.setQuantity(newQuantity);

        Product updatedProduct =
                productRepository.saveAndFlush(product);

        System.out.println("Stock before sale: " + oldQuantity);
        System.out.println("Quantity sold: " + soldQuantity);
        System.out.println(
                "Stock after sale: "
                        + updatedProduct.getQuantity());


        // 9. Calculate total amount
        sale.setProductName(product.getName());

        sale.setTotalAmount(
                sale.getUnitPrice().multiply(
                        BigDecimal.valueOf(
                                sale.getQuantity())));


        // 10. Save the sale
        return saleRepository.save(sale);
    }
}