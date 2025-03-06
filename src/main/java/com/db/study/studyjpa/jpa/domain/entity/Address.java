package com.db.study.studyjpa.jpa.domain.entity;


import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Address {

    private String city;
    private String street;
    private String zipCode;
}
