package com.example.tfiadm.exception;

public class VentaNotFoundException extends RuntimeException{
    public VentaNotFoundException(String message) {
        super(message);
    }
}
