package com.tot.system.repository;

import com.tot.system.model.Security;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.test.context.jdbc.Sql;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
class SecurityRepositoryTest {
    @Autowired
    private SecurityRepository securityRepository;

    @AfterAll
    @Sql("/sql/clean_tables.sql")
    static void cleanUp() {

    }

    @Test
    void shouldThrowInvalidDataAccessApiUsageException() {
        assertThrows(InvalidDataAccessApiUsageException.class, () -> securityRepository.save(null));
    }

    @Test
    void shouldSaveSecurity() {
        Security security = new Security();
        security.setId(1l);
        security.setSecId("1");
        Security actual = securityRepository.save(security);
        assertEquals(1l, actual.getId());
        assertEquals("1", actual.getSecId());
    }

    @Test
    @Sql(scripts = {"/sql/clean_tables.sql", "/sql/security/insert_security.sql"})
    void shouldFindSecurityBySecId() {
        Security expected = new Security();
        expected.setId(5l);
        expected.setSecId("d");
        expected.setRegNumber("d");
        expected.setName("d");
        expected.setEmitentTitle("d");

        assertEquals(Optional.of(expected), securityRepository.findBySecId("d"));
    }

    @Test
    @Sql(scripts = {"/sql/clean_tables.sql", "/sql/security/insert_securities.sql"})
    void shouldFindAllSecurities() {
        Security security1 = new Security();
        security1.setId(3l);
        security1.setSecId("a");
        security1.setRegNumber("a");
        security1.setName("a");
        security1.setEmitentTitle("a");

        Security security2 = new Security();
        security2.setId(4l);
        security2.setSecId("b");
        security2.setRegNumber("b");
        security2.setName("b");
        security2.setEmitentTitle("b");

        List<Security> expected = new ArrayList<>();
        expected.add(security1);
        expected.add(security2);

        assertEquals(expected, securityRepository.findAll());
    }

    @Test
    @Sql("/sql/clean_tables.sql")
    void shouldReturnEmptyList() {
        List<Security> expected = new ArrayList<>();
        assertEquals(expected, securityRepository.findAll());
    }

    @Test
    @Sql(scripts = {"/sql/clean_tables.sql", "/sql/security/insert_security.sql"})
    void shouldDeleteSecurityBySecId() {
        securityRepository.deleteBySecId("d");
        assertEquals(Optional.empty(), securityRepository.findBySecId("d"));
    }

    @Test
    @Sql("/sql/clean_tables.sql")
    void shouldReturnOptionalEmpty() {
        assertEquals(Optional.empty(), securityRepository.findBySecId("25643"));
    }
}