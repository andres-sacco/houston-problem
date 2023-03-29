package com.twa.flights.api.catalog.service;

import com.google.common.base.Stopwatch;
import com.twa.flights.api.catalog.enums.APIError;
import com.twa.flights.api.catalog.exception.DuplicateResourceException;
import com.twa.flights.api.catalog.exception.ResourceNotException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.twa.flights.api.catalog.dto.CityDTO;
import com.twa.flights.api.catalog.model.City;
import com.twa.flights.api.catalog.repository.CityRepository;

import ma.glasnost.orika.MapperFacade;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
public class CityService {

    private static Logger LOGGER = LoggerFactory.getLogger(CityService.class);

    CityRepository cityRepository;
    MapperFacade mapper;

    @Autowired
    public CityService(CityRepository cityRepository, MapperFacade mapper) {
        this.cityRepository = cityRepository;
        this.mapper = mapper;
    }

    public CityDTO getByCode(String code) {
        Stopwatch timer = Stopwatch.createStarted();

        Optional<City> city = cityRepository.findByCode(code);
        if (city.isEmpty()) {
            LOGGER.debug("Not exist city with code: {}", code);
            throw new ResourceNotException(APIError.CITY_NOT_FOUND);
        }
        LOGGER.debug("Duration of the getByCode-operation {} ms", timer.elapsed(TimeUnit.MILLISECONDS));
        return mapper.map(city.get(), CityDTO.class);
    }

    @Transactional(readOnly = false)
    public CityDTO update(CityDTO city) {
        Stopwatch timer = Stopwatch.createStarted();

        City entity = cityRepository.save(mapper.map(city, City.class));

        LOGGER.debug("Duration of the update-operation {} ms", timer.elapsed(TimeUnit.MILLISECONDS));
        return mapper.map(entity, CityDTO.class);
    }

    public CityDTO insert(CityDTO city) {
        Stopwatch timer = Stopwatch.createStarted();

        City entity = commonInsert(city);

        LOGGER.debug("Duration of the insert-operation {} ms", timer.elapsed(TimeUnit.MILLISECONDS));
        return mapper.map(entity, CityDTO.class);
    }

    /*
     * public List<CityDTO> insert(List<CityDTO> cities) { Stopwatch timer = Stopwatch.createStarted();
     * 
     * List<City> entities = new ArrayList<>(); for (CityDTO city : cities) { City entity = commonInsert(city);
     * 
     * entities.add(entity); }
     * 
     * LOGGER.debug("Duration of the insert-operation {} ms", timer.elapsed(TimeUnit.MILLISECONDS)); return
     * mapper.mapAsList(entities, CityDTO.class); }
     */

    public List<CityDTO> insert(List<CityDTO> cities) {
        Stopwatch timer = Stopwatch.createStarted();

        for (CityDTO city : cities) {

            Optional<City> existCity = cityRepository.findByCode(city.getCode());

            if (existCity.isPresent()) {
                throw new DuplicateResourceException(APIError.CITY_WITH_SAME_CODE);
            }
        }

        Iterable<City> persistedEntities = cityRepository.saveAll(mapper.mapAsList(cities, City.class));

        LOGGER.debug("Duration of the insert-operation {} ms", timer.elapsed(TimeUnit.MILLISECONDS));
        return mapper.mapAsList(persistedEntities, CityDTO.class);
    }

    private City commonInsert(CityDTO city) {
        Optional<City> existCity = cityRepository.findByCode(city.getCode());

        if (existCity.isPresent()) {
            throw new DuplicateResourceException(APIError.CITY_WITH_SAME_CODE);
        }

        return cityRepository.save(mapper.map(city, City.class));
    }

    @Transactional(readOnly = false)
    public void delete(String code) {
        Stopwatch timer = Stopwatch.createStarted();
        cityRepository.deleteByCode(code);
        LOGGER.debug("Duration of the delete-operation {} ms", timer.elapsed(TimeUnit.MILLISECONDS));
    }

    @Transactional(readOnly = false)
    public void delete(List<String> codes) {
        Stopwatch timer = Stopwatch.createStarted();
        for (String code : codes) {
            cityRepository.deleteByCode(code);
        }
        LOGGER.debug("Duration of the delete-operation {} ms", timer.elapsed(TimeUnit.MILLISECONDS));
    }
}
