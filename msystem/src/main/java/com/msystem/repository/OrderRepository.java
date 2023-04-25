package com.msystem.repository;

import com.msystem.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Description: OrderRepository接口
 * @author: Eker
 * @date: 2023/4/25 上午 12:31
 * @version: V1.0
 */
public interface OrderRepository extends JpaRepository<Order, Integer> {
}
