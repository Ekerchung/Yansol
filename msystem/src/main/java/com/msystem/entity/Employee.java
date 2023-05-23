package com.msystem.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * @Description: Employee的實體類容器
 * @author: Eker
 * @date: 2023/4/20 下午 02:52
 * @version: V1.0
 */
@Data
@Entity
@Table(name = "t_employee")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int eId; //員工id
    @JsonProperty("name")
    private String eName; //員工姓名
    private String gender; //性別
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date birthday; //生日
    private String phone; //電話
    private String address; //地址

}
