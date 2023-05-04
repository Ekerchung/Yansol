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
    List<Employee> queryAllEmployee();

    Optional<Employee> queryEmployeeById(Integer id);
    void updateEmployee(Employee emp);

    void deleteEmployee(Integer id);

    Page<Employee> queryEmployeeByPage(Pageable pageable);
    Page<Employee> findByENameContainingOrAddressContainingOrPhoneContaining(String eName,String address,String phone, Pageable pageable);;
}
