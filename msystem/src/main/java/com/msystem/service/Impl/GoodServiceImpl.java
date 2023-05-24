package com.msystem.service.Impl;

import com.msystem.dto.GoodDto;
import com.msystem.entity.Account;
import com.msystem.entity.Company;
import com.msystem.entity.Good;
import com.msystem.repository.CompanyRepository;
import com.msystem.repository.GoodRepository;
import com.msystem.service.GoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @Description: GoodService接口的實現類
 * @author: Eker
 * @date: 2023/4/20 下午 11:59
 * @version: V1.0
 */

@Service
public class GoodServiceImpl implements GoodService {
    @Autowired
    private GoodRepository goodRepository;
    @Autowired
    private CompanyRepository companyRepository;


    /**
     * @titile: queryGoodById
     * @description: 根據id查詢線材信息
     * @param id 線材id
     * @return: Optional<Good> 線材信息
     * @author: Eker
     * @date: 2023/5/23 下午 03:34
     */
    @Override
    public Optional<Good> queryGoodById(int id) {
        Optional<Good> good = goodRepository.findById(id);
        return good;

    }

    /**
     * @titile: queryAllGood
     * @description: 查詢全部線材信息
     * @return: List<Good> 線材信息列表
     * @author: Eker
     * @date: 2023/5/23 下午 03:34
     */
    @Override
    public List<Good> queryAllGood() {
        List<Good> goodList = goodRepository.findAll();
        return goodList;
    }
    /**
     * @titile: queryAllGoodPage
     * @description: 查詢全部線材分頁信息
     * @param pageable 分頁參數
     * @return: Page<Good> 線材分頁信息
     * @author: Eker
     * @date: 2023/5/23 下午 03:35
     */
    @Override
    public Page<Good> queryAllGoodPage(PageRequest pageable) {
        Page<Good> goodPage = goodRepository.findAll(pageable);
        return goodPage;
    }

    /**
     * @titile: addGood
     * @description: 新增線材
     * @param goodDtoList 新增線材分頁列表
     * @return: ResponseEntity 響應給前端狀態碼及body資訊
     * @author: Eker
     * @date: 2023/5/23 下午 03:36
     */
    @Override
    public ResponseEntity addGood(List<GoodDto> goodDtoList) {
        List<Good> goodList = new ArrayList<>();
        int isExist = 0; //0代表lineId存在，1代表不存在
        if(!goodDtoList.isEmpty()){
            for (int i = 0; i < goodDtoList.size(); i++) {
                if (goodRepository.findBylineId(goodDtoList.get(i).getLineId()) == null && isExist == 0) {  //isExit = 0 修改Good
                    Good good = new Good();
                    Optional<Company> optionalCompany = companyRepository.findById(goodDtoList.get(i).getCompanyId());
                    good.setLineId(goodDtoList.get(i).getLineId());
                    good.setLineName(goodDtoList.get(i).getLineName());
                    good.setLineType(goodDtoList.get(i).getLineType());
                    good.setPDate(goodDtoList.get(i).getPDate());
                    good.setTotalCount(goodDtoList.get(i).getTotalCount());
                    good.setRemainingCount(goodDtoList.get(i).getTotalCount());
                    good.setUnitPrice(goodDtoList.get(i).getUnitPrice());
                    good.setCompany(optionalCompany.orElse(null));
                    good.setState(1);
                    goodList.add(good);
                } else {
                    isExist = 1;
                }
            }
        }
        if(isExist == 0){
            List<Good> goods = goodRepository.saveAll(goodList);
            System.out.println(goods);
            return ResponseEntity.status(HttpStatus.OK).body("添加成功");
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("線材編號有重複，請確認");

    }

    /**
     * @titile: queryGoodByPageByState
     * @description: 根據線材狀態查詢線材分頁信息
     * @param pageable 分頁參數
     * @param state 線材狀態
     * @return: Page<Good> 線材分頁信息
     * @author: Eker
     * @date: 2023/5/23 下午 03:38
     */
    @Override
    public Page<Good> queryGoodByPageByState(PageRequest pageable,Integer state) {
        if (state != null){
            Page<Good> goodPage = goodRepository.findByState(state, pageable);
            return goodPage;
        }else{
            Page<Good> goodPage = goodRepository.findAll(pageable);
            return goodPage;
        }
    }

    /**
     * @titile: findGoodsByStateAndLineNameOrLineTypeOrLineIdOrCompanyNameContaining
     * @description: 根據關鍵字模糊查詢某線材狀態下的線材信息
     * @param state 線材狀態
     * @param lineName 線材狀態
     * @param lineType 線材狀態
     * @param lineId 線材編號
     * @param companyName 廠商名稱
     * @param pageable 分頁參數
     * @return: Page<Good> 線材分頁信息
     * @author: Eker
     * @date: 2023/5/23 下午 03:40
     */
    @Override
    public Page<Good> findGoodsByStateAndLineNameOrLineTypeOrLineIdOrCompanyNameContaining(Integer state, String lineName, String lineType, String lineId, String companyName, Pageable pageable) {
        Page<Good> goodPage = goodRepository.findGoodsByStateAndLineNameOrLineTypeOrLineIdOrCompanyNameContaining(state, lineName,lineType,lineId,companyName, pageable);
        return goodPage;
    }

    /**
     * @titile: findGoodsByLineNameContainingOrLineTypeContainingOrCompany_CompanyNameContaining
     * @description: 根據關鍵字模糊查詢線材信息
     * @param lineName 線材狀態
     * @param lineType 線材狀態
     * @param lineId 線材編號
     * @param companyName 廠商名稱
     * @param pageable 分頁參數
     * @return: Page<Good> 線材分頁信息
     * @author: Eker
     * @date: 2023/5/23 下午 03:42
     */
    @Override
    public Page<Good> findGoodsByLineNameContainingOrLineTypeContainingOrCompany_CompanyNameContaining(String lineName, String lineType, String lineId, String companyName, Pageable pageable) {
        Page<Good> goodPage = goodRepository.findGoodsByLineNameContainingOrLineTypeContainingOrLineIdContainingOrCompany_CompanyNameContaining(lineName,lineType,lineId,companyName, pageable);
        System.out.println(goodPage);
        return goodPage;
    }

    /**
     * @titile: updateGood
     * @description: 更新線材信息
     * @param goodDto 輸入的線材信息
     * @return: ResponseEntity 響應給前端狀態碼及body資訊
     * @author: Eker
     * @date: 2023/5/23 下午 03:43
     */
    @Override
    public ResponseEntity updateGood(GoodDto goodDto) {
        //獲取資料庫中good信息
        Optional<Good> goodOptional = goodRepository.findById(goodDto.getGId());
        Good good = goodOptional.orElse(null);
        //獲取goodDto中的廠商信息
        Integer companyId = goodDto.getCompanyId();
        Optional<Company> optionalCompany = companyRepository.findById(goodDto.getCompanyId());
        //保存修改資料到資料庫中
        assert good != null;
        good.setLineId(goodDto.getLineId());
        good.setLineName(goodDto.getLineName());
        good.setLineType(goodDto.getLineType());
        good.setPDate(goodDto.getPDate());
        good.setTotalCount(goodDto.getTotalCount());
        good.setUnitPrice(goodDto.getUnitPrice());
        good.setCompany(optionalCompany.orElse(null));
        if(goodDto.getSDate() != null){
            good.setSDate(goodDto.getSDate());
            good.setState(4);
        }
        return ResponseEntity.status(HttpStatus.OK).body("修改完成");
    }
}
