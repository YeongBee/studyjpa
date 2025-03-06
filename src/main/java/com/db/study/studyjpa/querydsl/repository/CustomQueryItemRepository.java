package com.db.study.studyjpa.querydsl.repository;

import com.db.study.studyjpa.jpa.domain.entity.Category;
import com.db.study.studyjpa.jpa.domain.entity.Item;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface CustomQueryItemRepository{

    List<Item> searchItem(String name, Integer minPrice, Integer maxPrice, Category category);
}
