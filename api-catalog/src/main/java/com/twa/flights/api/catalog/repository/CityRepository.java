package com.twa.flights.api.catalog.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.twa.flights.api.catalog.model.City;

import java.util.Optional;

@Repository
public interface CityRepository extends PagingAndSortingRepository<City, Long> {

    Optional<City> findByCode(String code);

    @Modifying
    @Query("delete from City u where u.code=:code")
    void deleteByCode(@Param("code") String code);

}
