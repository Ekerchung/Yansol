package com.msystem.controller;

import com.msystem.entity.Employee;
import com.msystem.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @Description: Employee的控制類
 * @author: Eker
 * @date: 2023/4/22 下午 07:40
 * @version: V1.0
 */
@CrossOrigin
@RestController
@RequestMapping(value = "/api")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;
    /**
     * @titile: queryAllEmployee
     * @description: 查詢全部員工信息
     * @return: List<Employee> 員工信息列表
     * @author: Eker
     * @date: 2023/5/23 下午 04:39
     */
    @GetMapping(value = "/employee")
    public List<Employee> queryAllEmployee(){
        List<Employee> employeeList = employeeService.queryAllEmployee();
        return employeeList;
    }
    /**
     * @titile: queryEmployeeByPage
     * @description: 查詢員工分頁信息
     * @param pageNum 頁碼
     * @param queryKeyWord 查詢關鍵字
     * @return: Page<Employee> 員工分頁信息
     * @author: Eker
     * @date: 2023/5/23 下午 04:39
     */
    @GetMapping(value = "/page/employee/")
    public Page<Employee> queryEmployeeByPage(@RequestParam Integer pageNum,@RequestParam(required = false) String queryKeyWord){
        //默認頁碼為0，PageRequest頁碼從0開始
        int page = 0;
        if(pageNum != null){
            page = pageNum - 1;
        }
        //默認每頁顯示10個數據
        int size = 10;
        //默認排序依照id
        String sortBy = "eId";
        PageRequest pageable = PageRequest.of(page, size, Sort.by(sortBy));
        Page<Employee> employeePage = null;
        if(queryKeyWord == null){
            employeePage = employeeService.queryEmployeeByPage(pageable);
        }else {
            String eName = queryKeyWord;
            String address = queryKeyWord;
            String phone = queryKeyWord;
            employeePage = employeeService.findByENameContainingOrAddressContainingOrPhoneContaining(eName,address,phone,pageable);
        }
        return employeePage;
    }
    /**
     * @titile: queryEmployeeById
     * @description: 依員工id查詢員工信息
     * @param id 員工id
     * @return: Employee 員工信息
     * @author: Eker
     * @date: 2023/5/23 下午 04:40
     */
    @GetMapping(value = "/employee/{id}")
    public Employee queryEmployeeById(@PathVariable("id") Integer id){
        Optional<Employee> employee = employeeService.queryEmployeeById(id);
        return employee.orElse(null);
    }
    /**
     * @titile: updateEmployee
     * @description: 更新員工信息
     * @param emp 員工信息
     * @author: Eker
     * @date: 2023/5/23 下午 04:40
     */
    @PutMapping(value = "/employee")
    public void updateEmployee(@RequestBody Employee emp){
        employeeService.updateEmployee(emp);
    }
    /**
     * @titile: deleteEmployee
     * @description: 刪除員工信息
     * @param id 員工id
     * @author: Eker
     * @date: 2023/5/23 下午 04:41
     */
    @DeleteMapping(value = "/employee/{id}")
    public void deleteEmployee(@PathVariable("id") Integer id){
        employeeService.deleteEmployee(id);
    }
}
