package com.db.study.studyjpa.jpa.controller;

import com.db.study.studyjpa.jpa.domain.entity.Category;
import com.db.study.studyjpa.jpa.dto.CategoryDto;
import com.db.study.studyjpa.jpa.dto.SearchOrderDTO;
import com.db.study.studyjpa.jpa.service.ShopService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/test")
@RequiredArgsConstructor
@Slf4j
public class ShopController {

    private final ShopService shopService;

    @ResponseBody
    @PostMapping("/category/add")
    public String categorySave(@RequestBody CategoryDto categoryDto) {
        Category category = shopService.createCategory(categoryDto);
        return category.toString();
    }

    @ResponseBody
    @GetMapping("/order/dto")
    public ResponseEntity<SearchOrderDTO> findSearchOrderDTO() {
        SearchOrderDTO orderDTO = shopService.findSearchOrderDTO(7L);

        return ResponseEntity.ok(orderDTO);
    }
}
