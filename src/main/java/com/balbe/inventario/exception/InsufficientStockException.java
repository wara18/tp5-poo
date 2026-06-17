package com.balbe.inventario.exception;

public class InsufficientStockException extends RuntimeException {
    public InsufficientStockException(String mensaje) {
        super(mensaje);
    }
}