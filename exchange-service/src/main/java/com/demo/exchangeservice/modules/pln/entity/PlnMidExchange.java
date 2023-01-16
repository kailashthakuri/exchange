package com.demo.exchangeservice.modules.pln.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "pln_mid_exchange")
@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class PlnMidExchange {
    @EmbeddedId
    private PlnExchangeKey id;

    @Column(nullable = false)
    private String currency;

    @Column(nullable = false)
    private Double mid;

}


