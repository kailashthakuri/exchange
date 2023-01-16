package com.demo.exchangeservice.modules.pln.nbpapi;


import com.demo.exchangeservice.modules.pln.nbpapi.dto.NBP;
import com.demo.exchangeservice.modules.pln.nbpapi.dto.rate.NBPExchangeRate;
import com.demo.exchangeservice.modules.pln.nbpapi.dto.table.BaseNBPTableExchangeRate;
import com.demo.exchangeservice.modules.pln.nbpapi.dto.table.NBPTableExchangeRate;
import com.demo.exchangeservice.modules.pln.nbpapi.dto.table.NBPTableMidExchangeRate;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;


public class NBPWebApiUtils {

    public static final String BASE_URI = "http://api.nbp.pl/api/exchangerates";
    public static final String TABLE_URL = BASE_URI + "/tables/";


    public static List<NBPTableMidExchangeRate> getLastMidExchangeRates(NBP.Table table, Integer last) {
        RestTemplate restTemplate = new RestTemplate();
        String url = TABLE_URL.concat(table.getName()).concat("/last/").concat(last.toString());
        return Arrays.asList(restTemplate.getForObject(url, NBPTableMidExchangeRate[].class));
    }

    public static List<NBPTableExchangeRate> getLastExchangeRates(Integer last) {
        RestTemplate restTemplate = new RestTemplate();
        String url = TABLE_URL.concat(NBP.Table.C.getName()).concat("/last/").concat(last.toString());
        return Arrays.asList(restTemplate.getForObject(url, NBPTableExchangeRate[].class));
    }


    public static NBPExchangeRate getExchangeRate(NBP.Table table, String code, Date publishedDate) {
        RestTemplate restTemplate = new RestTemplate();
        String url = BASE_URI.concat("/rates/").concat(table.getName())
                .concat("/").concat(code);
        if (null != publishedDate) {
            url = url.concat("/").concat(formatDate(publishedDate));
        }
        return restTemplate.getForObject(url, NBPExchangeRate.class);
    }


    ////////////////// Private /////////////////
    private static String formatDate(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyy-MM-dd");
        return simpleDateFormat.format(date);
    }
}
