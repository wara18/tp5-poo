package com.balbe.inventario.service;

import com.balbe.inventario.dto.CategoriaRequest;
import com.balbe.inventario.dto.CategoriaResponse;

import java.util.List;

public interface CategoriaService {
    List<CategoriaResponse> findAll();
    CategoriaResponse findById(Long id);
    CategoriaResponse crear(CategoriaRequest request);
    CategoriaResponse actualizar(Long id, CategoriaRequest request);
    void eliminar(Long id);
}