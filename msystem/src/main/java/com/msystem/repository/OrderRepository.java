package com.msystem.repository;

import com.msystem.entity.Good;
import com.msystem.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

/**
 * @Description: OrderRepository接口
 * @author: Eker
 * @date: 2023/4/25 上午 12:31
 * @version: V1.0
 */
public interface OrderRepository extends JpaRepository<Order, Integer> {
    //根據狀態查詢生產訂單信息
    Page<Order> findAllByState(Integer state ,Pageable pageable);
    //依關鍵字模糊查詢lineId、employeeName、companyName的生產訂單信息
    Page<Order> findByGood_LineIdContainingOrEmployee_eNameContainingOrGood_Company_CompanyNameContaining(String lineId, String empName, String companyName, Pageable pageable);
    //依關鍵字模糊查詢lineId、employeeName、companyName，並且完成時間為null的生產訂單信息
    Page<Order> findByGood_LineIdContainingOrEmployee_eNameContainingOrGood_Company_CompanyNameContainingAndComDateIsNull(String lineId, String empName, String companyName, Pageable pageable);
    //依線材編號及狀態查詢生產訂單信息
    List<Order> findByGood_LineIdAndState(String lineId,Integer state);
    //依線材編號查詢生產訂單信息
    List<Order> findByGood_LineId(String lineId);
    //依員工id及生產訂單狀態查詢生產訂單信息
    List<Order> findOrdersByEmployee_eIdAndState(Integer eid,Integer state);
    //依員工id及查詢日期查詢生產訂單信息
    @Query(value = "SELECT * FROM t_order WHERE e_id = ?1 AND YEAR(com_date) = ?2 AND MONTH(com_date) = ?3 ORDER BY com_date DESC", nativeQuery = true)
    List<Order> findOrdersByEmployee_eIdAndByYearAndMonth(Integer eid, int year, int month);


}
