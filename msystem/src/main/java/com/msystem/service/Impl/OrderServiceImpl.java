package com.msystem.service.Impl;

import com.msystem.dto.OrderAddDto;
import com.msystem.dto.OrderUpdateDto;
import com.msystem.entity.Employee;
import com.msystem.entity.Good;
import com.msystem.entity.Order;
import com.msystem.repository.EmployeeRepository;
import com.msystem.repository.GoodRepository;
import com.msystem.repository.OrderRepository;
import com.msystem.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
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
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private GoodRepository goodRepository;

   
    /**
     * @titile: queryAllOrder
     * @description: 查詢全部生產訂單
     * @return: List<Order> 生產訂單列表
     * @author: Eker
     * @date: 2023/5/23 下午 03:46
     */
    @Override
    @Transactional
    public List<Order> queryAllOrder(){
        List<Order> orderList = orderRepository.findAll();
        return orderList;
    }

    /**
     * @titile: queryOrderByEid
     * @description: 根據員工id查詢生產訂單
     * @param eid 員工id
     * @return: List<Order> 生產訂單列表
     * @author: Eker
     * @date: 2023/5/23 下午 03:46
     */
    @Override
    public List<Order> queryOrderByEid(Integer eid){
        List<Order> orderList = orderRepository.findOrdersByEmployee_eIdAndState(eid,1);
        return orderList;
    }
   
    /**
     * @titile: queryOrderByEidByDate
     * @description: 根據員工id及輸入日期查詢生產訂單
     * @param eid 員工id
     * @param queryDate 查詢的日期
     * @return: List<Order> 生產訂單列表
     * @author: Eker
     * @date: 2023/5/23 下午 03:48
     */
    @Override
    public List<Order> queryOrderByEidByDate(Integer eid, String queryDate){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDate date = LocalDate.parse(queryDate, formatter);
        int year = date.getYear();
        int month = date.getMonthValue();
        List<Order> orderList = orderRepository.findOrdersByEmployee_eIdAndByYearAndMonth(eid,year,month);
        return orderList;
    }

    /**
     * @titile: queryOrderByLineId
     * @description: 根據線材編號查詢生產訂單
     * @param lineId 線材編號
     * @return: List<Order> 生產訂單列表
     * @author: Eker
     * @date: 2023/5/23 下午 03:49
     */
    @Override
    public List<Order> queryOrderByLineId(String lineId){
        List<Order> orderList = orderRepository.findByGood_LineId(lineId);
        return orderList;
    }

    /**
     * @titile: queryOrderByPage
     * @description: 查詢生產訂單分頁信息
     * @param pageable 分頁參數
     * @return: Page<Order> 生產訂單分頁信息
     * @author: Eker
     * @date: 2023/5/23 下午 03:49
     */
    @Override
    public Page<Order> queryOrderByPage(PageRequest pageable) {
        Page<Order> orderPage = orderRepository.findAll(pageable);
        return orderPage;
    }
    /**
     * @titile: findAllByState
     * @description: 根據狀態查詢全部生產訂單
     * @param state 生產訂單狀態
     * @param pageable 分頁參數
     * @return: Page<Order> 生產訂單分頁信息
     * @author: Eker
     * @date: 2023/5/23 下午 03:50
     */
    @Override
    public Page<Order> findAllByState(Integer state, PageRequest pageable) {
        Page<Order> orderPage = orderRepository.findAllByState(state,pageable);
        return orderPage;
    }

    /**
     * @titile: findByGood_LineIdContainingOrEmployee_eNameContainingOrGood_Company_CompanyNameContaining
     * @description: 依關鍵字模糊查詢線材
     * @param lineId 線材編號
     * @param empName 員工名
     * @param companyName 廠商名稱
     * @param pageable 分頁參數
     * @return: Page<Order> 生產訂單分頁信息
     * @author: Eker
     * @date: 2023/5/23 下午 03:51
     */
    @Override
    public Page<Order> findByGood_LineIdContainingOrEmployee_eNameContainingOrGood_Company_CompanyNameContaining(String lineId, String empName, String companyName, PageRequest pageable) {
        Page<Order> orderPage = orderRepository.findByGood_LineIdContainingOrEmployee_eNameContainingOrGood_Company_CompanyNameContaining(lineId, empName, companyName, pageable);
        return orderPage;
    }
    /**
     * @titile: findByGood_LineIdContainingOrEmployee_eNameContainingOrGood_Company_CompanyNameContainingAndComDateIsNull
     * @description: 尚未完成的生產訂單依關鍵字模糊查詢線材
     * @param lineId 線材編號
     * @param empName 員工名
     * @param companyName 廠商名稱
     * @param pageable 分頁參數
     * @return: Page<Order> 生產訂單分頁信息
     * @author: Eker
     * @date: 2023/5/23 下午 03:59
     */
    @Override
    public Page<Order> findByGood_LineIdContainingOrEmployee_eNameContainingOrGood_Company_CompanyNameContainingAndComDateIsNull(String lineId, String empName, String companyName, PageRequest pageable) {
        Page<Order> orderPage = orderRepository.findByGood_LineIdContainingOrEmployee_eNameContainingOrGood_Company_CompanyNameContainingAndComDateIsNull(lineId, empName, companyName, pageable);
        return orderPage;
    }

    /**
     * @titile: addOrder
     * @description: 新增生產訂單
     * @param orderAddDto 生產訂單信息
     * @return: ResponseEntity 響應給前端狀態碼及body資訊
     * @author: Eker
     * @date: 2023/5/23 下午 04:00
     */
    @Override
    public ResponseEntity addOrder(OrderAddDto orderAddDto) {
        Order order = new Order();
        //獲取員工信息
        Optional<Employee> employee = employeeRepository.findById(orderAddDto.getEId());
        //獲取線材信息
        Good good = goodRepository.findBylineId(orderAddDto.getLineId());
        //將要新增的訂單信息保存到訂單
        order.setEmployee(employee.orElse(null));
        order.setGood(good);
        order.setODate(new Date());
        order.setOCount(orderAddDto.getOrderCount());
        order.setOUnitPrice(orderAddDto.getOrderUnitPrice());
        //保存訂單
        orderRepository.save(order);
        //更新線材信息內待分配數量
        int remainingCount = good.getRemainingCount() - order.getOCount();
        good.setRemainingCount(remainingCount);
        goodRepository.save(good);
        return ResponseEntity.status(HttpStatus.OK).body("添加成功");
    }

    /**
     * @titile: updateOrder
     * @description: 更新生產訂單
     * @param orderUpdateDto 生產訂單信息
     * @return: ResponseEntity 響應給前端狀態碼及body資訊
     * @author: Eker
     * @date: 2023/5/23 下午 04:01
     */
    @Override
    public ResponseEntity updateOrder(OrderUpdateDto orderUpdateDto) {
        //獲取訂單ID
        Integer orderId = orderUpdateDto.getOId();
        //由訂單ID取得訂單訊息
        Optional<Order> orderOptional = orderRepository.findById(orderId);
        //取得訂單信息中的線材id
        Good goodInOrder = orderOptional.orElse(null).getGood();
        //取得資料庫中訂單的線材信息
        Good good = goodRepository.findBylineId(goodInOrder.getLineId());
        //取得資料庫中線材的待分配數量
        int remainingCount = good.getRemainingCount();
        //修改線材待分配數量
        good.setRemainingCount(remainingCount + orderOptional.orElse(null).getOCount() - orderUpdateDto.getCount());
        //將修改後的線材信息保存至資料庫
        goodRepository.save(good);
        //將修改後的數量保存到訂單
        orderOptional.orElse(null).setOCount(orderUpdateDto.getCount());
        //將修改後的單價保存到訂單
        orderOptional.orElse(null).setOUnitPrice(orderUpdateDto.getUnitPrice());
        //取得eid對應的員工信息
        Optional<Employee> employeeOptional = employeeRepository.findById(orderUpdateDto.getEmployeeId());
        //將員工信息保存到訂單中
        orderOptional.orElse(null).setEmployee(employeeOptional.orElse(null));
        //若有輸入完成日期，則保存至訂單中，並將訂單狀態改為2:完成
        if(orderUpdateDto.getComDate() != null){
            orderOptional.orElse(null).setComDate(orderUpdateDto.getComDate());
            orderOptional.orElse(null).setState(2);
            //查詢當前線材是否全部訂單都完成，若已完成則將線材狀態改為3:待發貨
            List<Order> orderList = orderRepository.findByGood_LineIdAndState(good.getLineId(),1);
            if(orderList.isEmpty() && remainingCount == 0){
                good.setState(3);
                goodRepository.save(good);
            }
        }
        //將訂單修改保存到資料庫
        orderRepository.save(orderOptional.orElse(null));
        return ResponseEntity.status(HttpStatus.OK).body("修改完成");
    }

    /**
     * @titile: deleteOrder
     * @description: 刪除生產訂單
     * @param id 生產訂單id
     * @return: ResponseEntity 響應給前端狀態碼及body資訊
     * @author: Eker
     * @date: 2023/5/23 下午 04:01
     */
    @Override
    public ResponseEntity deleteOrder(Integer id) {
        //將刪除的分配數量保存回good中待分配數量
        //取得訂單內容
        Optional<Order> order = orderRepository.findById(id);
        //取的線材編號
        assert order.orElse(null) != null;
        String lineId = order.orElse(null).getGood().getLineId();
        //依線材編號取得線材內容
        Good good = goodRepository.findBylineId(lineId);
        //將刪除數量保存回回good中待分配數量
        good.setRemainingCount(good.getRemainingCount() + order.orElse(null).getOCount());
        //確認欲刪除訂單的線材是否已經改為狀態2：生產中
        if(good.getState() == 2){
            good.setState(1);
        }
        //將修改的good保存回資料庫
        goodRepository.save(good);
        //刪除訂單
        orderRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body("刪除成功");
    }
}
