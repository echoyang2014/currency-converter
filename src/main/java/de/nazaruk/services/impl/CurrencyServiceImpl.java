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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by nazaruk on 11/6/16.
 */
@Service
public class CurrencyServiceImpl implements CurrencyService {

    public static final String GET_CURRENCY_URI = "http://apilayer.net/api/live?access_key=b7670ad620a918606c689e7063b99316&currencies=%s,%s&format=1";

    @Autowired
    private SecurityService securityService;

    @Autowired
    private CurrencyExchangeHistoryRepository currencyExchangeHistoryRepository;

    @Override
    public List<CurrencyExchangeHistoryEntity> getLastCurrencyExchanges() {
        String loggedInUsername = securityService.findLoggedInUsername();
        if (!StringUtils.isEmpty(loggedInUsername)) {
            return currencyExchangeHistoryRepository.findTop10ByUsername(loggedInUsername);
        }

        return Collections.emptyList();
    }

    @Override
    public void requestExchangeRate(String from, String to) {
        BigDecimal exchangeRate = getExternalExchangeRate(from, to);

        CurrencyExchangeHistoryEntity history = new CurrencyExchangeHistoryEntity();
        history.setFromCode(from);
        history.setToCode(to);
        history.setRate(exchangeRate);
        history.setExchangeDate(new Date());
        history.setUsername(securityService.findLoggedInUsername());

        currencyExchangeHistoryRepository.save(history);
    }

    private BigDecimal getExternalExchangeRate(String from, String to) {
        try {
            HttpClient httpClient = HttpClientBuilder.create().build();
            String uri = String.format(GET_CURRENCY_URI, from, to);
            HttpGet request = new HttpGet(uri);

            HttpResponse response = httpClient.execute(request);

            if (response.getStatusLine().getStatusCode() != 200) {
                throw new RuntimeException("Can not get currency: " + uri);
            }

            JSONObject json = new JSONObject(IOUtils.toString(response.getEntity().getContent()));
            return json.getJSONObject("quotes").getBigDecimal(from + to);
        } catch (IOException e) {
            throw new RuntimeException("IOException has been occurred");
        }
    }

}
