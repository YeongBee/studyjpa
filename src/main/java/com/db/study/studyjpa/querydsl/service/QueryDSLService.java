package com.db.study.studyjpa.querydsl.service;

import com.db.study.studyjpa.jpa.domain.entity.Category;
import com.db.study.studyjpa.jpa.domain.entity.Item;
import com.db.study.studyjpa.querydsl.repository.ItemQueryDSLRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.db.study.studyjpa.jpa.domain.entity.QItem.item;

@Service
@RequiredArgsConstructor
public class QueryDSLService {

    private final ItemQueryDSLRepository itemQueryDSLRepository;


    // QueryDSL 동적쿼리
    public List<Item> searchItem(String name, Integer minPrice, Integer maxPrice, Category category) {
        return itemQueryDSLRepository.searchItem(name, minPrice, maxPrice, category);
    }
}
