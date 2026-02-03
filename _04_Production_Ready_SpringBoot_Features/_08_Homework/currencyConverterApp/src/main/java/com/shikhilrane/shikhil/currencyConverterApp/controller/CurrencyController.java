package com.shikhilrane.shikhil.currencyConverterApp.controller;

import com.shikhilrane.shikhil.currencyConverterApp.dto.CurrencyConversionDto;
import com.shikhilrane.shikhil.currencyConverterApp.dto.CurrencyResponseDto;
import com.shikhilrane.shikhil.currencyConverterApp.client.CurrencyClient;
import com.shikhilrane.shikhil.currencyConverterApp.service.CurrencyConversionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/currency")
public class CurrencyController {

    private final CurrencyClient currencyClient;
    private final CurrencyConversionService currencyConversionService;

    @GetMapping("/convert")
    public ResponseEntity<CurrencyResponseDto> convertCurrency(
            @RequestParam String from,
            @RequestParam String to,
            @RequestParam double units
    ) {
        return ResponseEntity.ok(currencyClient.convert(from, to, units));
    }

    // READ ALL
    @GetMapping
    public ResponseEntity<List<CurrencyConversionDto>> getAll() {
        return ResponseEntity.ok(currencyConversionService.getAll());
    }

    // READ ONE
    @GetMapping("/{id}")
    public ResponseEntity<CurrencyConversionDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(currencyConversionService.getById(id));
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<CurrencyConversionDto> update(
            @PathVariable Long id,
            @RequestBody CurrencyConversionDto dto
    ) {
        return ResponseEntity.ok(currencyConversionService.update(id, dto));
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        currencyConversionService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CurrencyConversionDto> patchConversion(
            @PathVariable Long id,
            @RequestBody CurrencyConversionDto dto
    ) {
        return ResponseEntity.ok(currencyConversionService.patch(id, dto));
    }
}
