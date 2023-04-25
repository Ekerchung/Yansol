package com.msystem.service.Impl;

import com.msystem.entity.Good;
import com.msystem.repository.GoodRepository;
import com.msystem.service.GoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
