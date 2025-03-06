package com.db.study.studyjpa.jpa.dto;

import com.db.study.studyjpa.jpa.domain.entity.Address;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberSaveDTO {

    private String name;
    private Address address;

}
