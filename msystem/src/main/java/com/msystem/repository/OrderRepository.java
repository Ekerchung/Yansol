package com.msystem.repository;

import com.msystem.entity.Good;
import com.msystem.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @Description: OrderRepository接口
 * @author: Eker
 * @date: 2023/4/25 上午 12:31
 * @version: V1.0
 */
public interface OrderRepository extends JpaRepository<Order, Integer> {
    //根據狀態查詢order
    Page<Order> findAllByState(Integer state ,Pageable pageable);
    //依關鍵字模糊查詢lineId、employeeName、companyName
    Page<Order> findByGood_LineIdContainingOrEmployee_eNameContainingOrGood_Company_CompanyNameContaining(String lineId, String empName, String companyName, Pageable pageable);
    //依關鍵字模糊查詢lineId、employeeName、companyName，並且完成時間為null
    Page<Order> findByGood_LineIdContainingOrEmployee_eNameContainingOrGood_Company_CompanyNameContainingAndComDateIsNull(String lineId, String empName, String companyName, Pageable pageable);
    List<Order> findByGood_LineIdAndState(String lineId,Integer state);
    List<Order> findByGood_LineId(String lineId);

}
