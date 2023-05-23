package com.msystem.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * @Description: Account的容器類
 * @author: Eker
 * @date: 2023/5/3 下午 03:47
 * @version: V1.0
 */
@Entity
@Data
@Table(name = "t_account")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int aId; //帳號id
    private String username; //帳號名
    private String password; //帳號密碼
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date lastLogin; //上次登入時間
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date currentLogin; //最新登入時間
    private int role = 2; //帳號權限 1:admin，2:user
    @OneToOne
    @JoinColumn(name = "e_id", referencedColumnName = "eId")
    private Employee employee; //員工信息

}
