package dgtic.core.service.producto;

import dgtic.core.model.producto.Categoria;

import java.util.List;

public interface CategoriaService {

    void save(Categoria categoria);

    List<Categoria> findAll();

    boolean existsByNombreCategoria(String nombreCategoria);

    Categoria findById(Integer idCategoria);
}

