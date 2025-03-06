package com.db.study.studyjpa.jpa.service;

import com.db.study.studyjpa.jpa.domain.entity.*;
import com.db.study.studyjpa.jpa.dto.CategoryDto;
import com.db.study.studyjpa.jpa.dto.MemberSaveDTO;
import com.db.study.studyjpa.jpa.dto.SearchOrderDTO;
import com.db.study.studyjpa.jpa.repository.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.jaxb.SpringDataJaxb;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ShopServiceImpl implements ShopService {

    private final MemberRepository memberRepository;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final DeliveryRepository deliveryRepository;
    private final ItemRepository itemRepository;
    private final CategoryRepository categoryRepository;


    //멤버 저장
    @Transactional
    public Member memberSave(MemberSaveDTO memberSaveDTO) {
        Member member = Member
                .builder()
                .name(memberSaveDTO.getName())
                .address(memberSaveDTO.getAddress())
                .build();
        return memberRepository.save(member);
    }

    public Member findByMemberOne(Long id) {
        return memberRepository.findById(id)
                .orElseThrow( () -> new EntityNotFoundException("값이 없음"));
    }

    // 카테고리 생성
    @Transactional
    public Category createCategory(CategoryDto categoryDto) {
        Category rootCategory = convertToEntity(categoryDto);
        return categoryRepository.save(rootCategory);
    }

    // 카테고리에 자식 카테고리 추가
    @Transactional
    public Category addChildCategory(CategoryDto categoryDto, Long id) {
        Category category = convertToEntity(categoryDto);
        Category findNameCategory = categoryRepository.findById(id)
                .orElseThrow( () -> new EntityNotFoundException("값이 없음"));
        findNameCategory.addChildCategory(category);

        return categoryRepository.findById(id)
                .orElseThrow( () -> new EntityNotFoundException("값이 없음"));
    }

    // DTO → Entity 변환
    private Category convertToEntity(CategoryDto dto) {
        Category entity = new Category(dto.getName());

        if (dto.getChildren() != null && !dto.getChildren().isEmpty()) {
            for (CategoryDto childDto : dto.getChildren()) {
                Category childEntity = convertToEntity(childDto);
                // 연관관계 편의 메서드를 통해 자식 엔티티와 부모 관계를 설정
                entity.addChildCategory(childEntity);
            }
        }
        return entity;
    }

    // 상품 저장
    @Transactional
    public Item itemSave(Item item) {
        return itemRepository.save(item);
    }

    // 상품 하나 찾기
    public Item findItemById(Long id) {
        return itemRepository.findById(id)
                .orElseThrow( () -> new EntityNotFoundException("값이 없음"));
    }

    // 주문 저장
    @Transactional
    public Order orderSave(Order order) {
        return orderRepository.save(order);
    }

    // 주문 찾기
    @Transactional
    public Order findOrderById(Long id) {
        return orderRepository.findById(id)
                .orElse(null);
    }

    @Transactional(readOnly = true)
    public SearchOrderDTO findSearchOrderDTO(Long id) {
        return orderRepository.findOrderQueryDtos(7L);
    }


    @Transactional
    public void deleteItem(Long id) {

        Item byId = itemRepository.findById(id)
                .orElseThrow( () -> new EntityNotFoundException(" 값이 없음"));

        //given
        for (Category category : byId.getCategories()) {
            category.getItems().remove(byId); // 각 Category에서 Item 제거
        }
        byId.getCategories().clear(); // Item에서도 Category 리스트 비우기

        itemRepository.delete(byId);
    }

}

