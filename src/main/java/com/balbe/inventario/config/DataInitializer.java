package com.balbe.inventario.config;

import com.balbe.inventario.model.Categoria;
import com.balbe.inventario.model.Producto;
import com.balbe.inventario.repository.CategoriaRepository;
import com.balbe.inventario.repository.ProductoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner cargarDatos(CategoriaRepository categoriaRepository, ProductoRepository productoRepository) {
        return args -> {
            Categoria electronicos = new Categoria(null, "Electrónicos");
            Categoria alimentos = new Categoria(null, "Alimentos");
            categoriaRepository.save(electronicos);
            categoriaRepository.save(alimentos);

            productoRepository.save(new Producto(null, "Notebook Dell XPS 15", "Laptop de alto rendimiento", 1599.99, 25, electronicos));
            productoRepository.save(new Producto(null, "Mouse Logitech", "Mouse inalámbrico", 29.99, 5, electronicos));
            productoRepository.save(new Producto(null, "Arroz 1kg", "Arroz blanco grano largo", 2.50, 100, alimentos));
            productoRepository.save(new Producto(null, "Aceite de oliva", "Botella 500ml", 8.75, 2, alimentos));
        };
    }
}