package com.iodigital.tedtalks.exceptions;

import java.util.UUID;

public class TedTalksNotFoundException extends RuntimeException {

    public TedTalksNotFoundException(String resource, UUID id) {
        super(String.format("Could not find %s with id %s", resource, id));
    }
}
