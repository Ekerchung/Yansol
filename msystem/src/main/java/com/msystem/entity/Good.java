package com.msystem.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @Description: Good的實體類容器
 * @author: Eker
 * @date: 2023/4/20 下午 06:45
 * @version: V1.0
 */
@Entity
@Data
@Table(name = "t_good")
public class Good {
    @Id
    @GeneratedValue
    private int gId;
    private String lineId;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date pDate;
    @ManyToOne
//    @JoinColumn(name = "companyId", referencedColumnName = "company_id")
    @JoinColumn(name = "company_id")
    private Company company;
    private String lineName;
    private String lineType;
    private int totalCount;
    private BigDecimal unitPrice;
    private BigDecimal totalPrice;
    private BigDecimal totalProfit;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date sDate;
}
