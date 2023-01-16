package com.demo.exchangeservice.modules.pln.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@Entity
@Table(name = "pln_exchange")
@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class PlnExchange {

    @EmbeddedId
    private PlnExchangeKey id;

    @Column(nullable = false)
    private String currency;

    @Column(nullable = false)
    private Double bid;

    @Column(nullable = false)
    private Double ask;

    @Temporal(TemporalType.DATE)
    private Date tradingDate;
}
