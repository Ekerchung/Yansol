package com.msystem.repository;

import com.msystem.entity.Good;
import com.msystem.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Description: OrderRepository接口
 * @author: Eker
 * @date: 2023/4/25 上午 12:31
 * @version: V1.0
 */
public interface OrderRepository extends JpaRepository<Order, Integer> {
    Page<Order> findAllByState(Integer state ,Pageable pageable);
    Page<Order> findByGood_LineIdContainingOrEmployee_eNameContainingOrGood_Company_CompanyNameContaining(String lineId, String empName, String companyName, Pageable pageable);
    Page<Order> findByGood_LineIdContainingOrEmployee_eNameContainingOrGood_Company_CompanyNameContainingAndComDateIsNull(String lineId, String empName, String companyName, Pageable pageable);
}
