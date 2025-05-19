package dgtic.core.service.pedido;

import dgtic.core.model.pedido.Carrito;
import dgtic.core.model.usuario.Usuario;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CarritoService {

    void agregarAlCarrito(Usuario usuario, Integer idProducto, Integer cantidad);
    List<Carrito> obtenerCarritoPorUsuario(Usuario usuario);
    void eliminarDelCarrito(Usuario usuario, Integer idProducto);

}
