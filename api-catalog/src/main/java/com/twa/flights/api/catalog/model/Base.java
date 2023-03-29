package com.twa.flights.api.catalog.model;

import javax.persistence.*;

@MappedSuperclass
public abstract class Base {

    @Id // Identify which is the primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Indicate the way to generate the ID
    private Long id;

    @Column(length = 4, nullable = false)
    private String code;

    @Column(length = 50, nullable = false)
    private String name;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
