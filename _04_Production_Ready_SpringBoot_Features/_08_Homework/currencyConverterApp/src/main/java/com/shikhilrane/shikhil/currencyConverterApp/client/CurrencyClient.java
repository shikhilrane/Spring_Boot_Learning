package com.shikhilrane.shikhil.currencyConverterApp.client;

import com.shikhilrane.shikhil.currencyConverterApp.dto.CurrencyResponseDto;

public interface CurrencyClient {
    CurrencyResponseDto convert(String from, String to, double units);

}
