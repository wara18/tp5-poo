package com.balbe.inventario.dto;

import com.balbe.inventario.model.TipoMovimiento;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record MovimientoRequest(
    @NotNull(message = "El producto es obligatorio")
    Long productoId,

    @NotNull(message = "El tipo de movimiento es obligatorio")
    TipoMovimiento tipo,

    @Positive(message = "La cantidad debe ser mayor a 0")
    int cantidad,

    String motivo
) {}