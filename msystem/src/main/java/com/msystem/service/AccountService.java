package com.msystem.service;

import com.msystem.entity.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

/**
 * @Description: Account的Service接口
 * @author: Eker
 * @date: 2023/5/3 下午 04:02
 * @version: V1.0
 */
public interface AccountService {
    //查詢全部帳號
    List<Account> queryAllAccount();

    //登入驗證
    Account login(String username, String password);
    //更新帳號信息
    void updateAccount(Account account);
    //查詢帳號分頁信息
    Page<Account> queryAccountByPage(PageRequest pageable);
    //依帳號名查詢帳號信息
    Page<Account> findByUsernameContaining(String username, PageRequest pageable);
    //依帳號id查詢帳號信息
    Optional<Account> queryAccountById(Integer id);
    //刪除帳號
    void deleteAccount(Integer id);
}
