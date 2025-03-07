package com.db.study.studyjpa.querydsl.controller;

import com.db.study.studyjpa.jpa.domain.entity.Category;
import com.db.study.studyjpa.jpa.domain.entity.Item;
import com.db.study.studyjpa.jpa.service.ShopServiceImpl;
import com.db.study.studyjpa.querydsl.dto.SearchQueryDTO;
import com.db.study.studyjpa.querydsl.service.QueryDSLService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class QueryController {

    private final ShopServiceImpl shopService;
    private final QueryDSLService queryDSLService;

    // 상품 검색 및 목록 조회 화면: /items
    @GetMapping("/items")
    public String searchItems( Model model) {

        List<Category> categories = shopService.parentCategories();
        List<Item> items = shopService.findAllItems();
        model.addAttribute("items", items);
        model.addAttribute("categories", categories);
        return "querydsl";
    }

    @PostMapping("/api/items")
    @ResponseBody
    public ResponseEntity<?> findItem(@RequestBody SearchQueryDTO searchQueryDTO) {

        String name = searchQueryDTO.getName();
        Integer minPrice = searchQueryDTO.getMinPrice();
        Integer maxPrice = searchQueryDTO.getMaxPrice();
        Long categoryId = searchQueryDTO.getCategoryId();

        Category category = null;
        // 검색조건으로 사용할 Category 객체 조회 (선택한 경우)
        if (categoryId != null) {
            category = shopService.findCategoryById(categoryId);
        }

        // 동적 쿼리를 이용해 검색 실행
        List<Item> items = new ArrayList<>();
        // 하나 이상의 검색 조건이 존재하면 검색 진행 (없으면 빈 목록)
        if (name != null || minPrice != null || maxPrice != null || category != null) {
            items = queryDSLService.searchItem(name, minPrice, maxPrice, category);
        }

        // 드롭다운에 사용할 전체 Category 목록
        List<Category> categories = shopService.parentCategories();

        return new ResponseEntity<>(items, HttpStatus.OK);
    }
}
