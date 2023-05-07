package com.gmail.mikhnevvich.crypto.service;

import com.gmail.mikhnevvich.crypto.model.CryptoCurrency;
import com.gmail.mikhnevvich.crypto.model.PersonWithCrypto;
import com.gmail.mikhnevvich.crypto.model.Tickers;
import com.gmail.mikhnevvich.crypto.repository.CryptoCurrencyRepository;
import com.gmail.mikhnevvich.crypto.repository.PersonWithCryptoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.List;

@Service
@Slf4j
public class CryptoService {

    private static final String COIN_LORE_URL = "https://api.coinlore.net/api/tickers/";

    private final CryptoCurrencyRepository cryptoCurrencyRepository;
    private final PersonWithCryptoRepository personWithCryptoRepository;
    private final RestTemplate restTemplate = new RestTemplate();

    @Autowired
    public CryptoService(CryptoCurrencyRepository cryptoCurrencyRepository, PersonWithCryptoRepository personWithCryptoRepository) {
        this.cryptoCurrencyRepository = cryptoCurrencyRepository;
        this.personWithCryptoRepository = personWithCryptoRepository;
    }

    @PostConstruct
    @Scheduled(cron = "0 * * * * *")
    @Transactional
    public void updateAndNotify() {
        ResponseEntity<Tickers> response = restTemplate.getForEntity(COIN_LORE_URL, Tickers.class);
        cryptoCurrencyRepository.saveAll(response.getBody().getData());

        for (PersonWithCrypto personWithCrypto : personWithCryptoRepository.findByIsDoneFalse()) {
            double fixedPrice = personWithCrypto.getFixedPrice();
            double currentPrice = findBySymbol(personWithCrypto.getSymbol()).getPrice();
            double difference = Math.abs((fixedPrice - currentPrice) / fixedPrice) * 100;
            if (difference >= 1) {
                log.warn("User {}, symbol {}, price changed on {} percents",
                        personWithCrypto.getUsername(), personWithCrypto.getSymbol(), difference);
                personWithCrypto.setDone(true);
                personWithCryptoRepository.save(personWithCrypto);
            }
        }
    }

    @Transactional
    public List<CryptoCurrency> findAll() {
        return cryptoCurrencyRepository.findAll();
    }

    @Transactional
    public CryptoCurrency findBySymbol(String symbol) {
        return cryptoCurrencyRepository.findBySymbol(symbol.toUpperCase());
    }

    @Transactional
    public void notify(String username, String symbol) {
        personWithCryptoRepository.save(new PersonWithCrypto(username, symbol, findBySymbol(symbol).getPrice()));
    }


}
