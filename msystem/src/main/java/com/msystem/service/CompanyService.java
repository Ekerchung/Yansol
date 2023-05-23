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
    //依廠商id查詢廠商信息
    Optional<Company> queryCompanyById(Integer id);
    //查詢全部廠商信息
    List<Company> queryAllCompany();
}
