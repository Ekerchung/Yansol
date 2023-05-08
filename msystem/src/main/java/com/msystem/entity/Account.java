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
    private int aId;
    private String username;
    private String password;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date lastLogin;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date currentLogin;
    private int role = 2; //帳號權限 1:admin，2:user
    @OneToOne
    @JoinColumn(name = "e_id", referencedColumnName = "eId")
    private Employee employee;

}
