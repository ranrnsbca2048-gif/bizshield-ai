package com.bizshield.bizshield_ai.controller;

import com.bizshield.bizshield_ai.model.Product;
import com.bizshield.bizshield_ai.repository.ProductRepository;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductRepository productRepository;

    // Constructor
    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    // 1. Get all products
    @GetMapping
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // 2. Get all low-stock products
    @GetMapping("/low-stock")
    public List<Product> getLowStockProducts() {
        return productRepository.findLowStockProducts();
    }

    // 3. Get a product by ID
    @GetMapping("/{id:\\d+}")
    public Product getProductById(@PathVariable Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Product not found"
                ));
    }

    // 4. Add a new product
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Product addProduct(@RequestBody Product product) {
        product.setId(null);
        return productRepository.save(product);
    }

    // 5. Update an existing product
    @PutMapping("/{id}")
    public Product updateProduct(
            @PathVariable Long id,
            @RequestBody Product updatedProduct) {

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Product not found"
                ));

        product.setName(updatedProduct.getName());
        product.setCategory(updatedProduct.getCategory());
        product.setQuantity(updatedProduct.getQuantity());
        product.setPrice(updatedProduct.getPrice());
        product.setMinimumStock(updatedProduct.getMinimumStock());

        return productRepository.save(product);
    }

    // 6. Delete a product by ID
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable Long id) {

        if (!productRepository.existsById(id)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Product not found"
            );
        }

        productRepository.deleteById(id);
    }
}