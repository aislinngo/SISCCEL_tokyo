package dgtic.core.repository;

import dgtic.core.model.pedido.Carrito;
import dgtic.core.model.usuario.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CarritoRepository extends JpaRepository<Carrito, Integer> {

    Optional<Carrito> findByUsuarioAndProducto_IdProducto(Usuario usuario, Integer idProducto);
    List<Carrito> findByUsuario(Usuario usuario);
    void deleteByUsuarioAndProducto_IdProducto(Usuario usuario, Integer idProducto);
    void deleteByUsuario(Usuario usuario);

}
