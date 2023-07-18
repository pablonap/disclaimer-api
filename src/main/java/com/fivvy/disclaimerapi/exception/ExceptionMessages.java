package com.fivvy.disclaimerapi.exception;

public enum ExceptionMessages {
    RESOURCE_NOT_FOUND("Resource not found"),
    NO_DATA_CHANGES_FOUND(
            "No data changes found");

    private final String message;

    private ExceptionMessages(final String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
