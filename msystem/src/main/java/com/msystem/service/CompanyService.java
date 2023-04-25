package com.msystem.service;

import com.msystem.entity.Company;

import java.util.List;
import java.util.Optional;

/**
 * @Description: Company的Service接口
 * @author: Eker
 * @date: 2023/4/22 下午 07:42
 * @version: V1.0
 */
public interface CompanyService {
    Optional<Company> queryCompanyById(Integer id);
    List<Company> queryAllCompany();
}
