package com.tot.system.repository;

import com.tot.system.model.History;
import com.tot.system.model.Security;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.test.context.jdbc.Sql;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
class HistoryRepositoryTest {
    @Autowired
    private HistoryRepository historyRepository;

    @AfterAll
    @Sql("/sql/clean_tables.sql")
    static void cleanUp() {

    }

    @Test
    void shouldThrowInvalidDataAccessApiUsageException() {
        assertThrows(InvalidDataAccessApiUsageException.class, () -> historyRepository.save(null));
    }

    @Test
    void shouldSaveHistory() {
        History history = new History();
        history.setId(5l);
        History actual = historyRepository.save(history);
        assertEquals(5l, actual.getId());
    }

    @Test
    @Sql(scripts = {"/sql/clean_tables.sql", "/sql/history/insert_list.sql"})
    void shouldFindAllHistories() {
        Security security = new Security();
        security.setId(2l);
        security.setSecId("a");
        security.setRegNumber("a");
        security.setName("a");
        security.setEmitentTitle("a");

        History history = new History();
        history.setId(1l);
        history.setSecurity(security);
        history.setTradeDate(Date.valueOf("2022-10-20"));
        history.setNumTrades(2);
        history.setOpen(2d);
        history.setClose(3d);

        History history2 = new History();
        history2.setId(2l);
        history2.setSecurity(security);
        history2.setTradeDate(Date.valueOf("2022-10-21"));
        history2.setNumTrades(3);
        history2.setOpen(3d);
        history2.setClose(4d);

        List<History> expected = new ArrayList<>();
        expected.add(history);
        expected.add(history2);

        assertEquals(expected, historyRepository.findAll());
    }

    @Test
    @Sql(scripts = {"/sql/clean_tables.sql", "/sql/history/insert_entities.sql"})
    void shouldFindHistoryById() {
        Security security = new Security();
        security.setId(2l);
        security.setSecId("a");
        security.setRegNumber("a");
        security.setName("a");
        security.setEmitentTitle("a");

        History expected = new History();
        expected.setId(3l);
        expected.setSecurity(security);
        expected.setTradeDate(Date.valueOf("2022-10-20"));
        expected.setNumTrades(3);
        expected.setOpen(3d);
        expected.setClose(4d);
        assertEquals(Optional.of(expected), historyRepository.findById(3l));
    }

    @Test
    @Sql("/sql/clean_tables.sql")
    void shouldReturnEmptyList() {
        List<History> expected = new ArrayList<>();
        assertEquals(expected, historyRepository.findAll());
    }

    @Test
    @Sql(scripts = {"/sql/clean_tables.sql", "/sql/history/insert_entities.sql"})
    void shouldDeleteHistoryById() {
        historyRepository.deleteById(3l);
        assertEquals(Optional.empty(), historyRepository.findById(3l));
    }

    @Test
    @Sql("/sql/clean_tables.sql")
    void shouldReturnEmptyOptional() {
        assertEquals(Optional.empty(), historyRepository.findById(1234l));
    }
}