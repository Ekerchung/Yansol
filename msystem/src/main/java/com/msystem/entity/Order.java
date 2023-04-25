package com.msystem.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Description: Order的實體類容器
 * @author: Eker
 * @date: 2023/4/20 下午 10:58
 * @version: V1.0
 */
@Data
@Entity
@Table(name = "t_order")
public class Order {
    @Id
    @GeneratedValue
    private int oId;
    @ManyToOne
    @JoinColumn(name = "g_id")
    private Good good;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date oDate;
    private int oCount;
    private BigDecimal oUnitPrice;
    private BigDecimal oTotalPrice;
    @ManyToOne
    @JoinColumn(name = "e_id")
    private Employee employee;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date comDate;

}
