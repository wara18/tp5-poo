package com.balbe.inventario.service;

import com.balbe.inventario.dto.CategoriaRequest;
import com.balbe.inventario.dto.CategoriaResponse;
import com.balbe.inventario.exception.BusinessRuleException;
import com.balbe.inventario.exception.ResourceNotFoundException;
import com.balbe.inventario.model.Categoria;
import com.balbe.inventario.repository.CategoriaRepository;
import com.balbe.inventario.repository.ProductoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaServiceImpl implements CategoriaService {

    private final CategoriaRepository categoriaRepository;
    private final ProductoRepository productoRepository;

    public CategoriaServiceImpl(CategoriaRepository categoriaRepository, ProductoRepository productoRepository) {
        this.categoriaRepository = categoriaRepository;
        this.productoRepository = productoRepository;
    }

    @Override
    public List<CategoriaResponse> findAll() {
        return categoriaRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public CategoriaResponse findById(Long id) {
        Categoria categoria = buscarOLanzar(id);
        return toResponse(categoria);
    }

    @Override
    public CategoriaResponse crear(CategoriaRequest request) {
        Categoria categoria = new Categoria(null, request.nombre());
        categoriaRepository.save(categoria);
        return toResponse(categoria);
    }

    @Override
    public CategoriaResponse actualizar(Long id, CategoriaRequest request) {
        Categoria categoria = buscarOLanzar(id);
        categoria.setNombre(request.nombre());
        categoriaRepository.save(categoria);
        return toResponse(categoria);
    }

    @Override
    public void eliminar(Long id) {
        buscarOLanzar(id);
        boolean tieneProductos = !productoRepository.findByCategoria(id).isEmpty();
        if (tieneProductos) {
            throw new BusinessRuleException("No se puede eliminar la categoría: tiene productos asociados");
        }
        categoriaRepository.deleteById(id);
    }

    private Categoria buscarOLanzar(Long id) {
        return categoriaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoría no encontrada: " + id));
    }

    private CategoriaResponse toResponse(Categoria c) {
        return new CategoriaResponse(c.getId(), c.getNombre());
    }
}