package com.db.study.studyjpa.querydsl.repository;

import com.db.study.studyjpa.jpa.domain.entity.Category;
import com.db.study.studyjpa.jpa.domain.entity.Item;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;

import java.util.List;

@RequiredArgsConstructor
public class CustomQueryItemRepositoryImpl implements CustomQueryItemRepository{

    private JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Item> searchItem(String name, Integer minPrice, Integer maxPrice, Category category) {
        return jpaQueryFactory
                .selectFrom()
    }


    private BooleanExpression nameContains(String name) {
        return StringUtils.hasText(name) ? item.name.contains(name) : null;
    }

    private BooleanExpression priceGoe(Integer minPriceP){
        return minPriceP != null ? item.price.goe(minPriceP) : null;
    }

    private BooleanExpression priceLoe(Integer maxPrice){
        return maxPrice != null ? item.price.loe(maxPrice) : null;
    }

    private BooleanExpression priceBetween(Integer minPrice, Integer maxPrice) {
        if (minPrice == null && maxPrice == null) {
            return null;
        }

        if (minPrice == null) {
            return item.price.loe(maxPrice);
        }

        if (maxPrice == null) {
            return item.price.goe(minPrice);
        }

        return item.price.between(minPrice, maxPrice);
    }


    private BooleanExpression categoryEq(item.Category category){
        return category != null ? item.category.eq(category) : null;
    }
}
