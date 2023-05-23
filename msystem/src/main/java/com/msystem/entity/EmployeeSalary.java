package com.msystem.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Description: EmployeeSalary的容器類(尚未完成)
 * @author: Eker
 * @date: 2023/5/9 下午 03:47
 * @version: V1.0
 */
@Data
@Entity
@Table(name = "t_employee_salary")
public class EmployeeSalary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private Long employeeId; //員工id
    private BigDecimal salary; //薪資
    private BigDecimal paid; //實際支付
    @Column(name = "pay_month", nullable = false, updatable = false, columnDefinition = "DATE")
    @DateTimeFormat(pattern = "yyyy-MM")
    @JsonFormat(pattern = "yyyy-MM", timezone = "GMT+8")
    private Date payMonth; //支付月份

}