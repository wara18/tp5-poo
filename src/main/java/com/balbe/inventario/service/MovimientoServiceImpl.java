package com.balbe.inventario.service;

import com.balbe.inventario.dto.MovimientoRequest;
import com.balbe.inventario.dto.MovimientoResponse;
import com.balbe.inventario.exception.InsufficientStockException;
import com.balbe.inventario.exception.ResourceNotFoundException;
import com.balbe.inventario.model.MovimientoInventario;
import com.balbe.inventario.model.Producto;
import com.balbe.inventario.model.TipoMovimiento;
import com.balbe.inventario.repository.MovimientoRepository;
import com.balbe.inventario.repository.ProductoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovimientoServiceImpl implements MovimientoService {

    private final MovimientoRepository movimientoRepository;
    private final ProductoRepository productoRepository;

    public MovimientoServiceImpl(MovimientoRepository movimientoRepository, ProductoRepository productoRepository) {
        this.movimientoRepository = movimientoRepository;
        this.productoRepository = productoRepository;
    }

    @Override
    public MovimientoResponse registrar(MovimientoRequest request) {
        Producto producto = productoRepository.findById(request.productoId())
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado: " + request.productoId()));

        int stockResultante;

        if (request.tipo() == TipoMovimiento.SALIDA) {
            if (producto.getStock() < request.cantidad()) {
                throw new InsufficientStockException(
                        "No se pueden retirar " + request.cantidad() +
                        " unidades. Stock disponible: " + producto.getStock());
            }
            stockResultante = producto.decrementarStock(request.cantidad());
        } else {
            stockResultante = producto.incrementarStock(request.cantidad());
        }

        productoRepository.save(producto);

        MovimientoInventario movimiento = new MovimientoInventario(
                null, producto.getId(), request.tipo(), request.cantidad(), stockResultante, request.motivo());
        movimientoRepository.save(movimiento);

        return toResponse(movimiento);
    }

    @Override
    public List<MovimientoResponse> findByProducto(Long productoId) {
        return movimientoRepository.findByProductoId(productoId).stream()
                .map(this::toResponse)
                .toList();
    }

    private MovimientoResponse toResponse(MovimientoInventario m) {
        return new MovimientoResponse(m.getId(), m.getProductoId(), m.getTipo(),
                m.getCantidad(), m.getStockResultante(), m.getMotivo(), m.getFecha());
    }
}