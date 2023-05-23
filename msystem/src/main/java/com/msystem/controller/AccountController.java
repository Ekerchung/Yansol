package com.msystem.controller;

import com.msystem.dto.AccountDto;
import com.msystem.entity.Account;
import com.msystem.entity.Employee;
import com.msystem.service.AccountService;
import com.msystem.service.EmployeeService;
import com.msystem.utils.JwtTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Description: Account的控制類
 * @author: Eker
 * @date: 2023/5/3 下午 04:00
 * @version: V1.0
 */
@CrossOrigin
@RestController
@RequestMapping(value="/api")
public class AccountController {
    @Autowired
    AccountService accountService;
    @Autowired
    EmployeeService employeeService;

    /**
     * @titile: login
     * @description: 驗證登入
     * @param username 帳號
     * @param password 密碼
     * @return: ResponseEntity 響應給前端狀態碼及body資訊
     * @author: Eker
     * @date: 2023/5/23 下午 04:26
     */
    @GetMapping(value = "/login")
    public ResponseEntity login(@RequestParam String username,@RequestParam String password){
        Account account = accountService.login(username, password);
        if(account != null) {
            //調用JwtTokenUtils.generateToken()取得token
            String token = JwtTokenUtils.generateToken(account);
//            String user = account.getUsername();
            Map<String, Object> response = new HashMap<>();
            response.put("token",token);
            response.put("username",account.getUsername());
            response.put("lastLoginTime",account.getCurrentLogin());//上次登入時間
            response.put("role",account.getRole());
            response.put("employee",account.getEmployee());
            account.setLastLogin(account.getCurrentLogin());
            account.setCurrentLogin(new Date());
            accountService.updateAccount(account);
            //將狀態跟token、用戶信息返回客戶端
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("帳號密碼有誤");
    }

    /**
     * @titile: queryAllAccount
     * @description: 查詢全部帳號信息
     * @return: List<Account> 帳號信息列表
     * @author: Eker
     * @date: 2023/5/23 下午 04:27
     */
    @GetMapping(value = "/account")
    public List<Account> queryAllAccount(){
        List<Account> accountList = accountService.queryAllAccount();
        return accountList;
    }
    /**
     * @titile: queryAccountByPage
     * @description: 查詢帳號分頁信息
     * @param pageNum 頁碼
     * @param queryKeyWord 查詢關鍵字
     * @return: Page<Account> 帳號分頁信息
     * @author: Eker
     * @date: 2023/5/23 下午 04:27
     */
    @GetMapping(value = "/page/account/")
    public Page<Account> queryAccountByPage(@RequestParam Integer pageNum, @RequestParam(required = false) String queryKeyWord){
        //默認頁碼為0，PageRequest頁碼從0開始
        int page = 0;
        if(pageNum != null){
            page = pageNum - 1;
        }
        //默認每頁顯示10個數據
        int size = 10;
        //默認排序依照id
        String sortBy = "aId";
        PageRequest pageable = PageRequest.of(page, size, Sort.by(sortBy));
        Page<Account> accountPage = null;
        if(queryKeyWord == null){
            accountPage = accountService.queryAccountByPage(pageable);
        }else {
            String username = queryKeyWord;
            accountPage = accountService.findByUsernameContaining(username,pageable);
        }
        return accountPage;
    }
    /**
     * @titile: queryAccountById
     * @description: 依id查詢帳號信息
     * @param id 帳號id
     * @return: Account 帳號信息
     * @author: Eker
     * @date: 2023/5/23 下午 04:36
     */
    @GetMapping(value = "/account/{id}")
    public Account queryAccountById(@PathVariable("id") Integer id){
        Optional<Account> account = accountService.queryAccountById(id);
        return account.orElse(null);
    }
    /**
     * @titile: updateAccount
     * @description: 更新帳號信息
     * @param accountDto 帳號信息
     * @author: Eker
     * @date: 2023/5/23 下午 04:37
     */
    @PutMapping(value = "/account")
    public void updateAccount(@RequestBody AccountDto accountDto){
        System.out.println(accountDto);
        Optional<Employee> optionalEmployee = employeeService.queryEmployeeById(accountDto.getEId());
        Employee employee = optionalEmployee.orElse(null);
        Account account = new Account();
        account.setUsername(accountDto.getUsername());
        account.setAId(accountDto.getAId());
        account.setPassword(accountDto.getPassword());
        account.setRole(accountDto.getRole());
        account.setEmployee(employee);
        accountService.updateAccount(account);
    }
    /**
     * @titile: deleteAccount
     * @description: 刪除帳號信息
     * @param id 帳號id
     * @author: Eker
     * @date: 2023/5/23 下午 04:37
     */
    @DeleteMapping(value = "/account/{id}")
    public void deleteAccount(@PathVariable("id") Integer id){
        accountService.deleteAccount(id);
    }
}
