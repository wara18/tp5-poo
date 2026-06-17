package com.balbe.inventario.repository;

import com.balbe.inventario.exception.ResourceNotFoundException;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public abstract class GenericInMemoryRepository<T, ID> implements IGenericRepository<T, ID> {

    protected final ConcurrentHashMap<ID, T> dataStore = new ConcurrentHashMap<>();
    protected final AtomicLong idGenerator = new AtomicLong(0);

    @Override
    public List<T> findAll() {
        return List.copyOf(dataStore.values());
    }

    @Override
    public Optional<T> findById(ID id) {
        return Optional.ofNullable(dataStore.get(id));
    }

    @Override
    public T save(T entity) {
        ID id = getId(entity);
        if (id == null) {
            id = generarNuevoId();
            setId(entity, id);
        }
        dataStore.put(id, entity);
        return entity;
    }

    @Override
    public void deleteById(ID id) {
        if (!dataStore.containsKey(id)) {
            throw new ResourceNotFoundException("No existe entidad con id: " + id);
        }
        dataStore.remove(id);
    }

    @Override
    public boolean existsById(ID id) {
        return dataStore.containsKey(id);
    }

    @Override
    public long count() {
        return dataStore.size();
    }

    @SuppressWarnings("unchecked")
    private ID generarNuevoId() {
        return (ID) Long.valueOf(idGenerator.incrementAndGet());
    }

    protected abstract ID getId(T entity);

    protected abstract void setId(T entity, ID id);
}