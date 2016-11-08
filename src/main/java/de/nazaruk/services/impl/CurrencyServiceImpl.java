package de.nazaruk.services.impl;

import de.nazaruk.persistence.CurrencyExchangeHistoryEntity;
import de.nazaruk.persistence.CurrencyExchangeHistoryRepository;
import de.nazaruk.services.CurrencyService;
import de.nazaruk.services.SecurityService;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by nazaruk on 11/6/16.
 */
@Service
public class CurrencyServiceImpl implements CurrencyService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CurrencyServiceImpl.class);

    public static final String GET_LIVE_CURRENCY_URI = "http://apilayer.net/api/live?access_key=b7670ad620a918606c689e7063b99316&currencies=%s,%s&format=1";
    public static final String GET_HISTORICAL_CURRENCY_URI = "http://apilayer.net/api/historical?access_key=b7670ad620a918606c689e7063b99316&currencies=%s,%s&format=1&date=%s";
    public static final String DEFAULT_SOURCE = "USD";

    @Autowired
    private SecurityService securityService;

    @Autowired
    private CurrencyExchangeHistoryRepository currencyExchangeHistoryRepository;

    @Override
    public List<CurrencyExchangeHistoryEntity> getLastCurrencyExchanges() {
        String loggedInUsername = securityService.findLoggedInUsername();
        if (!StringUtils.isEmpty(loggedInUsername)) {
            return currencyExchangeHistoryRepository.findTop10ByUsernameOrderByIdDesc(loggedInUsername);
        }

        return Collections.emptyList();
    }

    @Override
    public BigDecimal requestExchangeRate(String from, String to, Date date) {
        BigDecimal exchangeRate = getExternalExchangeRate(from, to, date);
        if (exchangeRate == null) {
            return null;
        }

        CurrencyExchangeHistoryEntity history = new CurrencyExchangeHistoryEntity();
        history.setFromCode(from);
        history.setToCode(to);
        history.setRate(exchangeRate);
        history.setExchangeDate(date == null ? new Date() : date);
        history.setUsername(securityService.findLoggedInUsername());

        currencyExchangeHistoryRepository.save(history);

        return exchangeRate;
    }

    private BigDecimal getExternalExchangeRate(String from, String to, Date date) {
        try {
            HttpClient httpClient = HttpClientBuilder.create().build();
            String uri;
            if (date == null) {
                uri = String.format(GET_LIVE_CURRENCY_URI, from, to);
            } else {
                uri = String.format(GET_HISTORICAL_CURRENCY_URI, from, to, new SimpleDateFormat("YYYY-MM-dd").format(date));
            }
            HttpGet request = new HttpGet(uri);

            HttpResponse response = httpClient.execute(request);

            if (response.getStatusLine().getStatusCode() != 200) {
                LOGGER.warn("Can not get currency: " + uri);
                return null;
            }

            JSONObject json = new JSONObject(IOUtils.toString(response.getEntity().getContent()));
            BigDecimal usdFrom = json.getJSONObject("quotes").getBigDecimal(DEFAULT_SOURCE + from);
            BigDecimal usdTo = json.getJSONObject("quotes").getBigDecimal(DEFAULT_SOURCE + to);

            return usdTo.divide(usdFrom, MathContext.DECIMAL32);
        } catch (IOException e) {
            LOGGER.error("IOException has been occurred", e);
            return null;
        }
    }

}
