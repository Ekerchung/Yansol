package com.msystem.repository;

import com.msystem.entity.Account;
import com.msystem.entity.Company;
import com.msystem.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Description: AccountRepository接口
 * @author: Eker
 * @date: 2023/5/3 下午 03:59
 * @version: V1.0
 */
public interface AccountRepository extends JpaRepository<Account, Integer> {
    Account findByUsernameAndPassword(String username,String password);
}
