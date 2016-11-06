package de.nazaruk.controller;

import de.nazaruk.entity.CurrencyExchangeHistoryEntity;
import de.nazaruk.entity.UserEntity;
import de.nazaruk.services.CurrencyService;
import de.nazaruk.services.SecurityService;
import de.nazaruk.services.UserService;
import de.nazaruk.services.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by nazaruk on 11/6/16.
 */
@Controller
public class CurrencyController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserValidator userValidator;

    @Autowired
    private CurrencyService currencyService;

    @Autowired
    private SecurityService securityService;

    @RequestMapping(value = {"/", "/currency-converter"}, method = RequestMethod.GET)
    public String currencyConverter(Model model) {
        List<CurrencyExchangeHistoryEntity> currencyExchanges = currencyService.getLastCurrencyExchanges();
        model.addAttribute("lastCurrencyExchanges", currencyExchanges);
        model.addAttribute("currencyExchange", new CurrencyExchangeHistoryEntity());
        model.addAttribute("userForm", new UserEntity());

        return "currency-converter";
    }

    @RequestMapping(value = "/currency-converter", method = RequestMethod.POST)
    public String currencyConverter(@ModelAttribute("currencyExchange") CurrencyExchangeHistoryEntity currencyExchange,
            BindingResult bindingResult, Model model) {
        //TODO get actual currency rate

        currencyExchange.setRate(new BigDecimal(1.23));
        currencyExchange.setExchangeDate(new Date());
        currencyExchange.setUsername(securityService.findLoggedInUsername());

        currencyService.saveCurrencyExchange(currencyExchange);

        return "redirect:/currency-converter";
    }

}
