package ua.ievleva.movieland.service.impl;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ua.ievleva.movieland.service.CurrencyService;


@Service
public class CurrencyServiceImpl implements CurrencyService {
    @Autowired
    private RestTemplate restTemplate;

    @Value("${currency.service.url}")
    private String currencyService;

    @Override
    public double getCurrencyRate(String currency) {
        String body = restTemplate.getForEntity(currencyService, String.class, currency).getBody();

        return new JSONObject(body.substring(1, body.length() - 1)).getDouble("rate");
    }
}
