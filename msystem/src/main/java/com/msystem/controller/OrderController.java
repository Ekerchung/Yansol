package com.msystem.controller;

import com.msystem.dto.OrderAddDto;
import com.msystem.dto.OrderUpdateDto;
import com.msystem.entity.Order;
import com.msystem.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * @Description: Order的控制類
 * @author: Eker
 * @date: 2023/4/21 上午 12:47
 * @version: V1.0
 */
@CrossOrigin
@RestController
@RequestMapping("/api")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @GetMapping("/order")
    public List<Order> queryAllOrder(){
        List<Order> orderList = orderService.queryAllOrder();
        return orderList;
    }
    @GetMapping("/order/{id}")
    public Order queryOrderById(@PathVariable("id") Integer id){
        Optional<Order> order = orderService.queryOrderById(id);
        return order.orElse(null);
    }
    @GetMapping("/page/order")
    public Page<Order> queryOrderByPage(@RequestParam Integer pageNum, @RequestParam(required = false) String queryKeyWord, @RequestParam boolean completeVisible){
        //默認頁碼為0，PageRequest頁碼從0開始
        int page = 0;
        if(pageNum != null){
            page = pageNum - 1;
        }
        //默認每頁顯示10個數據
        int size = 10;
        //默認排序依照id
        String sortBy = "oId";
        PageRequest pageable = PageRequest.of(page, size, Sort.by(sortBy));
        Page<Order> orderPage = null;

        if(completeVisible){
            if(queryKeyWord == null || queryKeyWord.equals("")){
                orderPage = orderService.findAllByState(1,pageable);
            }else {
                String lineId = queryKeyWord;
                String empName = queryKeyWord;
                String companyName = queryKeyWord;
                orderPage = orderService.findByGood_LineIdContainingOrEmployee_eNameContainingOrGood_Company_CompanyNameContainingAndComDateIsNull(lineId, empName, companyName,pageable);
            }
        }else {
            if(queryKeyWord == null || queryKeyWord.equals("")){
                orderPage = orderService.queryOrderByPage(pageable);
            }else {
                String lineId = queryKeyWord;
                String empName = queryKeyWord;
                String companyName = queryKeyWord;
                orderPage = orderService.findByGood_LineIdContainingOrEmployee_eNameContainingOrGood_Company_CompanyNameContaining(lineId, empName, companyName,pageable);
            }
        }
        return orderPage;
    }
    @Transactional
    @PostMapping("/order")
    public ResponseEntity addOrder(@RequestBody OrderAddDto orderAddDto){
        ResponseEntity response = orderService.addOrder(orderAddDto);
        return response;
    }
    @Transactional
    @PutMapping("/order")
    public ResponseEntity updateOrder(@RequestBody OrderUpdateDto orderUpdateDto){
        ResponseEntity response = orderService.updateOrder(orderUpdateDto);
        return response;
    }

    @Transactional
    @DeleteMapping("/order/{id}")
    public ResponseEntity deleteOrder(@PathVariable("id")Integer id){
        ResponseEntity response = orderService.deleteOrder(id);
        return response;
    }
}
