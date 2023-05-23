package com.msystem.service.Impl;

import com.msystem.entity.Account;
import com.msystem.entity.Employee;
import com.msystem.repository.AccountRepository;
import com.msystem.repository.EmployeeRepository;
import com.msystem.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
    @Autowired
    EmployeeRepository employeeRepository;

    /**
     * @titile: login
     * @description: 用戶登入
     * @param username 帳號
     * @param password 密碼
     * @return: Account 帳號信息
     * @author: Eker
     * @date: 2023/5/23 下午 03:21
     */
    @Override
    public Account login(String username,String password){
        Account account = accountRepository.findByUsernameAndPassword(username, password);
        return account;
    }

    /**
     * @titile: queryAllAccount
     * @description: 獲取全部帳號信息
     * @return: List<Account> 帳號List列表
     * @author: Eker
     * @date: 2023/5/23 下午 03:22
     */
    @Override
    public List<Account> queryAllAccount() {
        List<Account> accountList = accountRepository.findAll();
        return accountList;
    }

    /**
     * @titile: queryAccountByPage
     * @description: 查詢全部帳號的分頁信息
     * @param pageable 分頁參數
     * @return: Page<Account> 帳號分頁信息
     * @author: Eker
     * @date: 2023/5/23 下午 03:23
     */
    @Override
    public Page<Account> queryAccountByPage(PageRequest pageable) {
        Page<Account> accountPage = accountRepository.findAll(pageable);
        return accountPage;
    }

    /**
     * @titile: findByUsernameContaining
     * @description: 依據帳號查詢帳號信息
     * @param username 帳號
     * @param pageable 分頁信息
     * @return: Page<Account> 帳號的分頁信息
     * @author: Eker
     * @date: 2023/5/23 下午 03:23
     */
    @Override
    public Page<Account> findByUsernameContaining(String username, PageRequest pageable) {
        Page<Account> accountPage = accountRepository.findByUsernameContaining(username, pageable);
        return accountPage;
    }

    /**
     * @titile: queryAccountById
     * @description: 依據id查詢帳號
     * @param id 帳號id
     * @return: Optional<Account> 帳號信息
     * @author: Eker
     * @date: 2023/5/23 下午 03:25
     */
    @Override
    public Optional<Account> queryAccountById(Integer id) {
        Optional<Account> account = accountRepository.findById(id);
        return account;
    }

    /**
     * @titile: deleteAccount
     * @description: 根據id刪除帳號
     * @param id 帳號id
     * @author: Eker
     * @date: 2023/5/23 下午 03:26
     */
    @Override
    public void deleteAccount(Integer id) {
        accountRepository.deleteById(id);
    }

    /**
     * @titile: updateAccount
     * @description: 更新帳號信息
     * @param acc 帳號信息
     * @author: Eker
     * @date: 2023/5/23 下午 03:26
     */
    @Override
    public void updateAccount(Account acc){

        if(acc.getAId() != 0){
            //若傳入的參數有eId值，為修改員工操作
            Optional<Account> optionalAccount = queryAccountById(acc.getAId());
            Account account = optionalAccount.orElse(null);
            assert account != null;
            account.setUsername(acc.getUsername());
            account.setPassword(acc.getPassword());
            account.setEmployee(acc.getEmployee());
            account.setRole(acc.getRole());
            accountRepository.save(account);
        }else {
            ////若傳入的aId值為0，為新增員工操作
            accountRepository.save(acc);
        }
    }
}
