package de.nazaruk.services;

import de.nazaruk.persistence.CurrencyExchangeHistoryEntity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by nazaruk on 11/6/16.
 */
public interface CurrencyService {

    List<CurrencyExchangeHistoryEntity> getLastCurrencyExchanges();

    BigDecimal requestExchangeRate(String from, String to, Date date);
}
