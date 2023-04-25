package com.msystem.service;

import com.msystem.entity.Order;

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
}
