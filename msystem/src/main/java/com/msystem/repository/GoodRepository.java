package com.msystem.repository;

import com.msystem.entity.Good;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @Description: GoodRepository接口
 * @author: Eker
 * @date: 2023/4/20 下午 11:52
 * @version: V1.0
 */
public interface GoodRepository  extends JpaRepository<Good, Integer> {
}