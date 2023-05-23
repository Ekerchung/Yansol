package com.msystem.service.Impl;

import com.msystem.entity.ReturnGood;
import com.msystem.repository.ReturnGoodRepository;
import com.msystem.service.ReturnGoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @Description: ReturnGoodService的實現類
 * @author: Eker
 * @date: 2023/4/25 下午 03:45
 * @version: V1.0
 */
@Service
public class ReturnGoodServiceImpl implements ReturnGoodService{
    @Autowired
    private ReturnGoodRepository returnGoodRepository;


    @Override
    public List<ReturnGood> queryAllReturnGood(){
        List<ReturnGood> returnGoodList = returnGoodRepository.findAll();
        return returnGoodList;
    }
    @Override
    public Optional<ReturnGood> queryReturnGoodById(Integer id){
        Optional<ReturnGood> returnGood = returnGoodRepository.findById(id);
        return returnGood;
    }
}
