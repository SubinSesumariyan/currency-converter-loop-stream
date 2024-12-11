package com.demo.currency.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CurrencyUIController {
    @GetMapping("/currency-converter")
    public String showCurrencyConverterPage() {
        return "currency-converter";
    }
}
