package com.demo.exchangeservice.modules.pln.service;

import com.demo.exchangeservice.dto.CurrencyAmountDTO;
import com.demo.exchangeservice.dto.TotalCostDTO;
import com.demo.exchangeservice.dto.common.ErrorCodes;
import com.demo.exchangeservice.exception.DataException;
import com.demo.exchangeservice.model.ExchangeRate;
import com.demo.exchangeservice.modules.pln.PLNConstants;
import com.demo.exchangeservice.modules.pln.entity.PlnExchange;
import com.demo.exchangeservice.modules.pln.entity.PlnExchangeKey;
import com.demo.exchangeservice.modules.pln.entity.PlnMidExchange;
import com.demo.exchangeservice.modules.pln.repoistory.PlnExchangeRepository;
import com.demo.exchangeservice.modules.pln.repoistory.PlnMidExchangeRepository;
import com.demo.exchangeservice.service.ExchangeRateService;
import com.demo.exchangeservice.utils.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Qualifier("plnExchangeService")
@Service
public class PlnExchangeServiceImpl implements ExchangeRateService {

    private final PlnExchangeRepository exchangeRepository;
    private final PlnMidExchangeRepository midExchangeRepository;

    @Autowired
    public PlnExchangeServiceImpl(PlnExchangeRepository exchangeRepository, PlnMidExchangeRepository midExchangeRepository) {
        this.exchangeRepository = exchangeRepository;
        this.midExchangeRepository = midExchangeRepository;
    }

    /**
     * Returns sell exchange rate for provided code and published date
     */
    @Override
    public ExchangeRate getExchangeRate(String code, Date publishedDate) throws DataException {
        Optional<PlnExchange> byId = this.exchangeRepository.findById(new PlnExchangeKey(code, publishedDate));
        if (byId.isPresent()) {
            PlnExchange exchange = byId.get();
            return ExchangeRate.builder()
                    .effectiveDate(exchange.getId().getEffectiveDate())
                    .code(exchange.getId().getCode())
                    .currency(exchange.getCurrency())
                    .rate(exchange.getAsk()).build();

        }
        throw new DataException("Rate not found for provided parameters", ErrorCodes.DATA_NOT_FOUND);
    }

    /**
     * calculates the total amount in PLN based on mid exchange rate for given currency and amount.
     */
    @Override
    public TotalCostDTO getTotalPurchasingCost(List<CurrencyAmountDTO> amounts, Date publishedDate) throws DataException {

        // prepare keys
        List<PlnExchangeKey> keys = amounts.stream()
                .map(amount -> new PlnExchangeKey(amount.getCode(), publishedDate))
                .collect(Collectors.toList());
        // collect MidExchanges in map
        Map<String, PlnMidExchange> mapMidExchanges = new HashMap<>();
        this.midExchangeRepository.findAllById(keys)
                .forEach(plnMidExchange -> mapMidExchanges.put(plnMidExchange.getId().getCode(), plnMidExchange));

        if (!CollectionUtils.isEmpty(mapMidExchanges.keySet())) {
            List<String> notFoundCurrencies = new ArrayList<>();
            // calculate total amount in PLA based on mid exchange rate.
            double totalSum = 0.0;
            for (CurrencyAmountDTO amount : amounts) {
                String code = amount.getCode();
                if (mapMidExchanges.containsKey(code)) {
                    totalSum += amount.getAmount() * mapMidExchanges.get(code).getMid();
                } else {
                    // collect code whose exchange rate not found
                    notFoundCurrencies.add(code);
                }
            }
            TotalCostDTO totalCostDTO = new TotalCostDTO();
            totalCostDTO.setTotal(totalSum);
            totalCostDTO.setCode(PLNConstants.PLN_CODE);
            if (!CollectionUtils.isEmpty(notFoundCurrencies)) {
                totalCostDTO.setMeta(Collections.singletonMap("missingCurrencies", notFoundCurrencies));
            }
            return totalCostDTO;
        }
        throw new DataException("Could not found exchange rate for any of provided currencies", ErrorCodes.DATA_NOT_FOUND);
    }
}
