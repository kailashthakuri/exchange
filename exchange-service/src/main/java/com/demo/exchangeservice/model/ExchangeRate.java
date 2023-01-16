package com.demo.exchangeservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class ExchangeRate {
    private Double rate;
    private String currency;
    private String code;
    private Date effectiveDate;
}
