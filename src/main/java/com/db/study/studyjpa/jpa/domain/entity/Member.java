package com.db.study.studyjpa.jpa.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "member")
@NoArgsConstructor
@ToString
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "member_seq")
    @SequenceGenerator(name = "member_seq", sequenceName = "SEQ_MEMBER", allocationSize = 1)
    @Column(name = "member_id")
    private Long id;

    private String name;

    @Embedded
    private Address address;

    @OneToMany(mappedBy = "member")
    @ToString.Exclude
    private List<Order> orders = new ArrayList<>();

    public void updateOrders(Order order){
        this.orders.add(order);
        order.updateOrderMember(this);
    }

    @Builder
    public Member(String name, Address address) {
        this.name = name;
        this.address = address;
    }
}
