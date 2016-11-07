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

import java.util.List;

/**
 * Created by nazaruk on 11/6/16.
 */
@Controller
public class CurrencyController {

    @Autowired
    private CurrencyService currencyService;

    @RequestMapping("/")
    public String currencyConverter() {
        return "redirect:/currency-converter";
    }

    @RequestMapping(value = {"/currency-converter"}, method = RequestMethod.GET)
    public String currencyConverter(Model model) {
        List<CurrencyExchangeHistoryEntity> currencyExchanges = currencyService.getLastCurrencyExchanges();
        model.addAttribute("lastCurrencyExchanges", currencyExchanges);
        model.addAttribute("exchangeRateRequest", new ExchangeRateRequest());

        return "currency-converter";
    }

    @RequestMapping(value = "/currency-converter", method = RequestMethod.POST)
    public String currencyConverter(@ModelAttribute("exchangeRateRequest") ExchangeRateRequest exchangeRateRequest,
            BindingResult bindingResult, Model model) {
        currencyService.requestExchangeRate(exchangeRateRequest.getFrom(), exchangeRateRequest.getTo());

        return "redirect:/currency-converter";
    }

}
