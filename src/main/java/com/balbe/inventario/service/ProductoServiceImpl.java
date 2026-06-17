package com.balbe.inventario.service;

import com.balbe.inventario.dto.ProductoRequest;
import com.balbe.inventario.dto.ProductoResponse;
import com.balbe.inventario.dto.CategoriaResponse;
import com.balbe.inventario.exception.BusinessRuleException;
import com.balbe.inventario.exception.ResourceNotFoundException;
import com.balbe.inventario.model.Categoria;
import com.balbe.inventario.model.Producto;
import com.balbe.inventario.repository.CategoriaRepository;
import com.balbe.inventario.repository.ProductoRepository;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class ProductoServiceImpl implements ProductoService {

    private final ProductoRepository productoRepository;
    private final CategoriaRepository categoriaRepository;

    public ProductoServiceImpl(ProductoRepository productoRepository, CategoriaRepository categoriaRepository) {
        this.productoRepository = productoRepository;
        this.categoriaRepository = categoriaRepository;
    }

    @Override
    public List<ProductoResponse> findAll(Long categoria, Double precioMin, Double precioMax, Boolean enStock) {
        return productoRepository.findAll().stream()
                .filter(p -> categoria == null || (p.getCategoria() != null && p.getCategoria().getId().equals(categoria)))
                .filter(p -> precioMin == null || p.getPrecio() >= precioMin)
                .filter(p -> precioMax == null || p.getPrecio() <= precioMax)
                .filter(p -> enStock == null || (enStock ? p.getStock() > 0 : p.getStock() == 0))
                .map(this::toResponse)
                .toList();
    }

    @Override
    public ProductoResponse findById(Long id) {
        return toResponse(buscarOLanzar(id));
    }

    @Override
    public ProductoResponse crear(ProductoRequest request) {
        Categoria categoria = categoriaRepository.findById(request.categoriaId())
                .orElseThrow(() -> new ResourceNotFoundException("Categoría no encontrada: " + request.categoriaId()));

        if (request.stockInicial() < 0) {
            throw new BusinessRuleException("El stock inicial debe ser >= 0");
        }

        Producto producto = new Producto(null, request.nombre(), request.descripcion(),
                request.precio(), request.stockInicial(), categoria);
        productoRepository.save(producto);
        return toResponse(producto);
    }

    @Override
    public ProductoResponse actualizar(Long id, ProductoRequest request) {
        Producto producto = buscarOLanzar(id);

        if (request.nombre() != null) producto.setNombre(request.nombre());
        if (request.descripcion() != null) producto.setDescripcion(request.descripcion());
        producto.setPrecio(request.precio());

        if (request.categoriaId() != null) {
            Categoria categoria = categoriaRepository.findById(request.categoriaId())
                    .orElseThrow(() -> new ResourceNotFoundException("Categoría no encontrada: " + request.categoriaId()));
            producto.setCategoria(categoria);
        }

        productoRepository.save(producto);
        return toResponse(producto);
    }

    @Override
    public void eliminar(Long id) {
        buscarOLanzar(id);
        productoRepository.deleteById(id);
    }

    @Override
    public List<ProductoResponse> buscarPorNombre(String texto) {
        if (texto == null || texto.isBlank()) {
            throw new BusinessRuleException("El parámetro de búsqueda 'q' no puede estar vacío");
        }
        return productoRepository.buscarPorNombre(texto).stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public List<ProductoResponse> ordenados(String campo, String orden) {
        Comparator<Producto> comparator = switch (campo) {
            case "precio" -> Comparator.comparingDouble(Producto::getPrecio);
            case "stock" -> Comparator.comparingInt(Producto::getStock);
            default -> Comparator.comparing(Producto::getNombre);
        };

        if ("desc".equalsIgnoreCase(orden)) {
            comparator = comparator.reversed();
        }

        return productoRepository.findAll().stream()
                .sorted(comparator)
                .map(this::toResponse)
                .toList();
    }

    private Producto buscarOLanzar(Long id) {
        return productoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado: " + id));
    }

    private ProductoResponse toResponse(Producto p) {
        CategoriaResponse catResponse = p.getCategoria() != null
                ? new CategoriaResponse(p.getCategoria().getId(), p.getCategoria().getNombre())
                : null;
        return new ProductoResponse(p.getId(), p.getNombre(), p.getDescripcion(), p.getPrecio(), p.getStock(), catResponse);
    }
}