package com.balbe.inventario.repository;

import com.balbe.inventario.model.MovimientoInventario;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class InMemoryMovimientoRepository
        extends GenericInMemoryRepository<MovimientoInventario, Long>
        implements MovimientoRepository {

    @Override
    public List<MovimientoInventario> findByProductoId(Long productoId) {
        return dataStore.values().stream()
                .filter(m -> m.getProductoId().equals(productoId))
                .toList();
    }

    @Override
    protected Long getId(MovimientoInventario entity) {
        return entity.getId();
    }

    @Override
    protected void setId(MovimientoInventario entity, Long id) {
        entity.setId(id);
    }
}