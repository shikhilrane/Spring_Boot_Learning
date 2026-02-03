package com.shikhilrane.shikhil.currencyConverterApp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CurrencyResponseDto {
    private Map<String, Double> data;
}
