package com.balbe.inventario.service;

import com.balbe.inventario.dto.MovimientoRequest;
import com.balbe.inventario.dto.MovimientoResponse;

import java.util.List;

public interface MovimientoService {
    MovimientoResponse registrar(MovimientoRequest request);
    List<MovimientoResponse> findByProducto(Long productoId);
}