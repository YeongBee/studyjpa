package com.db.study.studyjpa.jpa.service;

import com.db.study.studyjpa.jpa.domain.entity.Category;
import com.db.study.studyjpa.jpa.domain.entity.Member;
import com.db.study.studyjpa.jpa.dto.CategoryDto;
import com.db.study.studyjpa.jpa.dto.MemberSaveDTO;
import com.db.study.studyjpa.jpa.dto.SearchOrderDTO;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ShopService {


    Member memberSave(MemberSaveDTO memberSaveDTO);

    Member findByMemberOne(Long id);

    Category createCategory(CategoryDto categoryDto);

    SearchOrderDTO findSearchOrderDTO(Long id);

}
