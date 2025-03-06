package com.db.study.studyjpa.jpa.domain.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@DiscriminatorValue("A")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Album extends Item {
    private String artist;
    private String etc;


}