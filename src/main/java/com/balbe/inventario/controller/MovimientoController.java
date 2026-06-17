package com.balbe.inventario.controller;

import com.balbe.inventario.dto.MovimientoRequest;
import com.balbe.inventario.dto.MovimientoResponse;
import com.balbe.inventario.service.MovimientoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/movimientos")
public class MovimientoController {

    private final MovimientoService movimientoService;

    public MovimientoController(MovimientoService movimientoService) {
        this.movimientoService = movimientoService;
    }

    @PostMapping
    public ResponseEntity<MovimientoResponse> registrar(@Valid @RequestBody MovimientoRequest request) {
        return ResponseEntity.status(201).body(movimientoService.registrar(request));
    }

    @GetMapping("/producto/{id}")
    public ResponseEntity<List<MovimientoResponse>> historial(@PathVariable Long id) {
        return ResponseEntity.ok(movimientoService.findByProducto(id));
    }
}