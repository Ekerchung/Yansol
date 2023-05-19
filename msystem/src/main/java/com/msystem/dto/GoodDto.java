package com.msystem.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.msystem.entity.Company;
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
    private int gId;

    @JsonProperty("lineId")
    private String lineId;

    @JsonProperty("lineName")
    private String lineName;

    @JsonProperty("lineType")
    private String lineType;

    @JsonProperty("pDate")
    @DateTimeFormat(pattern = "yyyy-MM-DD")
    private Date pDate;

    @JsonProperty("sDate")
    @DateTimeFormat(pattern = "yyyy-MM-DD")
    private Date sDate;

    @JsonProperty("totalCount")
    private int totalCount;

    @JsonProperty("state")
    private Integer state;

    @JsonProperty("unitPrice")
    private BigDecimal unitPrice;

    @JsonProperty("companyName")
    private Integer companyName;

    @JsonProperty("companyId")
    private Integer companyId;


}
