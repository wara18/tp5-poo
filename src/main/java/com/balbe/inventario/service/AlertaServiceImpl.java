package com.balbe.inventario.service;

import com.balbe.inventario.config.StockConfig;
import com.balbe.inventario.dto.AlertaStockResponse;
import com.balbe.inventario.model.NivelAlerta;
import com.balbe.inventario.model.Producto;
import com.balbe.inventario.repository.ProductoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlertaServiceImpl implements AlertaService {

    private final ProductoRepository productoRepository;
    private final StockConfig stockConfig;

    public AlertaServiceImpl(ProductoRepository productoRepository, StockConfig stockConfig) {
        this.productoRepository = productoRepository;
        this.stockConfig = stockConfig;
    }

    @Override
    public List<AlertaStockResponse> stockBajo() {
        return productoRepository.findAll().stream()
                .filter(p -> p.getStock() < stockConfig.minimo())
                .map(this::toAlerta)
                .toList();
    }

    private AlertaStockResponse toAlerta(Producto p) {
        NivelAlerta nivel = p.getStock() < stockConfig.critico() ? NivelAlerta.CRITICO : NivelAlerta.BAJO;
        return new AlertaStockResponse(p.getId(), p.getNombre(), p.getStock(), stockConfig.minimo(), nivel);
    }
}