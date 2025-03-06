package com.db.study.studyjpa.jpa.domain.entity;

import com.db.study.studyjpa.jpa.domain.enums.OrderStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "orders")
@NoArgsConstructor
@ToString
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_seq")
    @SequenceGenerator(name = "order_seq", sequenceName = "SEQ_ORDER", allocationSize = 1)
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;      // 주문 회원

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Delivery delivery;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime orderDate;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    // 편의메서드
    public void updateOrderMember(Member member) {
        this.member = member;
        member.getOrders().add(this);
    }

    public void updateOrderOrderItem(OrderItem orderItem) {
        this.orderItems.add(orderItem);
        orderItem.updateOrder(this);
    }

    public void updateOrderDelivery(Delivery delivery) {
        this.delivery = delivery;
        delivery.updateDelivery(this);
    }

    @Builder
    public Order(Member member, OrderStatus status) {
        this.member = member;
        this.status = status;
    }

    @PrePersist
    public void prePersist() {
        this.status = OrderStatus.ORDER;
        this.orderDate = LocalDateTime.now();
    }

    //==생성 메서드==//
    public static Order createOrder(Member member, Delivery delivery,
                                    OrderItem... orderItems) {
        Order order = new Order();
        order.updateOrderMember(member);
        order.updateOrderDelivery(delivery);
        for (OrderItem orderItem : orderItems) {
            order.updateOrderOrderItem(orderItem);
        }
        return order;
    }

}
