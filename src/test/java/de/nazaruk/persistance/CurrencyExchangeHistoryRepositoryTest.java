package de.nazaruk.persistance;

import static org.junit.Assert.*;

import de.nazaruk.persistence.CurrencyExchangeHistoryEntity;
import de.nazaruk.persistence.CurrencyExchangeHistoryRepository;
import de.nazaruk.persistence.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by nazaruk on 11/8/16.
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class CurrencyExchangeHistoryRepositoryTest {

    @Autowired
    private CurrencyExchangeHistoryRepository repository;

    @Test
    public void getLast10EntriesOrderedById() {
        for (int i = 0; i < 20; i++) {
            CurrencyExchangeHistoryEntity entity = new CurrencyExchangeHistoryEntity();
            entity.setFromCode("EUR");
            entity.setToCode("EUR");
            entity.setExchangeDate(new Date());
            entity.setUsername("test");
            entity.setRate(BigDecimal.ONE);

            repository.save(entity);
        }

        List<CurrencyExchangeHistoryEntity> entities = repository.findTop10ByUsernameOrderByIdDesc("test");
        assertEquals(10, entities.size());
        for (int i = 0; i < 9; i++) {
            assertTrue(entities.get(i).getId() > entities.get(i+1).getId());
        }
    }
}
