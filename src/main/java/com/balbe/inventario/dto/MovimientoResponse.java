package com.balbe.inventario.dto;

import com.balbe.inventario.model.TipoMovimiento;
import java.time.LocalDateTime;

public record MovimientoResponse(
    Long id,
    Long productoId,
    TipoMovimiento tipo,
    int cantidad,
    int stockResultante,
    String motivo,
    LocalDateTime fecha
) {}