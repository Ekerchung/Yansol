package com.msystem.service;

import com.msystem.dto.OrderAddDto;
import com.msystem.dto.OrderUpdateDto;
import com.msystem.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

/**
 * @Description: Order的Service接口
 * @author: Eker
 * @date: 2023/4/25 上午 12:32
 * @version: V1.0
 */
public interface OrderService {
    List<Order> queryAllOrder();

    Optional<Order> queryOrderById(Integer id);
    ResponseEntity addOrder(OrderAddDto orderAddDto);

    Page<Order> queryOrderByPage(PageRequest pageable);

    Page<Order> findByGood_LineIdContainingOrEmployee_eNameContainingOrGood_Company_CompanyNameContaining(String lineId, String empName, String companyName, PageRequest pageable);
    Page<Order> findByGood_LineIdContainingOrEmployee_eNameContainingOrGood_Company_CompanyNameContainingAndComDateIsNull(String lineId, String empName, String companyName, PageRequest pageable);

    ResponseEntity deleteOrder(Integer id);

    ResponseEntity updateOrder(OrderUpdateDto orderUpdateDto);

    Page<Order> findAllByState(Integer state,PageRequest pageable);

    List<Order> queryOrderByLineId(String lineId);
}
