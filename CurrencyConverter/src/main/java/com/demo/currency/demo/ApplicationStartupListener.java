package com.demo.currency.demo;

import com.demo.currency.demo.service.CurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class ApplicationStartupListener implements ApplicationListener<ApplicationReadyEvent> {

    @Autowired
    private CurrencyService currencyService;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        currencyService.initializeCurrencies();
    }
}
