package dgtic.core.service.inventario;

import dgtic.core.model.inventario.Inventario;
import dgtic.core.model.producto.Producto;
import dgtic.core.repository.InventarioRepository;
import dgtic.core.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class InventarioServiceImpl implements InventarioService {

    @Autowired
    private InventarioRepository inventarioRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Override
    public Page<Inventario> findInventarios(Integer categoria, Pageable pageable) {
        if (categoria != null && categoria != 0) {
            return inventarioRepository.findByProducto_Categoria_IdCategoria(categoria, pageable);
        }
        return inventarioRepository.findAll(pageable);
    }

    @Override
    public Integer obtenerExistenciasPorProducto(Integer idProducto) {
        Inventario inventario = inventarioRepository.findByProducto_IdProducto(idProducto);
        return (inventario != null) ? inventario.getNumeroExistencias() : 0;
    }

    @Override
    public void actualizarInventario(Integer idProducto, Integer nuevaCantidad) {
        Inventario inventario = inventarioRepository.findByProducto_IdProducto(idProducto);
        if (inventario != null) {
            // Actualizar registro existente
            inventario.setNumeroExistencias(nuevaCantidad);
            inventario.setUltimoInventario(LocalDateTime.now());
            inventarioRepository.save(inventario);
        } else {
            // Si no existe, se crea un nuevo registro
            Optional<Producto> optProducto = productoRepository.findById(idProducto);
            if (optProducto.isPresent()) {
                inventario = new Inventario();
                inventario.setProducto(optProducto.get());
                inventario.setNumeroExistencias(nuevaCantidad);
                inventario.setUltimoInventario(LocalDateTime.now());
                inventarioRepository.save(inventario);
            }
        }
    }
}
