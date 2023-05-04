package com.msystem.controller;

import com.msystem.entity.Account;
import com.msystem.service.AccountService;
import com.msystem.utils.JwtTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

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
            response.put("role",account.getRole());

            //將狀態跟token、用戶信息返回客戶端
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("帳號密碼有誤");
    }
}
