package com.demo.currency.demo.repo;

import com.demo.currency.demo.entity.Currency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CurrencyRepository extends JpaRepository<Currency, Long> {

    @Query("SELECT c.code FROM Currency c")
    List<String> findAllCurrencyCodes();
}
