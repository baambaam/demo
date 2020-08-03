package com.example.demo.model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class File {
    private LocalDate date;
    private String name;
}
