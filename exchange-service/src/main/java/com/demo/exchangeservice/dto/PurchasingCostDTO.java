package com.demo.exchangeservice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.Date;
import java.util.List;


@RequiredArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PurchasingCostDTO {

    private Date publishedDate;

    @NotEmpty(message = "Currencies cannot be empty")
    @Valid
    private List<CurrencyAmountDTO> currencies;

}



