package de.nazaruk.services.impl;

import de.nazaruk.persistence.CurrencyExchangeHistoryEntity;
import de.nazaruk.persistence.CurrencyExchangeHistoryRepository;
import de.nazaruk.services.CurrencyService;
import de.nazaruk.services.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.List;

/**
 * Created by nazaruk on 11/6/16.
 */
@Service
public class CurrencyServiceImpl implements CurrencyService {

    @Autowired
    private SecurityService securityService;

    @Autowired
    private CurrencyExchangeHistoryRepository currencyExchangeHistoryRepository;

    @Override
    public List<CurrencyExchangeHistoryEntity> getLastCurrencyExchanges() {
        String loggedInUsername = securityService.findLoggedInUsername();
        if (!StringUtils.isEmpty(loggedInUsername)) {
            return currencyExchangeHistoryRepository.findAll();
//            return currencyExchangeHistoryRepository.findTop10ByUsername(loggedInUsername);
        }

        return Collections.emptyList();
    }

    @Override
    public void saveCurrencyExchange(CurrencyExchangeHistoryEntity currencyExchangeHistoryEntity) {
        currencyExchangeHistoryRepository.save(currencyExchangeHistoryEntity);
    }
}
