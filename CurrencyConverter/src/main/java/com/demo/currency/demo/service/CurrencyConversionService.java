package com.demo.currency.demo.service;


import com.demo.currency.demo.entity.Currency;
import com.demo.currency.demo.entity.ExchangeRate;
import com.demo.currency.demo.repo.ExchangeRateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Service
public class CurrencyConversionService {

    @Autowired
    private ExchangeRateRepository repository;

    @Autowired
    private CurrencyConversionAPIService apiService;

    public Currency convert(String sourceCurrency, String targetCurrency, Double amount) {
        ExchangeRate rate = repository.findBySourceCurrencyAndTargetCurrency(sourceCurrency, targetCurrency)
                .filter(r -> ChronoUnit.HOURS.between(r.getLastUpdated(), LocalDateTime.now()) < 1)
                .orElseGet(() -> fetchAndSaveExchangeRate(sourceCurrency, targetCurrency, amount));
        Currency currency = new Currency();
        currency.setValue(Math.round(amount * rate.getRate()));
        currency.setCode(sourceCurrency);
        return currency;
    }

    private ExchangeRate fetchAndSaveExchangeRate(String sourceCurrency, String targetCurrency, Double amount) {
        Double fetchedRate = apiService.fetchExchangeRate(sourceCurrency, targetCurrency, amount);
        ExchangeRate exchangeRate = repository.findBySourceCurrencyAndTargetCurrency(sourceCurrency, targetCurrency)
                .orElse(new ExchangeRate());
        exchangeRate.setSourceCurrency(sourceCurrency);
        exchangeRate.setTargetCurrency(targetCurrency);
        exchangeRate.setRate(fetchedRate);
        exchangeRate.setLastUpdated(LocalDateTime.now());
        return repository.save(exchangeRate);
    }
}
