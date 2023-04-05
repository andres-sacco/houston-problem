package com.twa.flights.api.catalog.service;

import com.twa.flights.api.catalog.dto.CityDTO;
import com.twa.flights.api.catalog.model.City;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.quickperf.junit5.QuickPerfTest;
import org.quickperf.jvm.allocation.AllocationUnit;
import org.quickperf.jvm.annotations.HeapSize;
import org.quickperf.jvm.annotations.MeasureHeapAllocation;
import org.quickperf.spring.sql.QuickPerfSqlConfig;
import org.quickperf.sql.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Import(QuickPerfSqlConfig.class)
@QuickPerfTest
@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CityServiceTest {

    @Autowired
    CityService service;

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
    @ExpectSelect(2) // Validate the number of queries that are executed
    @ExpectMaxQueryExecutionTime(thresholdInMilliSeconds = 2) // This check the duration of the execution of the query
    @AnalyzeSql
    @MeasureHeapAllocation
    @HeapSize(value = 60, unit = AllocationUnit.MEGA_BYTE)
    void should_get_a_city() {
        CityDTO city = service.getByCode("BUE");

        assertAll(() -> assertNotNull(city), () -> assertEquals("BUE", city.getCode()));
    }

    @Test
    @ExpectInsert(1) // Validate the number of queries that are executed
    @ExpectSelect(1) // Validate the number of queries that are executed
    @ExpectMaxQueryExecutionTime(thresholdInMilliSeconds = 1) // This check the duration of the execution of the query
    @AnalyzeSql
    void should_insert_a_city() {

        CityDTO newCity = new CityDTO();
        newCity.setName("Test");
        newCity.setCode("TST");
        newCity.setTimeZone("America/Argentina/Buenos_Aires");

        CityDTO city = service.insert(newCity);

        assertAll(() -> assertNotNull(city), () -> assertEquals("TST", city.getCode()));
    }

    @Test
    @ExpectJdbcBatching(batchSize = 10)
    @ExpectInsert(1) // Validate the number of queries that are executed
    @ExpectSelect(5) // Validate the number of queries that are executed
    @ExpectMaxQueryExecutionTime(thresholdInMilliSeconds = 1) // This check the duration of the execution of the query
    @AnalyzeSql
    void should_insert_a_cities() {
        List<CityDTO> city = service.insert(getCities());

        assertAll(() -> assertNotNull(city), () -> assertEquals("TS0", city.get(0).getCode()));
    }

    private List<CityDTO> getCities() {
        List<CityDTO> cities = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            CityDTO newCity = new CityDTO();
            newCity.setName("Test");
            newCity.setCode("TS" + i);
            newCity.setTimeZone("America/Argentina/Buenos_Aires");

            cities.add(newCity);
        }

        return cities;
    }

}
