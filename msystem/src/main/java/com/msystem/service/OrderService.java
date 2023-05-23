package com.msystem.service;

import com.msystem.dto.OrderAddDto;
import com.msystem.dto.OrderUpdateDto;
import com.msystem.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * @Description: Order的Service接口
 * @author: Eker
 * @date: 2023/4/25 上午 12:32
 * @version: V1.0
 */
public interface OrderService {
    //查詢全部生產訂單信息
    List<Order> queryAllOrder();
    //新增生產訂單信息
    ResponseEntity addOrder(OrderAddDto orderAddDto);
    //查詢生產訂單分頁信息
    Page<Order> queryOrderByPage(PageRequest pageable);
    //依關鍵字模糊查詢生產訂單分頁信息
    Page<Order> findByGood_LineIdContainingOrEmployee_eNameContainingOrGood_Company_CompanyNameContaining(String lineId, String empName, String companyName, PageRequest pageable);
    //依關鍵字模糊查詢未完成生產訂單分頁信息
    Page<Order> findByGood_LineIdContainingOrEmployee_eNameContainingOrGood_Company_CompanyNameContainingAndComDateIsNull(String lineId, String empName, String companyName, PageRequest pageable);
    //刪除生產訂單信息
    ResponseEntity deleteOrder(Integer id);
    //更新生產訂單信息
    ResponseEntity updateOrder(OrderUpdateDto orderUpdateDto);
    //依狀態查詢生產訂單分頁信息
    Page<Order> findAllByState(Integer state,PageRequest pageable);
    //依線材編號查詢生產訂單信息
    List<Order> queryOrderByLineId(String lineId);
    //依員工id查詢生產訂單信息
    List<Order> queryOrderByEid(Integer eid);
    //依員工id及查詢日期查詢生產訂單信息
    List<Order> queryOrderByEidByDate(Integer eid, String queryDate);
}
