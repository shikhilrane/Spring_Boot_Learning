package com.shikhilrane.shikhil.currencyConverterApp.client.Implementation;

import com.shikhilrane.shikhil.currencyConverterApp.dto.CurrencyResponseDto;
import com.shikhilrane.shikhil.currencyConverterApp.client.CurrencyClient;
import com.shikhilrane.shikhil.currencyConverterApp.entities.CurrencyConversion;
import com.shikhilrane.shikhil.currencyConverterApp.repositories.CurrencyConversionRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class CurrencyClientImpl implements CurrencyClient {

    private final CurrencyConversionRepository currencyConversionRepository;


    private static final Logger log = LoggerFactory.getLogger(CurrencyClientImpl.class);

    @Qualifier("currencyRestClient")
    private final RestClient restClient;

    @Value("${currencyService.api.key}")
    private String apiKey;

    @Override
    public CurrencyResponseDto convert(String from, String to, double units) {

        log.trace("Trying to get currency exchange rate...");
        try {
            log.info("Calling Currency API | from={} to={} units={}", from, to, units);
            ResponseEntity<CurrencyResponseDto> apiResponse = restClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/latest")
                            .queryParam("apikey", apiKey)
                            .queryParam("base_currency", from)
                            .queryParam("currencies", to)
                            .build()
                    )
                    .retrieve()
                    .onStatus(
                            status -> status.is4xxClientError(),
                            (req, res) -> {
                                log.error("4xx error from Currency API | status={} reason={}", res.getStatusCode(), res.getStatusText());
                                throw new RuntimeException("Invalid request / API key issue");
                            }
                    )
                    .onStatus(
                            status -> status.is5xxServerError(),
                            (req, res) -> {
                                log.error("5xx error from Currency API | status={} reason={}", res.getStatusCode(), res.getStatusText());
                                throw new RuntimeException("Currency client is down");
                            }
                    )
                    .toEntity(new ParameterizedTypeReference<CurrencyResponseDto>() {});

            log.debug("Currency API response status={}", apiResponse.getStatusCode());

            CurrencyResponseDto body = apiResponse.getBody();

            if (body == null || body.getData() == null) {
                log.error("Currency API returned empty body | from={} to={}", from, to);
                throw new RuntimeException("Empty response from currency client");
            }

            if (!body.getData().containsKey(to)) {
                log.error("Currency {} not present in response | data={}", to, body.getData());
                throw new RuntimeException("Currency rate not found for: " + to);
            }

            Double rate = body.getData().get(to);
            Double convertedAmount = rate * units;

            log.info(
                    "Currency conversion success | from={} to={} rate={} units={} result={}",
                    from, to, rate, units, convertedAmount
            );

            CurrencyConversion audit = CurrencyConversion.builder()
                    .fromCurrency(from)
                    .toCurrency(to)
                    .units(units)
                    .rate(rate)
                    .convertedAmount(convertedAmount)
                    .build();

            currencyConversionRepository.save(audit);

            return new CurrencyResponseDto(
                    Map.of(to, convertedAmount)
            );

        } catch (Exception e) {
            log.error("Currency conversion failed | from={} to={} units={} error={}", from, to, units, e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }
}
