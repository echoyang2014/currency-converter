package de.nazaruk.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by nazaruk on 11/6/16.
 */
@Repository
public interface CurrencyExchangeHistoryRepository extends JpaRepository<CurrencyExchangeHistoryEntity, Long> {

    List<CurrencyExchangeHistoryEntity> findTop10ByUsernameOrderByIdDesc(String username);
}
