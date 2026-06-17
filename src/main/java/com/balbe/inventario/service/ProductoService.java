package com.balbe.inventario.service;

import com.balbe.inventario.dto.ProductoRequest;
import com.balbe.inventario.dto.ProductoResponse;

import java.util.List;

public interface ProductoService {
    List<ProductoResponse> findAll(Long categoria, Double precioMin, Double precioMax, Boolean enStock);
    ProductoResponse findById(Long id);
    ProductoResponse crear(ProductoRequest request);
    ProductoResponse actualizar(Long id, ProductoRequest request);
    void eliminar(Long id);
    List<ProductoResponse> buscarPorNombre(String texto);
    List<ProductoResponse> ordenados(String campo, String orden);
}