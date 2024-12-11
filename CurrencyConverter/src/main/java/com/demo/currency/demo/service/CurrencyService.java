package com.demo.currency.demo.service;

import com.demo.currency.demo.entity.Currency;
import com.demo.currency.demo.repo.CurrencyRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class CurrencyService {

    @Autowired
    private CurrencyRepository currencyRepository;

    @Autowired
    private RestTemplate restTemplate;

    private static final String API_URL = "https://api.currencybeacon.com/v1/latest?api_key=JmdWBoo96nkSznkkEJLoe4KB1X0ukviv";

    @Transactional
    public void initializeCurrencies() {
        try {
            ResponseEntity<String> response = restTemplate.getForEntity(API_URL, String.class);
            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(response.getBody());

            JsonNode rates = rootNode.path("response").path("rates");
            rates.fields().forEachRemaining(entry -> {
                String currencyCode = entry.getKey();
                double rate = entry.getValue().asDouble();

                Currency currency = new Currency();
                currency.setCode(currencyCode);


                currencyRepository.save(currency);
            });
        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize currencies", e);
        }
    }


    public List<String> getAllCurrencyCodes() {
        return currencyRepository.findAllCurrencyCodes();
    }
}
