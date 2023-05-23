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

import java.util.ArrayList;
import java.util.Date;
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

    /**
     * @titile: queryOrderByEid
     * @description: 依員工id查詢生產訂單信息
     * @param eid 員工id
     * @return: List<Order> 生產訂單列表
     * @author: Eker
     * @date: 2023/5/23 下午 04:46
     */
    @GetMapping("/order/{eid}")
    public List<Order> queryOrderByEid(@PathVariable("eid") Integer eid){
        List<Order> orderList = orderService.queryOrderByEid(eid);
        return orderList;
    }
    /**
     * @titile: queryOrder
     * @description: 查詢訂單信息
     * @param lineId 線材編號
     * @return: List<Order> 線材信息列表
     * @author: Eker
     * @date: 2023/5/23 下午 04:47
     */
    @GetMapping("/order")
    public List<Order> queryOrder(@RequestParam(required = false) String lineId){
        //若沒輸入線材編號
        if(lineId == null || lineId.equals("")){
            //調用queryAllOrder()
            List<Order> orderList = orderService.queryAllOrder();
        return orderList;
        //若有輸入線材編號
        }else {
            //調用queryOrderByLineId()
            List<Order> orderList = orderService.queryOrderByLineId(lineId);
            return orderList;
        }
    }
    /**
     * @titile: queryOrderByEidByDate
     * @description: 依員工id及查詢日期查詢生產訂單信息
     * @param eid 員工id
     * @param queryDate 查詢日期
     * @return: List<Order> 生產訂單列表
     * @author: Eker
     * @date: 2023/5/23 下午 04:49
     */
    @GetMapping("/user/order")
    public List<Order> queryOrderByEidByDate(@RequestParam Integer eid,@RequestParam String queryDate){
        List<Order> orderList = orderService.queryOrderByEidByDate(eid,queryDate);
        return orderList;
    }

    /**
     * @titile: queryOrderByPage
     * @description: 查詢生產訂單分頁信息
     * @param pageNum 頁碼
     * @param queryKeyWord 查詢關鍵字
     * @param completeVisible 已完成生產訂單是否顯示。true：顯示，false：不顯示
     * @return: null
     * @author: Eker
     * @date: 2023/5/23 下午 04:49
     */
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
    /**
     * @titile: addOrder
     * @description: 新增生產訂單
     * @param orderAddDto 生產訂單信息
     * @return: ResponseEntity 響應給前端狀態碼及body資訊
     * @author: Eker
     * @date: 2023/5/23 下午 04:55
     */
    @Transactional
    @PostMapping("/order")
    public ResponseEntity addOrder(@RequestBody OrderAddDto orderAddDto){
        ResponseEntity response = orderService.addOrder(orderAddDto);
        return response;
    }
    /**
     * @titile: updateOrder
     * @description: 更新生產訂單
     * @param orderUpdateDto 生產訂單信息
     * @return: ResponseEntity 響應給前端狀態碼及body資訊
     * @author: Eker
     * @date: 2023/5/23 下午 04:55
     */
    @Transactional
    @PutMapping("/order")
    public ResponseEntity updateOrder(@RequestBody OrderUpdateDto orderUpdateDto){
        ResponseEntity response = orderService.updateOrder(orderUpdateDto);
        return response;
    }

    /**
     * @titile: deleteOrder
     * @description: 刪除生產訂單
     * @param id 生產訂單id
     * @return: ResponseEntity 響應給前端狀態碼及body資訊
     * @author: Eker
     * @date: 2023/5/23 下午 04:55
     */
    @Transactional
    @DeleteMapping("/order/{id}")
    public ResponseEntity deleteOrder(@PathVariable("id")Integer id){
        ResponseEntity response = orderService.deleteOrder(id);
        return response;
    }
}
