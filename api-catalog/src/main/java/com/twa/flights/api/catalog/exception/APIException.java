package com.twa.flights.api.catalog.exception;

import java.util.List;

import org.springframework.http.HttpStatus;

public class APIException extends RuntimeException {

    private static final long serialVersionUID = -5274357034970222787L;
    private HttpStatus status;
    private String description;
    private List<String> reasons;

    public APIException(HttpStatus status, String description, List<String> reasons) {
        this.status = status;
        this.description = description;
        this.reasons = reasons;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getReasons() {
        return reasons;
    }

    public void setReasons(List<String> reasons) {
        this.reasons = reasons;
    }
}
