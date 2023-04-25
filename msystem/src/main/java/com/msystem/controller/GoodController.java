package com.msystem.controller;

import com.msystem.entity.Good;
import com.msystem.service.GoodService;
import org.springframework.beans.factory.annotation.Autowired;
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
}
