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

    @Override
    public void addOrder(){
        Good good = new Good();
        good.setTotalCount(500);
        goodRepository.save(good);
    }

    @Override
    public Optional<Good> queryGoodById(int id) {
        Optional<Good> good = goodRepository.findById(id);
        return good;

    }

    @Override
    public List<Good> queryAllGood() {
        List<Good> goodList = goodRepository.findAll();
        return goodList;
    }
    @Override
    public Page<Good> queryAllGoodPage(PageRequest pageable) {
        Page<Good> goodPage = goodRepository.findAll(pageable);
        return goodPage;
    }

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

    @Override
    public Page<Good> findGoodsByStateAndLineNameOrLineTypeOrLineIdOrCompanyNameContaining(Integer state, String lineName, String lineType, String lineId, String companyName, Pageable pageable) {
        Page<Good> goodPage = goodRepository.findGoodsByStateAndLineNameOrLineTypeOrLineIdOrCompanyNameContaining(state, lineName,lineType,lineId,companyName, pageable);
        return goodPage;
    }

    @Override
    public Page<Good> findGoodsByLineNameContainingOrLineTypeContainingOrCompany_CompanyNameContaining(String lineName, String lineType, String lineId, String companyName, Pageable pageable) {
        Page<Good> goodPage = goodRepository.findGoodsByLineNameContainingOrLineTypeContainingOrLineIdContainingOrCompany_CompanyNameContaining(lineName,lineType,lineId,companyName, pageable);
        System.out.println(goodPage);
        return goodPage;
    }

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
