package com.gmail.mikhnevvich.crypto.repository;

import com.gmail.mikhnevvich.crypto.model.CryptoCurrency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CryptoCurrencyRepository extends JpaRepository<CryptoCurrency, Long> {

    CryptoCurrency findBySymbol(String symbol);

}
