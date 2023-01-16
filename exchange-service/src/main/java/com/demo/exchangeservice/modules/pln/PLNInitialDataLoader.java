package com.demo.exchangeservice.modules.pln;

import com.demo.exchangeservice.modules.pln.entity.PlnExchange;
import com.demo.exchangeservice.modules.pln.entity.PlnExchangeKey;
import com.demo.exchangeservice.modules.pln.entity.PlnMidExchange;
import com.demo.exchangeservice.modules.pln.nbpapi.NBPWebApiUtils;
import com.demo.exchangeservice.modules.pln.nbpapi.dto.NBP;
import com.demo.exchangeservice.modules.pln.nbpapi.dto.table.NBPTableExchangeRate;
import com.demo.exchangeservice.modules.pln.nbpapi.dto.table.NBPTableMidExchangeRate;
import com.demo.exchangeservice.modules.pln.repoistory.PlnExchangeRepository;
import com.demo.exchangeservice.modules.pln.repoistory.PlnMidExchangeRepository;
import com.demo.exchangeservice.utils.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class PLNInitialDataLoader implements ApplicationRunner {

    private final PlnExchangeRepository exchangeRepository;
    private final PlnMidExchangeRepository midExchangeRepository;

    @Autowired
    public PLNInitialDataLoader(PlnExchangeRepository exchangeRepository, PlnMidExchangeRepository midExchangeRepository) {
        this.exchangeRepository = exchangeRepository;
        this.midExchangeRepository = midExchangeRepository;
    }

    private static final Integer LAST = 10;


    public void run(ApplicationArguments args) {
        // persist initial data only if table is empty
        if (this.exchangeRepository.count() == 0) {
            Map<Date, Map<String, PlnExchange>> map = new HashMap<>();
            // persists all data from Table A and Table B for mid exchange rate.
            List<PlnMidExchange> plnMidExchanges = new ArrayList<>();
            addMidExchanges(NBP.Table.A, LAST, plnMidExchanges);
            addMidExchanges(NBP.Table.B, LAST, plnMidExchanges);
            this.midExchangeRepository.saveAll(plnMidExchanges);

            // persists all data from Table C for bid and ask exchange rate
            this.exchangeRepository.saveAll(addExchanges(LAST));
        }
    }

    //////////////////////////// Private ///////////////////////////


    private void addMidExchanges(NBP.Table table, Integer last, List<PlnMidExchange> plnMidExchanges) {
        List<NBPTableMidExchangeRate> currencyExchangeRatesForTable = NBPWebApiUtils.getLastMidExchangeRates(table, last);
        if (!CollectionUtils.isEmpty(currencyExchangeRatesForTable)) {
            currencyExchangeRatesForTable.forEach(nbpTableMidExchangeRate -> {
                List<PlnMidExchange> collect = nbpTableMidExchangeRate.getRates().stream().map(rate ->
                        PlnMidExchange.builder()
                                .id(new PlnExchangeKey(rate.getCode(), nbpTableMidExchangeRate.getEffectiveDate()))
                                .mid(rate.getMid())
                                .currency(rate.getCurrency())
                                .build()).collect(Collectors.toList());
                plnMidExchanges.addAll(collect);
            });
        }
    }


    // update PlnExchange with Table C. bid and ask
    private List<PlnExchange> addExchanges(Integer last) {
        List<PlnExchange> plnExchanges = new ArrayList<>();
        List<NBPTableExchangeRate> exchangeRates = NBPWebApiUtils.getLastExchangeRates(last);
        if (!CollectionUtils.isEmpty(exchangeRates)) {
            exchangeRates.forEach(nbpTableMidExchangeRate -> {
                List<PlnExchange> collect = nbpTableMidExchangeRate.getRates().stream().map(rate -> PlnExchange.builder()
                        .id(new PlnExchangeKey(rate.getCode(), nbpTableMidExchangeRate.getEffectiveDate()))
                        .currency(rate.getCurrency())
                        .bid(rate.getBid())
                        .ask(rate.getAsk())
                        .tradingDate(nbpTableMidExchangeRate.getTradingDate())
                        .build()).collect(Collectors.toList());
                plnExchanges.addAll(collect);
            });
        }
        return plnExchanges;
    }
}
