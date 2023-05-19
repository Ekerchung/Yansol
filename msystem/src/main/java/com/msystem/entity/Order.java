package com.msystem.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    private int state = 1; //1：生產中 2：完成


    public void setOTotalPrice() {
        //使用Optional避免空指針異常
        this.oTotalPrice = Optional.ofNullable(oUnitPrice)
                .map(price -> price.multiply(BigDecimal.valueOf(oCount)))
                .orElse(BigDecimal.ZERO);
    }

    public void setOUnitPrice(BigDecimal UnitPrice) {
        this.oUnitPrice = UnitPrice;
        setOTotalPrice();  // 在設置 oUnitPrice 屬性時調用 setoTotalPrice() 方法
    }

    public void setOCount(int oCount) {
        this.oCount = oCount;
        setOTotalPrice();  // 在設置 oCount 屬性時調用 setoTotalPrice() 方法
    }
}
