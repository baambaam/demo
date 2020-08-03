package com.example.demo.service;

import com.example.demo.client.PartClient;
import com.example.demo.client.ProductClient;
import com.example.demo.model.Part;
import com.example.demo.model.Product;
import com.example.demo.dto.ProductDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductClient productClient;
    private final PartClient partClient;

    public Mono<ProductDto> getProductWithParts(Long productId) {
        return productClient.getProduct(productId)
                .flatMap(this::convertToProductDto);
    }

    private Mono<ProductDto> convertToProductDto(Product product) {

        List<Part> partList = product.getParts()
                .stream()
                .map(this::getPart)
                .map(Mono::block)
                .collect(Collectors.toList());

        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setName(product.getName());
        productDto.setParts(partList);

        return Mono.just(productDto);
    }

    private Mono<Part> getPart(Long partId) {
        return partClient.getPart(partId);
    }
}
