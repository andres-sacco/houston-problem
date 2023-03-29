package com.twa.flights.api.catalog.exception;

import com.twa.flights.api.catalog.enums.APIError;

public class DuplicateResourceException extends APIException {

    public DuplicateResourceException(APIError error) {
        super(error.getHttpStatus(), error.getMessage(), null);
    }
}