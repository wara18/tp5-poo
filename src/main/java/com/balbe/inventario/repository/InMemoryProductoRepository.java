package com.balbe.inventario.repository;

import com.balbe.inventario.model.Producto;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class InMemoryProductoRepository
        extends GenericInMemoryRepository<Producto, Long>
        implements ProductoRepository {

    @Override
    public List<Producto> findByCategoria(Long categoriaId) {
        return dataStore.values().stream()
                .filter(p -> p.getCategoria() != null && p.getCategoria().getId().equals(categoriaId))
                .toList();
    }

    @Override
    public List<Producto> buscarPorNombre(String texto) {
        String lower = texto.toLowerCase();
        return dataStore.values().stream()
                .filter(p -> p.getNombre().toLowerCase().contains(lower))
                .toList();
    }

    @Override
    protected Long getId(Producto entity) {
        return entity.getId();
    }

    @Override
    protected void setId(Producto entity, Long id) {
        entity.setId(id);
    }
}