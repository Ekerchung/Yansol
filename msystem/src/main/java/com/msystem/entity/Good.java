package com.msystem.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    private int state = 1; // 1:已收貨，2:生產中，3：待發貨，3：已發貨

    public void setTotalPrice() {
        //使用Optional避免空指針異常
        this.totalPrice = Optional.ofNullable(unitPrice)
                .map(price -> price.multiply(BigDecimal.valueOf(totalCount)))
                .orElse(BigDecimal.ZERO);
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
        setTotalPrice();  // 在設置 unitPrice 屬性時調用 setTotalPrice() 方法
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
        setTotalPrice();  // 在設置 totalCount 屬性時調用 setTotalPrice() 方法
    }
}
