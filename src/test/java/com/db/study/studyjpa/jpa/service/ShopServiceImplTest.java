package com.db.study.studyjpa.jpa.service;

import com.db.study.studyjpa.jpa.domain.entity.*;
import com.db.study.studyjpa.jpa.domain.entity.Order;
import com.db.study.studyjpa.jpa.domain.enums.DeliveryStatus;
import com.db.study.studyjpa.jpa.domain.enums.OrderStatus;
import com.db.study.studyjpa.jpa.dto.CategoryDto;
import com.db.study.studyjpa.jpa.dto.MemberSaveDTO;
import com.db.study.studyjpa.jpa.repository.CategoryRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Slf4j
//@Rollback(value = false)
class ShopServiceImplTest {

    @Autowired
    private ShopServiceImpl shopService;

    @Autowired
    private CategoryRepository categoryRepository;

    @BeforeEach
    void beforeInit() {
        log.info("모든 테스트 시작 전에 실행되는 코드 입니다.");
    }

    @AfterEach
    void afterInit() {
        log.info("모든 테스트가 끝난 후에 실행되는 코드 입니다.");
    }


    @DisplayName("멤버 추가")
    @Test
    void memberSave() {
        //given
        Address address = new Address("SnugNamSi", "GuMiDong", "123-34-54");
        MemberSaveDTO memberSaveDTO = new MemberSaveDTO("user1", address);

        //when
        Member memberGet = shopService.memberSave(memberSaveDTO);
        Member findMember = shopService.findByMemberOne(memberGet.getId());

        //then
        assertEquals(memberSaveDTO.getName(), findMember.getName());

    }

    @DisplayName("카테고리 추가")
    @Test
    void categorySave() {
//        CategoryDto categoryDto = new CategoryDto("그래픽카드");
//        // 컴퓨터 카테고리에 그래픽카드 목록 추가
//        Category category = shopService.addChildCategory(categoryDto, 3L);
//
        CategoryDto categoryDto = new CategoryDto("RTX5080");
        Category categoryValue = categoryRepository.findByName("그래픽카드")
                .orElse(null);
        // 컴퓨터 카테고리에 그래픽카드 목록 추가
        Category category = shopService.addChildCategory(categoryDto, categoryValue.getId());

        log.info("categoryDto: {}", categoryDto);
    }

    @DisplayName("카테고리 목록 출력 조회")
    @Test
        // 카테고리 순환 조회를 통해 자식의 값을 모두 출력
    void categoryFindAll() {
        System.out.println("----------------------------------------------");
        Category category1 = categoryRepository.findById(2L).get();
        category1.getChild().size();

        log.info("category1={}", category1);
        Category category2 = categoryRepository.findById(3L).get();
//        category2.getChild().size();
        log.info("category2={}", category2);
        Category category3 = categoryRepository.findById(4L).get();
        category3.getChild().size();
        log.info("category3={}", category3);
        Category category4 = categoryRepository.findById(5L).get();
        category4.getChild().size();
        log.info("category4={}", category4);
        Category category5 = categoryRepository.findById(6L).get();
        category5.getChild().size();
        log.info("category5={}", category5);
        System.out.println("----------------------------------------------");

    }


    @DisplayName("상품추가")
    @Test
    void itemSave() {
        // Given: 테스트에 필요한 객체를 준비하고 초기 상태를 설정
        // 새 Item 생성
        Item item = new Item("ASUS RTX5090", 3400000, 10);
        // "RTX5090" 카테고리를 조회하여 연관관계 설정 (양방향 관계)
        Category rtx5090Category = categoryRepository.findByName("RTX5090").get();
        rtx5090Category.addItem(item);

        // When: 실제 동작을 수행
        // 아이템을 저장하고, 저장된 아이템을 조회
        Item itemSave = shopService.itemSave(item);
        Item findItem = shopService.findItemById(itemSave.getId());

        // Then: 동작 결과가 예상과 일치하는지 검증
        log.info("----------------------------------------------");
        log.info("itemSave={}", itemSave);
        log.info("findItem={}", findItem);
        log.info("----------------------------------------------");

        assertEquals(itemSave.getName(), findItem.getName());
    }

    @DisplayName("주문 추가")
    @Test
    void orderSave() {
        //given
        Address address = new Address("Seoul", "GangNam", "42304-543");
        MemberSaveDTO memberSaveDTO = new MemberSaveDTO("user2", address);
        Member memberGet = shopService.memberSave(memberSaveDTO);
        log.info("member={}", memberGet);

        // 상품 하나 가져오기
        Item item = shopService.findItemById(13L);
        log.info("item={}", item);

        // 배송 추가
        Delivery delivery = new Delivery(memberGet.getAddress(), DeliveryStatus.READY);
        log.info("delivery={}", delivery);

        // 주문 상품 생성
        int count = 2;
        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);
        log.info("orderItem={}", orderItem);

        // 주문 생성
        Order order = Order.createOrder(memberGet, delivery, orderItem);
        log.info("order={}", order);


        //when
        Order orderSave = shopService.orderSave(order);
        log.info("orderSave={}", orderSave);
        Order findOrder = shopService.findOrderById(orderSave.getId());
        log.info("findOrder={}", findOrder);

        //then
        assertEquals(orderSave.getDelivery(), findOrder.getDelivery());

    }

    // QueryDSL 동적쿼리용
    @DisplayName("아이템 & 카테고리 추가")
    @Test
    @Disabled
    void addItems() {
        //given
        // 대 카테고리, 편의상 "대" 만으로 분리
        shopService.createCategory(new CategoryDto("의류"));
        shopService.createCategory(new CategoryDto("이불"));
        shopService.createCategory(new CategoryDto("주방용품"));
        shopService.createCategory(new CategoryDto("악세서리"));
        shopService.createCategory(new CategoryDto("스포츠"));

        Category clothesCategory = categoryRepository.findByName("의류").get();
        Item clothes1 = new Item("Classic White Shirt", 30000, 50);
        clothesCategory.addItem(clothes1);
        shopService.itemSave(clothes1);

        Item clothes2 = new Item("Casual Denim Jeans", 50000, 30);
        clothesCategory.addItem(clothes2);
        shopService.itemSave(clothes2);

        Item clothes3 = new Item("Summer Hoodie", 40000, 20);
        clothesCategory.addItem(clothes3);
        shopService.itemSave(clothes3);


        // 이불 카테고리 상품 추가 (2개)
        Category blanketCategory = categoryRepository.findByName("이불").get();
        Item blanket1 = new Item("Luxury Down Blanket", 150000, 15);
        blanketCategory.addItem(blanket1);
        shopService.itemSave(blanket1);

        Item blanket2 = new Item("Microfiber Blanket", 80000, 40);
        blanketCategory.addItem(blanket2);
        shopService.itemSave(blanket2);


        // 주방용품 카테고리 상품 추가 (3개)
        Category kitchenCategory = categoryRepository.findByName("주방용품").get();
        Item kitchen1 = new Item("Stainless Steel Mixing Bowl Set", 60000, 25);
        kitchenCategory.addItem(kitchen1);
        shopService.itemSave(kitchen1);

        Item kitchen2 = new Item("Wooden Cutting Board", 25000, 50);
        kitchenCategory.addItem(kitchen2);
        shopService.itemSave(kitchen2);

        Item kitchen3 = new Item("Non-stick Frying Pan", 45000, 20);
        kitchenCategory.addItem(kitchen3);
        shopService.itemSave(kitchen3);


        // 악세서리 카테고리 상품 추가 (2개)
        Category accessoryCategory = categoryRepository.findByName("악세서리").get();
        Item accessory1 = new Item("Elegant Necklace", 75000, 30);
        accessoryCategory.addItem(accessory1);
        shopService.itemSave(accessory1);

        Item accessory2 = new Item("Silver Earrings", 55000, 20);
        accessoryCategory.addItem(accessory2);
        shopService.itemSave(accessory2);


        // 스포츠 카테고리 상품 추가 (3개)
        Category sportsCategory = categoryRepository.findByName("스포츠").get();
        Item sports1 = new Item("Running Shoes", 120000, 15);
        sportsCategory.addItem(sports1);
        shopService.itemSave(sports1);

        Item sports2 = new Item("Fitness Tracker", 90000, 20);
        sportsCategory.addItem(sports2);
        shopService.itemSave(sports2);

        Item sports3 = new Item("Yoga Mat", 30000, 50);
        sportsCategory.addItem(sports3);
        shopService.itemSave(sports3);

        //when
        // 주문 추가

        Address address = new Address("Seoul222", "GangNam222", "42304-543");
        MemberSaveDTO memberSaveDTO = new MemberSaveDTO("user333", address);
        Member memberGet = shopService.memberSave(memberSaveDTO);
        log.info("member={}", memberGet);

        // 상품 하나 가져오기
        Item item = shopService.findItemById(27L);
        log.info("item={}", item);

        // 배송 추가
        Delivery delivery = new Delivery(memberGet.getAddress(), DeliveryStatus.READY);
        log.info("delivery={}", delivery);

        // 주문 상품 생성
        int count = 2;
        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);
        log.info("orderItem={}", orderItem);

        // 주문 생성
        Order order = Order.createOrder(memberGet, delivery, orderItem);
        log.info("order={}", order);


        //when
        Order orderSave = shopService.orderSave(order);
        log.info("orderSave={}", orderSave);
        Order findOrder = shopService.findOrderById(orderSave.getId());
        log.info("findOrder={}", findOrder);
        //then

    }

    // 상품삭제는 상식적으로 말이 안된다.
    // 상품은 판매중 상품, 판매중지된 상품 이렇게 상태코드로 관리하는게 좋아보인다.
    @DisplayName("연관관계 삭제 후 아이템 삭제")
    @Test
    void deleteItem(){

        //given
        Long itemId = 28L;

        Item itemById = shopService.findItemById(itemId);
        log.info("itemById={}", itemById);

        //when
        shopService.deleteItem(itemId);

        //then
        assertThrows(EntityNotFoundException.class, () -> shopService.findItemById(itemId));

    }




}