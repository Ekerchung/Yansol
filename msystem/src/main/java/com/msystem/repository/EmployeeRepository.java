package com.msystem.repository;

import com.msystem.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Description: EmployeeRepository接口
 * @author: Eker
 * @date: 2023/4/22 下午 07:37
 * @version: V1.0
 */
public interface EmployeeRepository extends JpaRepository<Employee,Integer> {
    // 分頁模糊查詢方法，可以通過方法名自動解析參數並生成對應的SQL語句
    Page<Employee> findByeNameContainingOrAddressContainingOrPhoneContaining(String eName,String address,String phone, Pageable pageable);
}
