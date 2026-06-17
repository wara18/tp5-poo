package com.balbe.inventario.controller;

import com.balbe.inventario.dto.AlertaStockResponse;
import com.balbe.inventario.service.AlertaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/alertas")
public class AlertaController {

    private final AlertaService alertaService;

    public AlertaController(AlertaService alertaService) {
        this.alertaService = alertaService;
    }

    @GetMapping("/stock-bajo")
    public ResponseEntity<List<AlertaStockResponse>> stockBajo() {
        return ResponseEntity.ok(alertaService.stockBajo());
    }
}