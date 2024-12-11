package com.demo.currency.demo.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;

@Service
public class CurrencyConversionAPIService {

    private static final String BASE_URL = "https://api.currencybeacon.com/v1/convert";
    private static final String API_KEY = "JmdWBoo96nkSznkkEJLoe4KB1X0ukviv";

    private final RestTemplate restTemplate;

    public CurrencyConversionAPIService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Double fetchExchangeRate(String fromCurrency, String toCurrency, Double amount) {
        String url = UriComponentsBuilder.fromHttpUrl(BASE_URL)
                .queryParam("api_key", API_KEY)
                .queryParam("from", fromCurrency)
                .queryParam("to", toCurrency)
                .queryParam("amount", amount)
                .toUriString();

        Map<String, Object> response = restTemplate.getForObject(url, Map.class);

        if (response != null && response.containsKey("response")) {
            Map<String, Object> responseData = (Map<String, Object>) response.get("response");
            return (Double) responseData.get("value")/amount;
        } else {
            throw new RuntimeException("Failed to fetch exchange rate from API");
        }
    }
}
