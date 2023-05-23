package com.msystem.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.msystem.entity.Employee;
import com.msystem.entity.Good;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Description: Order的Dto容器
 * @author: Eker
 * @date: 2023/5/15 下午 11:14
 * @version: V1.0
 */
@Entity
@Data
public class OrderUpdateDto {
    @Id
    @JsonProperty("oid")
    private Integer oId;

    @JsonProperty("ocount")
    private Integer count;

    @JsonProperty("ounitPrice")
    private BigDecimal unitPrice;

    @JsonProperty("eid")
    private Integer employeeId;

    @JsonProperty("comDate")
    private Date comDate;
}
