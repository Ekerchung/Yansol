package com.msystem.service;

import com.msystem.dto.GoodDto;
import com.msystem.entity.Good;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

/**
 * @Description: GoodService的接口
 * @author: Eker
 * @date: 2023/4/20 下午 11:58
 * @version: V1.0
 */
public interface GoodService {
    public void addOrder();
    public Optional<Good> queryGoodById(int id);

    List<Good> queryAllGood();

    ResponseEntity addGood(List<GoodDto> goodDtoList);

    Page<Good> queryGoodByPageByState(PageRequest pageable,Integer state);
    Page<Good> findGoodsByStateAndLineNameOrLineTypeOrLineIdOrCompanyNameContaining(Integer state, String lineName, String lineType, String lineId, String companyName, Pageable pageable);

    ResponseEntity updateGood(GoodDto goodDto);

    Page<Good> queryAllGoodPage(PageRequest pageable);

    Page<Good> findGoodsByLineNameContainingOrLineTypeContainingOrCompany_CompanyNameContaining(String lineName, String lineType, String lineId, String companyName, Pageable pageable);
}
