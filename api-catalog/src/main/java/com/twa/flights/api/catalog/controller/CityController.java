package com.twa.flights.api.catalog.controller;

import com.google.common.base.Stopwatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.twa.flights.api.catalog.controller.documentation.CityResource;
import com.twa.flights.api.catalog.dto.CityDTO;
import com.twa.flights.api.catalog.service.CityService;

import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
public class CityController implements CityResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(CityController.class);

    private CityService entityService;

    @Autowired
    public CityController(CityService entityService) {
        this.entityService = entityService;
    }

    @Override
    public ResponseEntity<CityDTO> get(String code) {
        Stopwatch timer = Stopwatch.createStarted();
        LOGGER.info("Obtain all the information about the city with code {}", code);

        CityDTO response = entityService.getByCode(code);

        LOGGER.debug("Duration of the get-operation {} ms", timer.elapsed(TimeUnit.MILLISECONDS));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<CityDTO> update(String code, @RequestBody CityDTO city) {
        Stopwatch timer = Stopwatch.createStarted();

        LOGGER.info("Modify the information about the city with code {}", code);

        city.setCode(code);
        CityDTO response = entityService.update(city);

        LOGGER.debug("Duration of the update-operation {} ms", timer.elapsed(TimeUnit.MILLISECONDS));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<CityDTO> insert(@RequestBody CityDTO city) {
        Stopwatch timer = Stopwatch.createStarted();

        LOGGER.info("Insert the information about the city with code {}", city.getCode());

        CityDTO response = entityService.insert(city);

        LOGGER.debug("Duration of the insert-operation {} ms", timer.elapsed(TimeUnit.MILLISECONDS));
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<List<CityDTO>> insert(@RequestBody List<CityDTO> cities) {
        Stopwatch timer = Stopwatch.createStarted();

        LOGGER.info("Insert the information about a set of cities");

        List<CityDTO> response = entityService.insert(cities);

        LOGGER.debug("Duration of the insert-operation {} ms", timer.elapsed(TimeUnit.MILLISECONDS));
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Void> delete(@PathVariable String code) {
        Stopwatch timer = Stopwatch.createStarted();
        LOGGER.info("Delete the information about the city with code {}", code);

        entityService.delete(code);

        LOGGER.debug("Duration of the delete-operation {} ms", timer.elapsed(TimeUnit.MILLISECONDS));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> delete(@RequestBody List<String> codes) {
        Stopwatch timer = Stopwatch.createStarted();
        LOGGER.info("Delete the information about a list cities");

        entityService.delete(codes);

        LOGGER.debug("Duration of the delete-operation {} ms", timer.elapsed(TimeUnit.MILLISECONDS));
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
