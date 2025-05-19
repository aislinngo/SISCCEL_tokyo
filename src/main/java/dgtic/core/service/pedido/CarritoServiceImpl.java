package dgtic.core.service.pedido;

import dgtic.core.model.pedido.Carrito;
import dgtic.core.model.producto.Producto;
import dgtic.core.model.usuario.Usuario;
import dgtic.core.repository.CarritoRepository;
import dgtic.core.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CarritoServiceImpl implements CarritoService {

    @Autowired
    private CarritoRepository carritoRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Override
    public void agregarAlCarrito(Usuario usuario, Integer idProducto, Integer cantidad) {
        Producto producto = productoRepository.findById(idProducto)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        Optional<Carrito> carritoOpt = carritoRepository.findByUsuarioAndProducto_IdProducto(usuario, producto.getIdProducto());

        Carrito carrito;
        if (carritoOpt.isPresent()) {
            carrito = carritoOpt.get();
            carrito.setCantidadProductos(carrito.getCantidadProductos() + cantidad);
            carrito.setSubtotal(carrito.getCantidadProductos() * producto.getPrecio().doubleValue());
        } else {
            carrito = new Carrito();
            carrito.setUsuario(usuario);
            carrito.setProducto(producto);
            carrito.setCantidadProductos(cantidad);
            carrito.setSubtotal(cantidad * producto.getPrecio().doubleValue());
        }
        carritoRepository.save(carrito);
    }

    @Override
    public List<Carrito> obtenerCarritoPorUsuario(Usuario usuario) {
        return carritoRepository.findByUsuario(usuario);
    }

    @Override
    @Transactional
    public void eliminarDelCarrito(Usuario usuario, Integer idProducto) {
        carritoRepository.deleteByUsuarioAndProducto_IdProducto(usuario, idProducto);
    }
}
