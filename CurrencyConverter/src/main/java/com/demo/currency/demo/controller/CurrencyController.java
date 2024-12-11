package com.demo.currency.demo.controller;


import com.demo.currency.demo.entity.Currency;
import com.demo.currency.demo.service.CurrencyConversionService;
import com.demo.currency.demo.service.CurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@CrossOrigin("*")
@RestController
public class CurrencyController {

    @Autowired
    private CurrencyConversionService service;

    @Autowired
    private CurrencyService currencyService;

    @GetMapping("/api/currency/convert")
    public Currency convert(@RequestParam String sourceCurrency,
                            @RequestParam String targetCurrency,
                            @RequestParam Double amount) {
        return service.convert(sourceCurrency, targetCurrency, amount);
    }

    @GetMapping("/api/currency/codes")
    public ResponseEntity<List<String>> getAllCurrencyCodes() {
        List<String> currencyCodes = currencyService.getAllCurrencyCodes();
        return new ResponseEntity<>(currencyCodes, HttpStatus.OK);
    }

}
