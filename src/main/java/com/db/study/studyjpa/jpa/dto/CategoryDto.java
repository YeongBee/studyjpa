package com.db.study.studyjpa.jpa.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryDto {
    private String name;
    private List<CategoryDto> children = new ArrayList<>();

    public CategoryDto(String name) {
        this.name = name;
    }
}