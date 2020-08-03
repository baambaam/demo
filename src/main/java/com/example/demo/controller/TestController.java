package com.example.demo.controller;

import com.example.demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class TestController {
    private final ProductService productService;

    @Autowired
    public TestController(
            ProductService productService
    ) {
        this.productService = productService;
    }

    @GetMapping("product/{productId}")
    public Mono getAdjustmentFile(@PathVariable Long productId) {
        return productService.getProductWithParts(productId);
    }
}
