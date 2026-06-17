package com.balbe.inventario.repository;

import com.balbe.inventario.model.MovimientoInventario;

import java.util.List;

public interface MovimientoRepository extends IGenericRepository<MovimientoInventario, Long> {
    List<MovimientoInventario> findByProductoId(Long productoId);
}