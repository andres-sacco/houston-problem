package com.twa.flights.api.catalog.repository;

import com.twa.flights.api.catalog.model.City;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.quickperf.junit5.QuickPerfTest;
import org.quickperf.spring.sql.QuickPerfSqlConfig;
import org.quickperf.sql.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@Import(QuickPerfSqlConfig.class)
@QuickPerfTest
@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CityRepositoryTest {

    @Autowired
    CityRepository repository;

    public static MySQLContainer container = new MySQLContainer<>("mysql:5.7").withUsername("mysql")
            .withPassword("mysql").withDatabaseName("catalog").withReuse(true);

    @BeforeAll
    public static void setUp() {
        container.start();
    }

    @DynamicPropertySource
    static void postgresqlProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", container::getJdbcUrl);
        registry.add("spring.datasource.username", container::getUsername);
        registry.add("spring.datasource.password", container::getPassword);
    }

    @Test
    @ExpectSelect(1) // Validate the number of queries that are executed
    @ExpectMaxQueryExecutionTime(thresholdInMilliSeconds = 2) // This check the duration of the execution of the query
    @AnalyzeSql
    void should_get_a_city() {
        Optional<City> city = repository.findByCode("BUE");

        assertAll(() -> assertTrue(city.isPresent()), () -> assertEquals("BUE", city.get().getCode()));
    }

    @Test
    @ExpectInsert(1) // Validate the number of queries that are executed
    @ExpectMaxQueryExecutionTime(thresholdInMilliSeconds = 1) // This check the duration of the execution of the query
    @AnalyzeSql
    void should_insert_a_city() {

        City newCity = new City();
        newCity.setName("Test");
        newCity.setCode("TST");
        newCity.setTimeZone("America/Argentina/Buenos_Aires");

        City city = repository.save(newCity);

        assertAll(() -> assertNotNull(city), () -> assertNotNull(city.getId()),
                () -> assertEquals("TST", city.getCode()));
    }

}