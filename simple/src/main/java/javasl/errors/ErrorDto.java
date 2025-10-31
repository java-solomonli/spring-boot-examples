package javasl.errors;

public record ErrorDto(int httpStatus, String id, String message, String suggestion) {

}
