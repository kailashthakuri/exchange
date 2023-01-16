package com.demo.exchangeservice.modules.pln.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class PlnExchangeKey implements Serializable {

    @Column(name = "code", nullable = false)
    private String code;

    @Column(name = "effectiveDate", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date effectiveDate;

}
