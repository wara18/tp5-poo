package com.balbe.inventario.repository;

import com.balbe.inventario.model.Categoria;
import org.springframework.stereotype.Repository;

@Repository
public class InMemoryCategoriaRepository
        extends GenericInMemoryRepository<Categoria, Long>
        implements CategoriaRepository {

    @Override
    protected Long getId(Categoria entity) {
        return entity.getId();
    }

    @Override
    protected void setId(Categoria entity, Long id) {
        entity.setId(id);
    }
}