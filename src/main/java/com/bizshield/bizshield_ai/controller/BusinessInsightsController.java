
package com.bizshield.bizshield_ai.controller;

import com.bizshield.bizshield_ai.service.BusinessInsightsService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/business-insights")
public class BusinessInsightsController {

    private final BusinessInsightsService businessInsightsService;

    public BusinessInsightsController(
            BusinessInsightsService businessInsightsService) {
        this.businessInsightsService = businessInsightsService;
    }

    @GetMapping
    public List<String> getBusinessInsights() {
        return businessInsightsService.generateInsights();
    }
}