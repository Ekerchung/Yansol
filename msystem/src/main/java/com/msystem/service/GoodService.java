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
    //依線材編號查詢線材信息
    Optional<Good> queryGoodById(int id);
    //查詢全部線材信息
    List<Good> queryAllGood();
    //新增線材信息
    ResponseEntity addGood(List<GoodDto> goodDtoList);
    //依線材狀態查詢線材分頁信息
    Page<Good> queryGoodByPageByState(PageRequest pageable,Integer state);
    //依狀態及關鍵字模糊查詢線材信息
    Page<Good> findGoodsByStateAndLineNameOrLineTypeOrLineIdOrCompanyNameContaining(Integer state, String lineName, String lineType, String lineId, String companyName, Pageable pageable);
    //更新線材信息
    ResponseEntity updateGood(GoodDto goodDto);
    //查詢全部線材分頁信息
    Page<Good> queryAllGoodPage(PageRequest pageable);
    //依關鍵字模糊查詢線材信息
    Page<Good> findGoodsByLineNameContainingOrLineTypeContainingOrCompany_CompanyNameContaining(String lineName, String lineType, String lineId, String companyName, Pageable pageable);
}
