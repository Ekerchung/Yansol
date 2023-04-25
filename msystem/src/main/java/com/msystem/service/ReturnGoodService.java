package com.msystem.service;

import com.msystem.entity.ReturnGood;

import java.util.List;
import java.util.Optional;

/**
 * @Description: ReturnGood的Service接口
 * @author: Eker
 * @date: 2023/4/25 下午 03:44
 * @version: V1.0
 */
public interface ReturnGoodService {
    List<ReturnGood> queryAllReturnGood();
    Optional<ReturnGood> queryReturnGoodById(Integer id);
}
