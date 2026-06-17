package com.balbe.inventario.dto;

public record ProductoResponse(
    Long id,
    String nombre,
    String descripcion,
    double precio,
    int stock,
    CategoriaResponse categoria
) {}