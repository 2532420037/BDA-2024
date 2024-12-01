package com.example.entity.paper;

import lombok.Data;

@Data
public class Paper {
    public int id;
    String title;
    String abstracts;
    String category;
    int year;
}