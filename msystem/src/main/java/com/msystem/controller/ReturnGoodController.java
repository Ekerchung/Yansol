package com.msystem.controller;

import com.msystem.entity.ReturnGood;
import com.msystem.service.ReturnGoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * @Description: ReturnGood的控制類
 * @author: Eker
 * @date: 2023/4/25 下午 03:42
 * @version: V1.0
 */
@CrossOrigin
@RestController
@RequestMapping(value = "/api")
public class ReturnGoodController {
    @Autowired
    private ReturnGoodService returnGoodService;

    @GetMapping(value = "/returngood")
    public List<ReturnGood> queryAllReturnGood(){
        List<ReturnGood> returnGoodList = returnGoodService.queryAllReturnGood();
        return returnGoodList;
    }

    @GetMapping(value = "returngood/{id}")
    public ReturnGood queryReturnGoodById(@PathVariable("id") Integer id){
        Optional<ReturnGood> returnGood = returnGoodService.queryReturnGoodById(id);
        return returnGood.orElse(null);
    }
}
