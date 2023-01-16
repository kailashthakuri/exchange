package com.demo.exchangeservice.modules.pln.nbpapi.dto.table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NBPTableExchangeRate extends BaseNBPTableExchangeRate {
    private Date tradingDate;
    private List<Rate> rates;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Rate {
        private String currency;
        private String code;
        private Double bid;
        private Double ask;
    }
}
