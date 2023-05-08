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

    @Override
    public Account login(String username,String password){
        Account account = accountRepository.findByUsernameAndPassword(username, password);
        return account;
    }

    @Override
    public List<Account> queryAllAccount() {
        List<Account> accountList = accountRepository.findAll();
        return accountList;
    }

    @Override
    public Page<Account> queryAccountByPage(PageRequest pageable) {
        Page<Account> accountPage = accountRepository.findAll(pageable);
        return accountPage;
    }

    @Override
    public Page<Account> findByUsernameContaining(String username, PageRequest pageable) {
        Page<Account> accountPage = accountRepository.findByUsernameContaining(username, pageable);
        return accountPage;
    }

    @Override
    public Optional<Account> queryAccountById(Integer id) {
        Optional<Account> account = accountRepository.findById(id);
        return account;
    }

    @Override
    public void deleteAccount(Integer id) {
        accountRepository.deleteById(id);
    }

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
