package com.msystem.service;

import com.msystem.entity.Account;

/**
 * @Description: Account的Service接口
 * @author: Eker
 * @date: 2023/5/3 下午 04:02
 * @version: V1.0
 */
public interface AccountService {
    Account login(String username, String password);
}
