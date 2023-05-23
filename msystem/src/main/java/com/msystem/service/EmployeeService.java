package com.msystem.service;

import com.msystem.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * @Description: Employee的Service接口
 * @author: Eker
 * @date: 2023/4/22 下午 07:44
 * @version: V1.0
 */
public interface EmployeeService {
    //查詢全部員工信息
    List<Employee> queryAllEmployee();
    //依員工id查詢員工信息
    Optional<Employee> queryEmployeeById(Integer id);
    //更新員工信息
    void updateEmployee(Employee emp);
    //刪除員工信息
    void deleteEmployee(Integer id);
    //查詢員工分頁信息
    Page<Employee> queryEmployeeByPage(Pageable pageable);
    //依關鍵字模糊查詢員工信息
    Page<Employee> findByENameContainingOrAddressContainingOrPhoneContaining(String eName,String address,String phone, Pageable pageable);;
}
