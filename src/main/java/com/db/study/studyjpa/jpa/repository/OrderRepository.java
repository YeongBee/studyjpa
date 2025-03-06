package com.db.study.studyjpa.jpa.repository;

import com.db.study.studyjpa.jpa.domain.entity.Order;
import com.db.study.studyjpa.jpa.dto.SearchOrderDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {



    @Query(value = "select new com.db.study.studyjpa.jpa.dto.SearchOrderDTO(" +
            "m.name, m.address, o.orderDate, o.status, " +
            "d.address, d.deliveryStatus, it.name, it.price, oi.count) " +
            "from Order o " +
            "join o.member m " +
            "join o.delivery d " +
            "join o.orderItems oi " +
            "join oi.item it " +
            "where o.id = :id")
    SearchOrderDTO findOrderQueryDtos(@Param("id") Long id);
}