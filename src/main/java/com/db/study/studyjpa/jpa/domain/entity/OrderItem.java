package com.db.study.studyjpa.jpa.domain.entity;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Getter
@Table(name = "order_item")
@NoArgsConstructor
@ToString
public class OrderItem {


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_item_seq")
    @SequenceGenerator(name = "order_item_seq", sequenceName = "SEQ_ORDER_ITEM", allocationSize = 1)
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    @ToString.Exclude
    private Item item;      // 주문 상품

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    @ToString.Exclude
    private Order order;    // 주문

    private int orderPrice;  // 주문 가격

    private int count;    // 주문 수량


    public void updateOrder(Order order) {
        this.order = order;
    }

    @Builder
    public OrderItem(Item item, int orderPrice, int count) {
        this.item = item;
        this.orderPrice = orderPrice;
        this.count = count;
    }

    //==생성 메서드==//
    public static OrderItem createOrderItem(Item item, int orderPrice, int
            count) {
        OrderItem orderItem = OrderItem
                .builder()
                .item(item)
                .orderPrice(orderPrice)
                .count(count)
                .build();
        item.removeStock(count);

        return orderItem;
    }

}
