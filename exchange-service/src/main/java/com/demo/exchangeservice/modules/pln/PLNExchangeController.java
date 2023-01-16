package com.demo.exchangeservice.modules.pln;

import com.demo.exchangeservice.dto.CurrencyAmountDTO;
import com.demo.exchangeservice.dto.PurchasingCostDTO;
import com.demo.exchangeservice.dto.TotalCostDTO;
import com.demo.exchangeservice.dto.common.ResponseDTO;
import com.demo.exchangeservice.service.ExchangeRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Date;

@RestController()
@RequestMapping("pln")
public class PLNExchangeController {

    @Qualifier("plnExchangeService")
    @Autowired
    private ExchangeRateService exchangeRateService;

    @PostMapping("/total-purchasing-cost")
    public ResponseEntity<ResponseDTO> getPurchasingCost(@RequestBody @Valid PurchasingCostDTO request) {
        TotalCostDTO currencyAmount = this.exchangeRateService.getTotalPurchasingCost(
                request.getCurrencies(),
                request.getPublishedDate()
        );
        return new ResponseEntity<>(
                ResponseDTO.successResponse(currencyAmount),
                HttpStatus.OK
        );
    }

    @GetMapping("/rate/{code}/{publishedDate}")
    public ResponseEntity<ResponseDTO> getExchangeRate(
            @PathVariable("code") String code,
            @PathVariable("publishedDate") Date publishedDate
    ) {

        return new ResponseEntity<>(
                ResponseDTO.successResponse(this.exchangeRateService.getExchangeRate(code, publishedDate)),
                HttpStatus.OK
        );
    }
}
