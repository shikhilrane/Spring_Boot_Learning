package com.shikhilrane.shikhil.currencyConverterApp.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CurrencyConversionDto {

    private Long id;

    private String fromCurrency;

    private String toCurrency;

    private double units;

    private double rate;

    private double convertedAmount;
}
