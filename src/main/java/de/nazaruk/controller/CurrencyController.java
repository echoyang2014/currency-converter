package de.nazaruk.controller;

import de.nazaruk.persistence.CurrencyExchangeHistoryEntity;
import de.nazaruk.services.CurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by nazaruk on 11/6/16.
 */
@Controller
public class CurrencyController {

    @Autowired
    private CurrencyService currencyService;

    @ModelAttribute("currencies")
    public List<String> currencies() {
        List<String> currencies = new ArrayList<String>();
        currencies.add("EUR");
        currencies.add("AUD");
        currencies.add("GBP");
        currencies.add("JPY");
        currencies.add("USD");
        currencies.add("UAH");
        return currencies;
    }

    @RequestMapping("/")
    public String currencyConverter() {
        return "redirect:/currency-converter";
    }

    @RequestMapping(value = {"/currency-converter"}, method = RequestMethod.GET)
    public String currencyConverter(Model model) {
        List<CurrencyExchangeHistoryEntity> currencyExchanges = currencyService.getLastCurrencyExchanges();
        model.addAttribute("lastCurrencyExchanges", currencyExchanges);
        ExchangeRateRequest exchangeRateRequest = new ExchangeRateRequest();
        exchangeRateRequest.setFrom("EUR");
        exchangeRateRequest.setTo("USD");
        model.addAttribute("exchangeRateRequest", exchangeRateRequest);

        return "currency-converter";
    }

    @RequestMapping(value = "/currency-converter", method = RequestMethod.POST)
    public String currencyConverter(@ModelAttribute("exchangeRateRequest") ExchangeRateRequest request,
            BindingResult bindingResult, Model model) {
        BigDecimal exchangeRate = currencyService.requestExchangeRate(request.getFrom(), request.getTo(), request.getHistoryDate());
        if (exchangeRate == null) {
            return "currency-converter";
        }
        return "redirect:/currency-converter";
    }

}
