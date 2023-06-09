package com.gmail.mikhnevvich.crypto.controller;

import com.gmail.mikhnevvich.crypto.model.CryptoCurrency;
import com.gmail.mikhnevvich.crypto.service.CryptoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/api")
public class CryptoController {

    private final CryptoService cryptoService;

    @Autowired
    public CryptoController(CryptoService cryptoService) {
        this.cryptoService = cryptoService;
    }

    @GetMapping("/currencies")
    public List<CryptoCurrency> findAll() {
        return cryptoService.findAll();
    }

    @GetMapping("/currencies/{symbol}")
    public CryptoCurrency findBySymbol(@PathVariable String symbol) {
        return cryptoService.findBySymbol(symbol);
    }

    @GetMapping("/{username}/{symbol}")
    public void notify(@PathVariable String username, @PathVariable String symbol) {
        cryptoService.notify(username, symbol);
    }
}
