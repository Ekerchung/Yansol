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
    private int companyId;
    private String companyName;
    private String companyTel;
    private String companyAddress;
//    @OneToOne(mappedBy = "company")
//    @JsonIgnore //忽略good實體的查詢，避免死循環
//    @ToString.Exclude
//    @ManyToOne
//    @JoinColumn(name = "g_id")
//    private Good good;

}
