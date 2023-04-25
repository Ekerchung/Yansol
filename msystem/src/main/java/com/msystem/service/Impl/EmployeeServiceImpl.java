package com.msystem.service.Impl;

import com.msystem.entity.Employee;
import com.msystem.repository.EmployeeRepository;
import com.msystem.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @Description: EmployeeService的實現類
 * @author: Eker
 * @date: 2023/4/22 下午 07:45
 * @version: V1.0
 */
@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;
    @Override
    public List<Employee> queryAllEmployee() {
        List<Employee> employeeList = employeeRepository.findAll();
        return employeeList;
    }

    @Override
    public Optional<Employee> queryAllEmployeeById(Integer id) {
        Optional<Employee> employee = employeeRepository.findById(id);
        return employee;
    }
}
