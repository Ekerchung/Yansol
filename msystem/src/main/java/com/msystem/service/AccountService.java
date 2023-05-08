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
    List<Account> queryAllAccount();

    Account login(String username, String password);
    void updateAccount(Account account);

    Page<Account> queryAccountByPage(PageRequest pageable);

    Page<Account> findByUsernameContaining(String username, PageRequest pageable);

    Optional<Account> queryAccountById(Integer id);

    void deleteAccount(Integer id);
}
