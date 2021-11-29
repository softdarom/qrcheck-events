package ru.softdarom.qrcheck.events.exception;

public class InvalidBookOperation extends RuntimeException {

    public InvalidBookOperation(String message) {
        super(message);
    }
}
