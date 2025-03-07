package com.db.study.studyjpa.jpa.domain.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "category")
@NoArgsConstructor
@ToString
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "category_seq")
    @SequenceGenerator(name = "category_seq", sequenceName = "SEQ_CATEGORY", allocationSize = 1)
    @Column(name = "category_id")
    private Long id;

    private String name;

    @ManyToMany
    @JoinTable(name = "category_item",
            joinColumns = @JoinColumn(name = "category_id"),
            inverseJoinColumns = @JoinColumn(name = "item_id"))
    @ToString.Exclude
    @JsonIgnoreProperties(value = "categories")
    private List<Item> items = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    @ToString.Exclude
    @JsonBackReference
//    @JsonIgnoreProperties({"child", "items", "parent"})
    private Category parent;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
    @JsonManagedReference
//    @JsonIgnoreProperties("parent")
    private List<Category> child = new ArrayList<>();

    public void updateCategoryParent(Category category) {
        this.parent = category;
    }

    // 연관관계 편의 메서드
    public void addChildCategory(Category child) {
        this.child.add(child);
        child.updateCategoryParent(this);
    }


    public Category(String name) {
        this.name = name;
    }

    public void addItem(Item item) {
        // 중복 추가 방지
        if (!this.items.contains(item)) {
            this.items.add(item);
        }
        // 양방향 관계 설정: item에도 현재 Category가 포함되어 있는지 확인 후 추가
        if (!item.getCategories().contains(this)) {
            item.getCategories().add(this);
        }
    }

    @Builder
    public Category(String name, List<Item> items, Category parent) {
        this.name = name;
        this.items = items;
        this.parent = parent;
    }
}

