package com.twa.flights.api.catalog.controller;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import com.twa.flights.api.catalog.dto.CityDTO;
import com.twa.flights.api.catalog.service.CityService;

@ExtendWith(MockitoExtension.class)
public class CatalogControllerTest {

    private static final String DEFAULT_CITY_CODE = "BUE";

    private CityService entityService;
    private CityController controller;

    @BeforeEach
    public void setUp() {
        entityService = mock(CityService.class);
        controller = new CityController(entityService);
    }

    @Test
    public void should_return_a_city() {
        CityDTO city = getCity();

        when(entityService.getByCode(DEFAULT_CITY_CODE)).thenReturn(city);
        ResponseEntity<CityDTO> response = controller.get(DEFAULT_CITY_CODE);

        assertAll(() -> assertNotNull(response), () -> assertEquals(200, response.getStatusCodeValue()),
                () -> assertEquals(DEFAULT_CITY_CODE, response.getBody().getCode()));
    }

    @Test
    public void should_update_a_city() {
        CityDTO city = getCity();

        when(entityService.update(city)).thenReturn(city);
        ResponseEntity<CityDTO> response = controller.update(DEFAULT_CITY_CODE, city);

        assertAll(() -> assertNotNull(response), () -> assertEquals(200, response.getStatusCodeValue()),
                () -> assertEquals(DEFAULT_CITY_CODE, response.getBody().getCode()));
    }

    private CityDTO getCity() {
        CityDTO city = new CityDTO();
        city.setCode(DEFAULT_CITY_CODE);
        city.setName("Buenos Aires");
        city.setTimeZone("Argentina/Buenos_Aires");

        return city;
    }
}
