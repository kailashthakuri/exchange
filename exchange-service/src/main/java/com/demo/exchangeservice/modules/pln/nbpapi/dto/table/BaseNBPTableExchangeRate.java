package com.demo.exchangeservice.modules.pln.nbpapi.dto.table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BaseNBPTableExchangeRate {
    private String table;
    private String no;
    private Date effectiveDate;
}
