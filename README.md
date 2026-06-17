# Smart Inventory — Gestión de Inventario Inteligente

Microservicio REST para manejar el inventario de un depósito. Está hecho con Java 21 y Spring Boot 3.5.x, y permite manejar productos, categorías, movimientos de stock y alertas automáticas cuando el stock está bajo.

---

## INTEGRANTES
- Martin Francisco - EISI1534 - ISI - 46.788.188
- Avila Wara - EISI1510 - ISI - 46.870.396
- Peralta Lautaro - EISI1589 - ISI - 46788963
- Nuñez Castelli Santiago - EISI1587 - ISI - 47242651

---

## Stack tecnológico

- **Java 21**
- **Spring Boot 3.5.x** (Spring Web, Validation, Actuator)
- **Maven**
- **Almacenamiento in-memory** (`ConcurrentHashMap`, sin base de datos)
- **springdoc-openapi** (Swagger UI)

---

## Cómo correr el proyecto

```bash
mvn spring-boot:run
```

Levanta en el puerto **8081**.

- API base: `http://localhost:8081/api`
- Swagger UI: `http://localhost:8081/swagger-ui.html`
- Health check: `http://localhost:8081/actuator/health`

Al arrancar, carga solo automáticamente unos datos de prueba (2 categorías y 4 productos) con un `CommandLineRunner`, así no hay que crear todo a mano para probar los endpoints.

---

## Arquitectura

El proyecto sigue una arquitectura en capas bastante estricta:

```
Controller → Service → Repository
```

- **Controller**: recibe los requests HTTP, valida con `@Valid`, le pasa la pelota al Service y devuelve los DTOs.
- **Service**: tiene toda la lógica de negocio (validaciones, manejo de stock, alertas).
- **Repository**: guarda y devuelve las entidades en memoria.

### Genéricos

El acceso a datos usa una interfaz genérica `IGenericRepository<T, ID>` y una implementación abstracta `GenericInMemoryRepository<T, ID>`, que centraliza las operaciones CRUD comunes (`findAll`, `findById`, `save`, `deleteById`, `existsById`, `count`) usando `ConcurrentHashMap` como almacenamiento thread-safe. Los repositorios concretos (`InMemoryProductoRepository`, `InMemoryCategoriaRepository`, `InMemoryMovimientoRepository`) extienden esta clase y solo agregan las consultas propias de cada entidad.

### Concurrencia

El stock de cada `Producto` se maneja con `AtomicInteger`, para que los incrementos y decrementos sean atómicos aunque lleguen requests concurrentes.

---

## Estructura del proyecto

```
src/main/java/com/balbe/inventario/
├── InventarioApplication.java
├── model/          # Entidades de dominio (Producto, Categoria, MovimientoInventario, enums)
├── dto/            # Records de Request/Response con validaciones
├── repository/     # IGenericRepository, GenericInMemoryRepository y repos concretos
├── service/        # Lógica de negocio
├── controller/     # Endpoints REST
├── exception/      # Excepciones custom y manejo centralizado de errores
└── config/         # StockConfig y carga de datos de prueba
```

---

## Endpoints principales

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| `GET` | `/api/productos` | Listar productos (filtros opcionales: `categoria`, `precioMin`, `precioMax`, `enStock`) |
| `GET` | `/api/productos/{id}` | Obtener un producto por ID |
| `POST` | `/api/productos` | Crear un producto |
| `PUT` | `/api/productos/{id}` | Actualizar un producto |
| `DELETE` | `/api/productos/{id}` | Eliminar un producto |
| `GET` | `/api/productos/buscar?q=` | Búsqueda por nombre |
| `GET` | `/api/productos/ordenados?campo=&orden=` | Listar ordenado por nombre, precio o stock |
| `GET` | `/api/categorias` | Listar categorías |
| `POST` | `/api/categorias` | Crear categoría |
| `PUT` | `/api/categorias/{id}` | Actualizar categoría |
| `DELETE` | `/api/categorias/{id}` | Eliminar categoría (409 si tiene productos asociados) |
| `POST` | `/api/movimientos` | Registrar entrada/salida de stock (409 si el stock es insuficiente) |
| `GET` | `/api/movimientos/producto/{id}` | Historial de movimientos de un producto |
| `GET` | `/api/alertas/stock-bajo` | Productos con stock por debajo del umbral configurado |

La doc completa e interactiva está en Swagger UI.

---

## Reglas de negocio

1. No se puede sacar más stock del que hay disponible → `409 Conflict`.
2. El stock se modifica de forma atómica con `AtomicInteger`.
3. Un producto entra en alerta cuando `stock < stockMinimo`; si `stock < stockCritico`, la alerta pasa a ser **CRITICA**. Los umbrales se configuran en `application.yml`.
4. El stock inicial de un producto tiene que ser ≥ 0.
5. No se puede borrar una categoría que tenga productos asociados → `409 Conflict`.

---

## Configuración

Los umbrales de alerta se definen en `src/main/resources/application.yml`:

```yaml
inventario:
  stock:
    minimo: 10
    critico: 3
```

---

## Performance

Ver [`PERFORMANCE.md`](./PERFORMANCE.md) para el análisis de complejidad Big O de cada endpoint y las mediciones de tiempos de ejecución.