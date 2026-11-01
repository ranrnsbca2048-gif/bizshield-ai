package com.bizshield.bizshield_ai.service;

import com.bizshield.bizshield_ai.model.Product;
import com.bizshield.bizshield_ai.model.Sale;
import com.bizshield.bizshield_ai.repository.ProductRepository;
import com.bizshield.bizshield_ai.repository.SaleRepository;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class BusinessInsightsService {

    private final ProductRepository productRepository;
    private final SaleRepository saleRepository;

    public BusinessInsightsService(
            ProductRepository productRepository,
            SaleRepository saleRepository) {
        this.productRepository = productRepository;
        this.saleRepository = saleRepository;
    }

    public List<String> generateInsights() {

        List<String> insights = new ArrayList<>();

        List<Product> products = productRepository.findAll();
        List<Sale> sales = saleRepository.findAll();

        BigDecimal totalRevenue = BigDecimal.ZERO;

        for (Sale sale : sales) {
            if (sale.getTotalAmount() != null) {
                totalRevenue = totalRevenue.add(sale.getTotalAmount());
            }
        }

        insights.add(
                "Sales Summary: " + sales.size()
                        + " sales recorded, with total revenue of ₹"
                        + totalRevenue);

        for (Product product : products) {

            Integer quantity = product.getQuantity();
            Integer minimumStock = product.getMinimumStock();

            if (quantity != null
                    && minimumStock != null
                    && quantity <= minimumStock) {

                insights.add(
                        "Restock Alert: " + product.getName()
                                + " has " + quantity
                                + " units remaining. Minimum stock level: "
                                + minimumStock + ".");
            }
        }

        if (products.isEmpty()) {
            insights.add(
                    "Business Tip: Add products to your inventory to begin tracking stock.");
        }

        if (sales.isEmpty()) {
            insights.add(
                    "Business Tip: Record sales to begin tracking business revenue.");
        }

        if (!products.isEmpty() && !sales.isEmpty()) {
            insights.add(
                    "Business Tip: Review your sales and stock levels regularly "
                            + "to plan replenishment.");
        }

        return insights;
    }
}