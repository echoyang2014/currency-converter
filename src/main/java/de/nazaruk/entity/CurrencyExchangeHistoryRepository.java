package de.nazaruk.entity;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by nazaruk on 11/6/16.
 */
public interface CurrencyExchangeHistoryRepository extends JpaRepository<CurrencyExchangeHistoryEntity, Long> {

    List<CurrencyExchangeHistoryEntity> findTop10ByUsername(String username);
}
