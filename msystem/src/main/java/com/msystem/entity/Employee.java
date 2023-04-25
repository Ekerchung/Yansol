package com.msystem.entity;

import lombok.Data;

import javax.persistence.*;

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
    @GeneratedValue
    private int eId;
    private String eName;

}
