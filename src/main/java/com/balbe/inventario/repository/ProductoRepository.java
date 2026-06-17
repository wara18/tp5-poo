package com.balbe.inventario.repository;

import com.balbe.inventario.model.Producto;

import java.util.List;

public interface ProductoRepository extends IGenericRepository<Producto, Long> {
    List<Producto> findByCategoria(Long categoriaId);
    List<Producto> buscarPorNombre(String texto);
}