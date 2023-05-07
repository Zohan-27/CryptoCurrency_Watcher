package com.gmail.mikhnevvich.crypto.repository;

import com.gmail.mikhnevvich.crypto.model.PersonWithCrypto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonWithCryptoRepository extends JpaRepository<PersonWithCrypto, Long> {

    List<PersonWithCrypto> findByIsDoneFalse();

}
