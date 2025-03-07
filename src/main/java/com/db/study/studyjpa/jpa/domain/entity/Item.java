package com.db.study.studyjpa.jpa.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "item")
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
@ToString
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "item_seq")
    @SequenceGenerator(name = "item_seq", sequenceName = "SEQ_ITEM", allocationSize = 1)
    @Column(name = "item_id")
    private Long id;

    private String name;    // 상품이름
    private int price;      // 상품가격
    private int stockQuantity;  // 상품 수량

    @ManyToMany(mappedBy = "items")
    @JsonIgnoreProperties(value = "items")
    private List<Category> categories = new ArrayList<>();

    public void addCategory(Category category) {
        // 현재 item의 categories에 category가 없으면 추가
        if (!this.categories.contains(category)) {
            this.categories.add(category);
        }
        // category의 items 컬렉션에도 현재 item을 추가하여 양쪽 관계 성립
        if (!category.getItems().contains(this)) {
            category.getItems().add(this);
        }
    }

    @Builder
    public Item(String name, int price, int stockQuantity) {
        this.name = name;
        this.price = price;
        this.stockQuantity = stockQuantity;
    }

    public void addStock(int quantity) {
        this.stockQuantity += quantity;
    }

    public void removeStock(int quantity) {
        int restStock = this.stockQuantity - quantity;
        if (restStock < 0) {
            throw new RuntimeException("need more stock");
        }
        this.stockQuantity = restStock;
    }

}
