package com.msystem.service.Impl;

import com.msystem.entity.Order;
import com.msystem.repository.OrderRepository;
import com.msystem.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * @Description: OrderService的實現類
 * @author: Eker
 * @date: 2023/4/25 上午 12:33
 * @version: V1.0
 */
@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Override
    @Transactional
    public List<Order> queryAllOrder(){
        List<Order> orderList = orderRepository.findAll();
        return orderList;
    }

    @Override
    @Transactional
    public Optional<Order> queryOrderById(Integer id){
        Optional<Order> order = orderRepository.findById(id);
        return order;
    }
}
