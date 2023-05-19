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

    @GetMapping(value = "/good/{id}")
    public Good queryGoodById(@PathVariable("id") int id, Model model){
        System.out.println("調用了queryGoodById()");
        Optional<Good> good = goodService.queryGoodById(id);
        return good.orElse(null);
    }

    @GetMapping(value = "/good")
    @Transactional
    public List<Good> queryAllGood(){
//        System.out.println("調用了queryAllGood()");
        List<Good> goodList = goodService.queryAllGood();
        return goodList;
    }

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
//        if(queryKeyWord == null || queryKeyWord.equals("")){
//            goodPage = goodService.queryGoodByPageByState(pageable,state);
//        }else {
//            String lineName = queryKeyWord;
//            String lineType = queryKeyWord;
//            String lineId = queryKeyWord;
//            String companyName = queryKeyWord;
//            goodPage = goodService.findGoodsByStateAndLineNameOrLineTypeOrLineIdOrCompanyNameContaining(state, lineName, lineType, lineId, companyName,pageable);
//        }

        return goodPage;
    }
    @PostMapping(value = "/good")
    @Transactional
    public ResponseEntity addGood(@RequestBody List<GoodDto> goodDtoList){
        ResponseEntity response = goodService.addGood(goodDtoList);
        return response;
    }

    @Transactional
    @PutMapping("/good")
    public ResponseEntity updateGood(@RequestBody GoodDto goodDto){
        ResponseEntity response = goodService.updateGood(goodDto);
        return response;
    }
}
