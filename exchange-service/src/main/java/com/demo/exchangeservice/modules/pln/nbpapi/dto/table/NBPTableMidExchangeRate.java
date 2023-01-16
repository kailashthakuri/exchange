package com.demo.exchangeservice.modules.pln.nbpapi.dto.table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class NBPTableMidExchangeRate extends BaseNBPTableExchangeRate {
    private List<Rate> rates;

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    public static class Rate {
        private String currency;
        private String code;
        private Double mid;
    }
}
