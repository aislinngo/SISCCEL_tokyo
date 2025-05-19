package dgtic.core.repository;

import dgtic.core.model.inventario.Inventario;
import dgtic.core.model.producto.Producto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InventarioRepository extends JpaRepository<Inventario, Integer> {

    // Filtra inventarios según el id de la categoría asociada al producto
    Page<Inventario> findByProducto_Categoria_IdCategoria(Integer idCategoria, Pageable pageable);

    // Buscar el inventario asociado a un producto mediante su id
    Inventario findByProducto_IdProducto(Integer idProducto);

    Optional<Inventario> findByProducto(Producto producto);
}
