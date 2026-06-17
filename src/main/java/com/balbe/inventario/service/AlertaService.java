package com.balbe.inventario.service;

import com.balbe.inventario.dto.AlertaStockResponse;

import java.util.List;

public interface AlertaService {
    List<AlertaStockResponse> stockBajo();
}