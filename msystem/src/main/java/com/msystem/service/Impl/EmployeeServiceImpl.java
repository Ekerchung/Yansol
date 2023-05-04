package com.msystem.service.Impl;

import com.msystem.entity.Employee;
import com.msystem.repository.EmployeeRepository;
import com.msystem.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    public Optional<Employee> queryEmployeeById(Integer id) {
        Optional<Employee> employee = employeeRepository.findById(id);
        return employee;
    }
    @Override
    public void updateEmployee(Employee emp) {
        if(emp.getEId() != 0){
            //若傳入的參數有eId值，為修改員工操作
            Optional<Employee> optionalEmployee = queryEmployeeById(emp.getEId());
            Employee employee = optionalEmployee.orElse(null);
            System.out.println(employee);
            assert employee != null;
            employee.setEName(emp.getEName());
            employee.setGender(emp.getGender());
            employee.setBirthday(emp.getBirthday());
            employee.setPhone(emp.getPhone());
            employee.setAddress(emp.getAddress());
            employeeRepository.save(employee);
        }else {
            ////若傳入的eId值為0，為新增員工操作
            employeeRepository.save(emp);
        }
    }

    @Override
    public void deleteEmployee(Integer id) {
        employeeRepository.deleteById(id);
    }
    @Override
    public Page<Employee> queryEmployeeByPage(Pageable pageable) {
        Page<Employee> employeePage = employeeRepository.findAll(pageable);
        return employeePage;
    }

    @Override
    public Page<Employee> findByENameContainingOrAddressContainingOrPhoneContaining(String eName, String address, String phone, Pageable pageable) {
        Page<Employee> employeePage = employeeRepository.findByeNameContainingOrAddressContainingOrPhoneContaining(eName, address, phone, pageable);
        return employeePage;
    }
}
