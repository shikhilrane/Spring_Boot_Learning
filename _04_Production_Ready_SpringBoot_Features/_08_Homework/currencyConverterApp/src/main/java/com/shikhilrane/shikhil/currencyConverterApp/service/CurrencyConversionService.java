package com.shikhilrane.shikhil.currencyConverterApp.service;

import com.shikhilrane.shikhil.currencyConverterApp.dto.CurrencyConversionDto;
import com.shikhilrane.shikhil.currencyConverterApp.dto.CurrencyResponseDto;

import java.util.List;

public interface CurrencyConversionService {

    CurrencyResponseDto convert(String from, String to, double units);

    List<CurrencyConversionDto> getAll();

    CurrencyConversionDto getById(Long id);

    CurrencyConversionDto update(Long id, CurrencyConversionDto dto);

    void delete(Long id);

    CurrencyConversionDto patch(Long id, CurrencyConversionDto dto);
}
