package com.msystem.service.Impl;

import com.msystem.entity.Company;
import com.msystem.repository.CompanyRepository;
import com.msystem.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @Description: CompanyService的實現類
 * @author: Eker
 * @date: 2023/4/22 下午 07:43
 * @version: V1.0
 */
@Service
public class CompanyServiceImpl implements CompanyService {
    @Autowired
    private CompanyRepository companyRepository;

    /**
     * @titile: queryCompanyById
     * @description: 根據id查詢廠商信息
     * @param id 廠商id
     * @return: Optional<Company> 廠商信息
     * @author: Eker
     * @date: 2023/5/23 下午 03:27
     */
    @Override
    public Optional<Company> queryCompanyById(Integer id) {
        Optional<Company> company = companyRepository.findById(id);
        return company;
    }
    /**
     * @titile: queryAllCompany
     * @description: 查詢全部廠商信息
     * @return: List<Company> 廠商信息列表
     * @author: Eker
     * @date: 2023/5/23 下午 03:28
     */
    @Override
    public List<Company> queryAllCompany(){
        List<Company> companyList = companyRepository.findAll();
        return companyList;
    }
}
