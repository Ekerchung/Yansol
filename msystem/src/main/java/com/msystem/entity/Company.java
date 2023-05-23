package com.msystem.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

/**
 * @Description: Company的實體類容器
 * @author: Eker
 * @date: 2023/4/21 上午 09:29
 * @version: V1.0
 */
@Entity
@Data
@Table(name = "t_company")
public class Company {
    @Id
    @GeneratedValue
    private int companyId; //廠商id
    private String companyName; //廠商名稱
    private String companyTel; //廠商電話
    private String companyAddress; //廠商地址

}
