package com.db.study.studyjpa.jpa.service;

import com.db.study.studyjpa.jpa.domain.entity.*;
import com.db.study.studyjpa.jpa.dto.CategoryDto;
import com.db.study.studyjpa.jpa.dto.MemberSaveDTO;
import com.db.study.studyjpa.jpa.dto.SearchOrderDTO;
import com.db.study.studyjpa.jpa.repository.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Or;
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

    //멤버 찾기
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

    @Transactional(readOnly = true)
    public List<Item> findAllItems() {
        return itemRepository.findAll();
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

    // DTO로 찾기
    @Transactional(readOnly = true)
    public SearchOrderDTO findSearchOrderDTO(Long id) {
        return orderRepository.findOrderQueryDtos(7L);
    }


    // ManyTOMany 관계에서 제거
    // 카테고리가 삭제되어도 상품은 삭제되면 안되므로 양방향으로 연관관계 제거 후 상품 삭제
    @Transactional
    public void deleteItem(Long id) {

        Item byId = itemRepository.findById(id)
                .orElseThrow( () -> new EntityNotFoundException(" 값이 없음"));

        for (Category category : byId.getCategories()) {
            category.getItems().remove(byId); // 각 Category에서 Item 제거
        }
        byId.getCategories().clear(); // Item에서도 Category 리스트 비우기

        itemRepository.delete(byId);
    }

    // 자식 부모 카테고리가 없는 것을 가져오기 , 대 카테고리
    @Transactional(readOnly = true)
    public List<Category> parentCategories() {
        return categoryRepository.findByParentIsNull();
    }

    // 카테고리 찾기
    @Transactional(readOnly = true)
    public Category findCategoryById(Long id) {
        return categoryRepository.findById(id).orElse(null);
    }

    // 아이템 삭제
    @Transactional
    public void deleteItemById(Long id) {
        itemRepository.deleteById(id);
    }

    // 카테고리 삭제
    @Transactional
    public void deleteCategoryById(Long id) {
        categoryRepository.deleteById(id);
    }

    // 멤버 삭제
    @Transactional
    public void deleteMemberById(Long id) {
        memberRepository.deleteById(id);
    }

    // 주문삭제
    @Transactional
    public void deleteOrderById(Long id) {
        orderRepository.deleteById(id);
    }

    //주문 찾가
    @Transactional(readOnly = true)
    public Order findByOrderId(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("값이없음"));

    }
}

