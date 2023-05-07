package com.gmail.mikhnevvich.crypto.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;


public class Tickers {

    private List<CryptoCurrency> data;

    public Tickers() {
    }

    public List<CryptoCurrency> getData() {
        return data;
    }
}
