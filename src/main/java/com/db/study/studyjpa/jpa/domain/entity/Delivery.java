package com.db.study.studyjpa.jpa.domain.entity;

import com.db.study.studyjpa.jpa.domain.enums.DeliveryStatus;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Table(name = "delivery")
@NoArgsConstructor
@ToString
public class Delivery {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "delivery_seq")
    @SequenceGenerator(name = "delivery_seq", sequenceName = "SEQ_DELIVERY", allocationSize = 1)
    @Column(name = "delivery_id")
    private Long id;

    @OneToOne(mappedBy = "delivery", fetch = FetchType.LAZY)
    @ToString.Exclude
    private Order order;

    @Embedded
    private Address address;

    @Enumerated
    private DeliveryStatus deliveryStatus;

    public void setOrder(Order order) {
        this.order = order;
    }

    public void updateDelivery(Order order) {
        this.order = order;
    }

    @Builder
    public Delivery(Address address, DeliveryStatus deliveryStatus) {
        this.address = address;
        this.deliveryStatus = deliveryStatus;
    }
}
