package com.shikhilrane.shikhil.currencyConverterApp.service.Implementation;

import com.shikhilrane.shikhil.currencyConverterApp.client.CurrencyClient;
import com.shikhilrane.shikhil.currencyConverterApp.dto.CurrencyConversionDto;
import com.shikhilrane.shikhil.currencyConverterApp.dto.CurrencyResponseDto;
import com.shikhilrane.shikhil.currencyConverterApp.entities.CurrencyConversion;
import com.shikhilrane.shikhil.currencyConverterApp.repositories.CurrencyConversionRepository;
import com.shikhilrane.shikhil.currencyConverterApp.service.CurrencyConversionService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CurrencyConversionServiceImpl implements CurrencyConversionService {

    private final CurrencyClient currencyClient;
    private final CurrencyConversionRepository repository;
    private final ModelMapper modelMapper;

    @Override
    public CurrencyResponseDto convert(String from, String to, double units) {
        return currencyClient.convert(from, to, units);
    }

    @Override
    public List<CurrencyConversionDto> getAll() {
        return repository.findAll()
                .stream()
                .map(entity -> modelMapper.map(entity, CurrencyConversionDto.class))
                .toList();
    }

    @Override
    public CurrencyConversionDto getById(Long id) {
        CurrencyConversion entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Conversion not found"));

        return modelMapper.map(entity, CurrencyConversionDto.class);
    }

    @Override
    public CurrencyConversionDto update(Long id, CurrencyConversionDto dto) {
        CurrencyConversion existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Conversion not found"));

        modelMapper.map(dto, existing); // 🔥 DTO → Entity

        return modelMapper.map(
                repository.save(existing),
                CurrencyConversionDto.class
        );
    }

    @Override
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Conversion not found");
        }
        repository.deleteById(id);
    }

    public CurrencyConversionDto patch(Long id, CurrencyConversionDto dto) {

        CurrencyConversion entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Conversion not found"));

        if (dto.getFromCurrency() != null) {
            entity.setFromCurrency(dto.getFromCurrency());
        }

        if (dto.getToCurrency() != null) {
            entity.setToCurrency(dto.getToCurrency());
        }

        if (dto.getUnits() > 0) {
            entity.setUnits(dto.getUnits());
            entity.setConvertedAmount(entity.getRate() * dto.getUnits());
        }

        if (dto.getRate() > 0) {
            entity.setRate(dto.getRate());
            entity.setConvertedAmount(dto.getRate() * entity.getUnits());
        }

        CurrencyConversion saved = repository.save(entity);
        return modelMapper.map(saved, CurrencyConversionDto.class);
    }
}
