package com.msystem.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @Description: Account的接收類容器
 * @author: Eker
 * @date: 2023/5/5 下午 08:08
 * @version: V1.0
 */
@Entity
@Data
public class AccountDto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int aId;

    @JsonProperty("username")
    private String username;

    @JsonProperty("password")
    private String password;

    @JsonProperty("role")
    private int role;

    @JsonProperty("eId")
    private int eId;
}
