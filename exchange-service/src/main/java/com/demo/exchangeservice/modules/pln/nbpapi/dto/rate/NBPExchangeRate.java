package com.demo.exchangeservice.modules.pln.nbpapi.dto.rate;

import com.demo.exchangeservice.modules.pln.nbpapi.dto.NBP;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NBPExchangeRate {
    private NBP.Table table;
    private String currency;
    private String code;
    private List<Rate> rates;


    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Rate {
        private String no;
        private Date effectiveDate;
        private Double mid;
    }
}
