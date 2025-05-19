package dgtic.core.repository;

import dgtic.core.model.producto.Categoria;
import dgtic.core.model.producto.Producto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductoRepository extends JpaRepository<Producto, Integer> {

    boolean existsByNombreProducto(String nombreProducto);
    boolean existsBySku(String sku);

    // Metodo para buscar una página de productos cuya categoría coincida con el parámetro ingresado
    Page<Producto> findByCategoria_IdCategoria(Integer idCategoria, Pageable pageable);

    // Buscar productos con existencias mayores a 0
    Page<Producto> findByInventario_NumeroExistenciasGreaterThan(int numeroExistencias, Pageable pageable);

    // Buscar productos con existencias mayores a 0 y filtrados por categoría
    Page<Producto> findByInventario_NumeroExistenciasGreaterThanAndCategoria(int numeroExistencias, Categoria categoria, Pageable pageable);


}