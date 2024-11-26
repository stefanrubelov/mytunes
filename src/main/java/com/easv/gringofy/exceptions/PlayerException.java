package com.easv.gringofy.exceptions;

public class PlayerException extends Exception {
    public PlayerException(String message) {
        super(message);
    }
    public PlayerException(String message, Throwable cause) {
        super(message, cause);
    }
}
