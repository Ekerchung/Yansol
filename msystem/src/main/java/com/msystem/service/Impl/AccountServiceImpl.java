package com.msystem.service.Impl;

import com.msystem.entity.Account;
import com.msystem.repository.AccountRepository;
import com.msystem.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description: AccountService的實體類對象
 * @author: Eker
 * @date: 2023/5/3 下午 04:02
 * @version: V1.0
 */
@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    AccountRepository accountRepository;

    @Override
    public Account login(String username,String password){
        Account account = accountRepository.findByUsernameAndPassword(username, password);
        return account;
    }
}
