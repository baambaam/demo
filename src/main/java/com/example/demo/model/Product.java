package com.example.demo.model;

import lombok.Data;

import java.util.List;

@Data
public class Product {
    private Long id;
    private List<Long> parts;
    private String name;
    private List<File> files;
}
