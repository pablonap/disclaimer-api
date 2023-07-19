package com.fivvy.disclaimerapi.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public final class ControllerUtils {
    private ControllerUtils() {}

    static String objectToJson(Object object) {
        try {
            return new ObjectMapper().writeValueAsString(object);
        } catch(JsonProcessingException e) {
            return e.getMessage();
        }
    }
}
