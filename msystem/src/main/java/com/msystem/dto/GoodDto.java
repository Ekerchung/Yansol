package com.msystem.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Description: Good的DTO類
 * @author: Eker
 * @date: 2023/5/11 上午 01:35
 * @version: V1.0
 */
@Entity
@Data
public class GoodDto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int aId;

    @JsonProperty("line_id")
    private String lineId;

    @JsonProperty("line_name")
    private String lineName;

    @JsonProperty("line_type")
    private String lineType;

    @JsonProperty("p_date")
    @DateTimeFormat(pattern = "yyyy-MM-DD")
    private Date pDate;

    @JsonProperty("total_count")
    private int totalCount;

    @JsonProperty("unit_price")
    private BigDecimal unitPrice;

    @JsonProperty("company_name")
    private Integer companyId;
}
