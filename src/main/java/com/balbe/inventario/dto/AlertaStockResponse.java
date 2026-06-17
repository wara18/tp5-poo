package com.balbe.inventario.dto;

import com.balbe.inventario.model.NivelAlerta;

public record AlertaStockResponse(
    Long productoId,
    String nombre,
    int stockActual,
    int stockMinimo,
    NivelAlerta nivel
) {}