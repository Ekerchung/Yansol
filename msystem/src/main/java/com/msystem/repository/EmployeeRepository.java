package com.msystem.repository;

import com.msystem.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Description: EmployeeRepository接口
 * @author: Eker
 * @date: 2023/4/22 下午 07:37
 * @version: V1.0
 */
public interface EmployeeRepository extends JpaRepository<Employee,Integer> {
}
