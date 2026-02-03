package com.shikhilrane.shikhil.currencyConverterApp.repositories;

import com.shikhilrane.shikhil.currencyConverterApp.entities.CurrencyConversion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CurrencyConversionRepository extends JpaRepository<CurrencyConversion, Long> {
}