package org.example.carwashfx.controllers.exceptions;

public class ExceptionWash extends RuntimeException {
    public ExceptionWash(String msg) {
        super(msg);
    }

    public ExceptionWash(String msg, Throwable cause) {
        super(msg, cause);
    }
}
