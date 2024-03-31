package com.tyranno.ssg.order.application;


import com.tyranno.ssg.order.domain.Order;
import com.tyranno.ssg.order.domain.OrderList;
import com.tyranno.ssg.order.dto.OrderAddDto;
import com.tyranno.ssg.order.infrastructure.OrderRepository;
import java.util.Random;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderServiceImp implements OrderService {

    private final OrderRepository orderRepository;

    @Override
    public void addOrder(OrderAddDto orderAddDto, String uuid) {
        // 1. orderList에 임의의 주문번호를 넣어줌
        OrderList orderList = orderAddDto.toEntity(uuid, orderAddDto, "99");
        // 2. 주문번호 생성 ( 짜침 )
        String createdOrderNumber = createOrderNumber(orderList, uuid);
        // 3. 생성된 주문번호를 넣어줘서 orderList 완성
        orderList = orderAddDto.toEntity(uuid, orderAddDto, createdOrderNumber);
        // 4. 저장된 orderList Id (PK) 값을 가져오기 위해서 저장
        OrderList savedOrderList = orderRepository.save(orderList);

        /** 주문리스트 번호와 옵션아이디 넣어준다
         *  개수와 가격도 넣어준다 stream으로
         */




    }

    private String createOrderNumber(OrderList orderList, String uuid) {
        //날짜 + uuid + 랜덤 숫자
        String orderUuidNumber = uuid.substring(uuid.length() - 6);
        int randomNumber = new Random().nextInt(9000) + 1000; //
        return orderList.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) +
                "-" +orderUuidNumber + "-" + randomNumber;
    }
}
