package dgtic.core.service.producto;

import dgtic.core.model.producto.Categoria;
import dgtic.core.model.producto.Producto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ProductoService {
    void save(Producto producto);
    List<Producto> findAll();
    boolean existsByNombreProducto(String nombreProducto);
    boolean existsBySku(String sku);

    // Retorna una página de productos filtrados por el id de la categoría. Si idCategoria es 0 o nulo, devuelve todos.
    Page<Producto> findProductosByCategoria(Integer idCategoria, Pageable pageable);

    Page<Producto> obtenerProductosConStock(PageRequest pageable);

    Page<Producto> obtenerProductosPorCategoria(Categoria categoria, PageRequest pageable);
}