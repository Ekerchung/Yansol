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
    private int gId; //線材id
    private String lineId; //線材編號
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date pDate; //進貨日期
    @ManyToOne
//    @JoinColumn(name = "companyId", referencedColumnName = "company_id")
    @JoinColumn(name = "company_id")
    private Company company; //廠商信息
    private String lineName; //線材品名
    private String lineType; //線材端子
    private int totalCount; //總數量
    private int remainingCount; //待分配數量
    private BigDecimal unitPrice; //線材單價
    private BigDecimal totalPrice; //線材總價
    private BigDecimal totalProfit; //線材淨利
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date sDate; //發貨時間
    private int state = 1; // 1:已收貨，2:生產中，3：待發貨，4：已發貨

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
