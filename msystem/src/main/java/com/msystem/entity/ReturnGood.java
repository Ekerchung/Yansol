package com.msystem.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * @Description: ReturnGood的容器類
 * @author: Eker
 * @date: 2023/4/25 下午 03:21
 * @version: V1.0
 */
@Data
@Entity
@Table(name = "t_return_good")
public class ReturnGood {
    @Id
    @GeneratedValue
    private int rId;
    @OneToOne
    @JoinColumn(name = "g_id")
    private Good good;
    private int totalCount;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date returnDate;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date completeDate;
    @OneToOne
    @JoinColumn(name = "complete_employee", referencedColumnName = "eId")
    private Employee employee;
}
