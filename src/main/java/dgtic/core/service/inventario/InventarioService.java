package dgtic.core.service.inventario;

import dgtic.core.model.inventario.Inventario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

public interface InventarioService {

    // Si categoria es 0 o null se devuelven todos
    Page<Inventario> findInventarios(Integer categoria, Pageable pageable);

    // Metodo para obtener la cantidad de existencias de un producto dado su id.
    Integer obtenerExistenciasPorProducto(Integer idProducto);

    // Metodo para actualizar o crear un registro de inventario para un producto
    void actualizarInventario(Integer idProducto, Integer nuevaCantidad);
}
