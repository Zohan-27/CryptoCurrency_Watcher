package com.gmail.mikhnevvich.crypto.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class CryptoCurrency {

    @Id
    private Long id;
    @Column(unique = true)
    private String symbol;
    @JsonProperty("price_usd")
    private double price;

    public CryptoCurrency(Long id, String symbol, double price) {
        this.id = id;
        this.symbol = symbol;
        this.price = price;
    }

    public CryptoCurrency() {

    }

    public Long getId() {
        return id;
    }

    public String getSymbol() {
        return symbol;
    }

    public double getPrice() {
        return price;
    }
}
