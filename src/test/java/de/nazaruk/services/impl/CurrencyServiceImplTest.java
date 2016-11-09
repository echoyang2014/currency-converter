package de.nazaruk.services.impl;

import de.nazaruk.persistence.CurrencyExchangeHistoryEntity;
import de.nazaruk.persistence.CurrencyExchangeHistoryRepository;
import de.nazaruk.services.SecurityService;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.math.MathContext;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

/**
 * Created by nazaruk on 11/9/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class CurrencyServiceImplTest {

    @Mock
    private SecurityService securityService;

    @Mock
    private CurrencyExchangeHistoryRepository currencyExchangeHistoryRepository;

    @InjectMocks
    private CurrencyServiceImpl currencyService;

    @Test
    public void shouldReturnEmptyList_whenCanNotFindUser() {
        when(securityService.findLoggedInUsername()).thenReturn("");

        assertTrue(currencyService.getLastCurrencyExchanges().isEmpty());
        verifyNoMoreInteractions(currencyExchangeHistoryRepository);
    }

    @Test
    public void shouldReturnLastCurrencyExchanges() {
        when(securityService.findLoggedInUsername()).thenReturn("test");
        CurrencyExchangeHistoryEntity historyEntity = mock(CurrencyExchangeHistoryEntity.class);
        List<CurrencyExchangeHistoryEntity> history = new ArrayList<>();
        history.add(historyEntity);

        when(currencyExchangeHistoryRepository.findTop10ByUsernameOrderByIdDesc("test"))
                .thenReturn(history);

        assertTrue(currencyService.getLastCurrencyExchanges().equals(history));
    }

    @Test
    @Ignore
    public void requestEURUSDExchangeRate() {
        BigDecimal rate = currencyService.requestExchangeRate("EUR", "USD", new Date());
        assertNotNull(rate);
    }

    @Test
    public void requestEURUSD_2011_01_01_ExchangeRate() throws ParseException {
        Date exchangeDate = new SimpleDateFormat("yyyy/MM/dd").parse("2011/01/30");
        BigDecimal rate = currencyService.requestExchangeRate("EUR", "USD", exchangeDate);
        assertTrue(new BigDecimal(1.365956, MathContext.DECIMAL32).compareTo(rate.round(MathContext.DECIMAL32)) == 0);
    }

    @Test
    public void requestExchangeRateInFuture() throws ParseException {
        long MILLISECONDS_PER_DAY = 1000L * 60 * 60 * 24;
        Date now = new Date();
        Date future = new Date(now.getTime() + MILLISECONDS_PER_DAY);
        BigDecimal rate = currencyService.requestExchangeRate("EUR", "USD", future);
        BigDecimal rateForToday = currencyService.requestExchangeRate("EUR", "USD", new Date(now.getTime() + 1000l));
        assertNotNull(rate);
        assertTrue(rate.compareTo(rateForToday) == 0);
    }

    @Test
    public void requestExchangeRateForEmptyDate_shouldReturnRateForToday() {
        BigDecimal rateForNull = currencyService.requestExchangeRate("EUR", "USD", null);
        Date now = new Date();
        BigDecimal rateForToday = currencyService.requestExchangeRate("EUR", "USD", new Date(now.getTime() + 1000l));
        assertNotNull(rateForNull);
        assertTrue(rateForNull.compareTo(rateForToday) == 0);
    }
}
