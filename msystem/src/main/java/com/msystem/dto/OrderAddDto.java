package com.msystem.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

/**
 * @Description: OrderDto容器類
 * @author: Eker
 * @date: 2023/5/15 下午 11:14
 * @version: V1.0
 */
@Entity
@Data
public class OrderAddDto {
    @Id
    private int oId;

    @JsonProperty("lineId")
    private String lineId;

    @JsonProperty("totalCount")
    private int totalCount;

    @JsonProperty("remainingCount")
    private int remainingCount;

    @JsonProperty("unitPrice")
    private BigDecimal unitPrice;

    @JsonProperty("eId")
    private int eId;

    @JsonProperty("orderCount")
    private int orderCount;

    @JsonProperty("orderUnitPrice")
    private BigDecimal orderUnitPrice;
}
