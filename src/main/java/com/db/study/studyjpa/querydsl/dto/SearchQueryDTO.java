package com.db.study.studyjpa.querydsl.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchQueryDTO {

    private String name;
    private Integer minPrice;
    private Integer maxPrice;
    private Long categoryId;
}