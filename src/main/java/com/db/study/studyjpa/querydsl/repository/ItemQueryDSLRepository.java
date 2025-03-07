package com.db.study.studyjpa.querydsl.repository;

import com.db.study.studyjpa.jpa.domain.entity.Item;
import com.db.study.studyjpa.jpa.domain.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemQueryDSLRepository extends JpaRepository<Item, Long>, CustomQueryItemRepository {

}
