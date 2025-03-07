package com.db.study.studyjpa.querydsl.repository;

import com.db.study.studyjpa.jpa.domain.entity.Category;
import com.db.study.studyjpa.jpa.domain.entity.Item;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;

import java.util.List;

import static com.db.study.studyjpa.jpa.domain.entity.QItem.item;


/**
 * QueryDSL에서는 where문제 값이 NULL이 들어가면 무시된다.
 */


@RequiredArgsConstructor
public class CustomQueryItemRepositoryImpl implements CustomQueryItemRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Item> searchItem(String name, Integer minPrice, Integer maxPrice, Category category) {
        return queryFactory
                .selectFrom(item)
                .where(nameContains(name),
                        priceBetween(minPrice, maxPrice),
                        categoryEq(category)
                )
                .fetch();
    }


    private BooleanExpression nameContains(String name) {
        return StringUtils.hasText(name) ? item.name.contains(name) : null;
    }

    private BooleanExpression priceGoe(Integer minPriceP) {
        return minPriceP != null ? item.price.goe(minPriceP) : null;
    }

    private BooleanExpression priceLoe(Integer maxPrice) {
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


    private BooleanExpression categoryEq(Category category) {
        return category != null
                ? item.categories.any().id.eq(category.getId()) // Category id를 비교
                : null;
    }
}
