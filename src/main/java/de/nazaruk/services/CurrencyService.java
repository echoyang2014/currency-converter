package de.nazaruk.services;

import de.nazaruk.entity.CurrencyExchangeHistoryEntity;

import java.util.List;

/**
 * Created by nazaruk on 11/6/16.
 */
public interface CurrencyService {

    List<CurrencyExchangeHistoryEntity> getLastCurrencyExchanges();

    void saveCurrencyExchange(CurrencyExchangeHistoryEntity currencyExchangeHistoryEntity);
}
