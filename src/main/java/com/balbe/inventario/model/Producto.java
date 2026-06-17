package com.balbe.inventario.model;

import java.util.concurrent.atomic.AtomicInteger;

public class Producto {
    private Long id;
    private String nombre;
    private String descripcion;
    private double precio;
    private final AtomicInteger stock;
    private Categoria categoria;

    public Producto() {
        this.stock = new AtomicInteger(0);
    }

    public Producto(Long id, String nombre, String descripcion, double precio, int stockInicial, Categoria categoria) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.stock = new AtomicInteger(stockInicial);
        this.categoria = categoria;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public double getPrecio() { return precio; }
    public void setPrecio(double precio) { this.precio = precio; }

    public int getStock() { return stock.get(); }
    public int incrementarStock(int cantidad) { return stock.addAndGet(cantidad); }
    public int decrementarStock(int cantidad) { return stock.addAndGet(-cantidad); }
    public void setStock(int valor) { stock.set(valor); }

    public Categoria getCategoria() { return categoria; }
    public void setCategoria(Categoria categoria) { this.categoria = categoria; }
}