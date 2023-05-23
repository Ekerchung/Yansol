package com.msystem.repository;

import com.msystem.entity.Account;
import com.msystem.entity.Company;
import com.msystem.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Description: AccountRepository接口
 * @author: Eker
 * @date: 2023/5/3 下午 03:59
 * @version: V1.0
 */
public interface AccountRepository extends JpaRepository<Account, Integer> {
    //確認輸入的帳號密碼是否正確
    Account findByUsernameAndPassword(String username,String password);
    //依帳號名查詢帳號信息
    Page<Account> findByUsernameContaining(String username, Pageable pageable);

}
