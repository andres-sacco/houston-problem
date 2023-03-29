package com.twa.flights.api.catalog.exception;

import com.twa.flights.api.catalog.enums.APIError;

import java.util.List;

public class ResourceNotException extends APIException {

    public ResourceNotException(APIError error) {
        super(error.getHttpStatus(), error.getMessage(), null);
    }
}