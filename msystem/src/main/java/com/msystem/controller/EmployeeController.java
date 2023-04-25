package com.msystem.controller;

import com.msystem.entity.Employee;
import com.msystem.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
    @GetMapping(value = "/employee")
    public List<Employee> queryAllEmployee(){
        List<Employee> employeeList = employeeService.queryAllEmployee();
        return employeeList;
    }
    @GetMapping(value = "/employee/{id}")
    public Employee queryAllEmployeeById(@PathVariable("id") Integer id){
        Optional<Employee> employee = employeeService.queryAllEmployeeById(id);
        return employee.orElse(null);
    }
}
