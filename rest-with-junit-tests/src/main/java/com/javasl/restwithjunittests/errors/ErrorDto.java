package com.javasl.restwithjunittests.errors;

public record ErrorDto(int httpStatus, String id, String message, String suggestion) {

}
