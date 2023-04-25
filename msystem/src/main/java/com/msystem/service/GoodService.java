package com.msystem.service;

import com.msystem.entity.Good;

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
}
