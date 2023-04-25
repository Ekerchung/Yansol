package com.msystem.repository;

import com.msystem.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Description: CompanyRepository接口
 * @author: Eker
 * @date: 2023/4/20 下午 11:52
 * @version: V1.0
 */
public interface CompanyRepository extends JpaRepository<Company, Integer> {
}