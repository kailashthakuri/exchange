package com.demo.exchangeservice.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CurrencyAmountDTO {
    @NotEmpty(message = "Code can not be empty")
    private String code;

    @NotNull(message = "Amount can not be null")
    @Min(value = 0, message = "Should have at least 0 value")
    private Integer amount;
}
