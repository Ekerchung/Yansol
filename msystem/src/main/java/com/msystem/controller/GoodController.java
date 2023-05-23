package com.msystem.controller;

import com.msystem.dto.GoodDto;
import com.msystem.dto.OrderUpdateDto;
import com.msystem.entity.Account;
import com.msystem.entity.Good;
import com.msystem.service.GoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @Description: Good的控制類
 * @author: Eker
 * @date: 2023/4/21 上午 12:50
 * @version: V1.0
 */
/*
queryGoodById(id) --> /get/{id}
queryGoodById(id) --> /get/{id}



*/
@CrossOrigin
@RestController
@RequestMapping(value = "/api")
public class GoodController {
    @Autowired
    private GoodService goodService;

    /**
     * @titile: queryGoodById
     * @description: 依線材id查詢線材信息
     * @param id 線材id
     * @return: Good 線材信息
     * @author: Eker
     * @date: 2023/5/23 下午 04:41
     */
    @GetMapping(value = "/good/{id}")
    public Good queryGoodById(@PathVariable("id") int id){
        Optional<Good> good = goodService.queryGoodById(id);
        return good.orElse(null);
    }

    /**
     * @titile: queryAllGood
     * @description: 查詢全部線材信息
     * @return: List<Good> 訂單線材列表
     * @author: Eker
     * @date: 2023/5/23 下午 04:42
     */
    @GetMapping(value = "/good")
    @Transactional
    public List<Good> queryAllGood(){
        List<Good> goodList = goodService.queryAllGood();
        return goodList;
    }

    /**
     * @titile: queryGoodByPageByState
     * @description: 依狀態查詢線材分頁信息
     * @param pageNum 頁碼
     * @param queryKeyWord 查詢關鍵字
     * @param state 線材狀態
     * @return: Page<Good> 線材分頁信息
     * @author: Eker
     * @date: 2023/5/23 下午 04:43
     */
    @GetMapping(value = "/page/good")
    public Page<Good> queryGoodByPageByState(@RequestParam Integer pageNum, @RequestParam(required = false) String queryKeyWord,@RequestParam(required = false) Integer state){
        //默認頁碼為0，PageRequest頁碼從0開始
        int page = 0;
        if(pageNum != null){
            page = pageNum - 1;
        }
        //默認每頁顯示10個數據
        int size = 10;
        //默認排序依照id
        String sortBy = "gId";
        PageRequest pageable = PageRequest.of(page, size, Sort.by(sortBy));
        Page<Good> goodPage = null;
        //若沒狀態碼
        if(state == null){
            if(queryKeyWord == null || queryKeyWord.equals("")){
                goodPage = goodService.queryAllGoodPage(pageable);
            }else {
                String lineName = queryKeyWord;
                String lineType = queryKeyWord;
                String lineId = queryKeyWord;
                String companyName = queryKeyWord;
                goodPage = goodService.findGoodsByLineNameContainingOrLineTypeContainingOrCompany_CompanyNameContaining(lineName, lineType, lineId, companyName,pageable);
            }
        //若有狀態碼
        }else{
            if(queryKeyWord == null || queryKeyWord.equals("")){
                goodPage = goodService.queryGoodByPageByState(pageable,state);
            }else {
                String lineName = queryKeyWord;
                String lineType = queryKeyWord;
                String lineId = queryKeyWord;
                String companyName = queryKeyWord;
                goodPage = goodService.findGoodsByStateAndLineNameOrLineTypeOrLineIdOrCompanyNameContaining(state, lineName, lineType, lineId, companyName,pageable);
            }
        }
        return goodPage;
    }
    /**
     * @titile: addGood
     * @description: 新增線材信息
     * @param goodDtoList 線材信息
     * @return: ResponseEntity 響應給前端狀態碼及body資訊
     * @author: Eker
     * @date: 2023/5/23 下午 04:44
     */
    @PostMapping(value = "/good")
    @Transactional
    public ResponseEntity addGood(@RequestBody List<GoodDto> goodDtoList){
        ResponseEntity response = goodService.addGood(goodDtoList);
        return response;
    }
    /**
     * @titile: updateGood
     * @description: 更新線材信息
     * @param goodDto 線材信息
     * @return: ResponseEntity 響應給前端狀態碼及body資訊
     * @author: Eker
     * @date: 2023/5/23 下午 04:45
     */
    @Transactional
    @PutMapping("/good")
    public ResponseEntity updateGood(@RequestBody GoodDto goodDto){
        ResponseEntity response = goodService.updateGood(goodDto);
        return response;
    }
}
