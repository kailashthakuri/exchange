package com.demo.exchangeservice.service;

import com.demo.exchangeservice.dto.CurrencyAmountDTO;
import com.demo.exchangeservice.dto.TotalCostDTO;
import com.demo.exchangeservice.model.ExchangeRate;

import java.util.Date;
import java.util.List;

public interface ExchangeRateService {
    ExchangeRate getExchangeRate(String code, Date publishedDate);

    TotalCostDTO getTotalPurchasingCost(List<CurrencyAmountDTO> amounts, Date publishedDate);
}
