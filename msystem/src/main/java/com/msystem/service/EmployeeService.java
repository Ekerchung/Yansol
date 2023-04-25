package com.msystem.service;

import com.msystem.entity.Employee;

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

    Optional<Employee> queryAllEmployeeById(Integer id);
}
