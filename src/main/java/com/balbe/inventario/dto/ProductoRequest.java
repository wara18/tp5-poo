package com.balbe.inventario.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

public record ProductoRequest(
    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 2, max = 100)
    String nombre,

    @Size(max = 500)
    String descripcion,

    @PositiveOrZero(message = "El precio debe ser >= 0")
    double precio,

    @PositiveOrZero(message = "El stock inicial debe ser >= 0")
    int stockInicial,

    @NotNull(message = "La categoría es obligatoria")
    Long categoriaId
) {}