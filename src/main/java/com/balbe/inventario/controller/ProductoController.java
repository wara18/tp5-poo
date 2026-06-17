package com.balbe.inventario.controller;

import com.balbe.inventario.dto.ProductoRequest;
import com.balbe.inventario.dto.ProductoResponse;
import com.balbe.inventario.service.ProductoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    private final ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @GetMapping
    public ResponseEntity<List<ProductoResponse>> listar(
            @RequestParam(required = false) Long categoria,
            @RequestParam(required = false) Double precioMin,
            @RequestParam(required = false) Double precioMax,
            @RequestParam(required = false) Boolean enStock) {
        return ResponseEntity.ok(productoService.findAll(categoria, precioMin, precioMax, enStock));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductoResponse> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(productoService.findById(id));
    }

    @PostMapping
    public ResponseEntity<ProductoResponse> crear(@Valid @RequestBody ProductoRequest request) {
        ProductoResponse creado = productoService.crear(request);
        return ResponseEntity.created(URI.create("/api/productos/" + creado.id())).body(creado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductoResponse> actualizar(@PathVariable Long id, @Valid @RequestBody ProductoRequest request) {
        return ResponseEntity.ok(productoService.actualizar(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        productoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<ProductoResponse>> buscar(@RequestParam String q) {
        return ResponseEntity.ok(productoService.buscarPorNombre(q));
    }

    @GetMapping("/ordenados")
    public ResponseEntity<List<ProductoResponse>> ordenados(
            @RequestParam String campo,
            @RequestParam(defaultValue = "asc") String orden) {
        return ResponseEntity.ok(productoService.ordenados(campo, orden));
    }
}