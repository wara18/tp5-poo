package com.balbe.inventario.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CategoriaRequest(
    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 2, max = 100)
    String nombre
) {}