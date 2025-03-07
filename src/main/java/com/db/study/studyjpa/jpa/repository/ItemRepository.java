package com.db.study.studyjpa.jpa.repository;

import com.db.study.studyjpa.jpa.domain.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {


}