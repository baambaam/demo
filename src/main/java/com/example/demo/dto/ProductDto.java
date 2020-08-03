package com.example.demo.dto;

import com.example.demo.model.Part;
import lombok.Data;

import java.util.List;

@Data
public class ProductDto {
    private Long id;
    private String name;
    private List<Part> parts;
}
