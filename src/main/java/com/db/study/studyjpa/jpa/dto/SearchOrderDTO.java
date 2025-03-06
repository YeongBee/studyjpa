package com.db.study.studyjpa.jpa.dto;

import com.db.study.studyjpa.jpa.domain.entity.Address;
import com.db.study.studyjpa.jpa.domain.entity.Item;
import com.db.study.studyjpa.jpa.domain.enums.DeliveryStatus;
import com.db.study.studyjpa.jpa.domain.enums.OrderStatus;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 Error : java.lang.IllegalStateException: Cannot resolve parameter names for constructor cat.community.NyangMunity.board.request.BoardListRequest(java.lang.Integer,java.lang.Integer)
 에러 내용은 생성자의 매개변수 이름을 해석할 수가 없다는 뜻
 Spring boot 3.2.0 부터는 Spring Framework 6.1을 사용하게 되었다.

 LocalVariableTableParameterNameDiscoverer 를 더 이상 사용하지 않게 되었고 이로 인해 발생할 수 있는 매개 변수 관련 이슈는 -parameter 옵션을 Compile 시에 활성화 시켜야 된다고 한다.

 또는 DTO에 Setter를 기반으로 생성

 IntelliJ IDEA

 Build, Execution, Deployment → Compiler → Java Compiler → Additional command line parameters
 -parameters 추가
 build.gradle에 아래 내용 추가
 tasks.withType(JavaCompile) {
 options.compilerArgs << "-parameters"
 }



 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SearchOrderDTO {

    private String memberName;        // Member의 이름
    private Address memberAddress;     // Member의 주소
    private LocalDateTime orderDate;  // 주문 날짜
    private OrderStatus orderStatus;  // 주문 상태
    private Address deliveryAddress;   // 배송 주소 (Delivery의 주소)
    private DeliveryStatus deliveryStatus; // 배송 상태 (Delivery의 상태)
    private String itemName;       // 상품명 (OrderItem 에서 참조하는 Item의 이름)
    private int productPrice;         // 상품 가격 (Item의 가격)
    private int productQuantity;      // 상품 수량 (OrderItem의 수량)

}
