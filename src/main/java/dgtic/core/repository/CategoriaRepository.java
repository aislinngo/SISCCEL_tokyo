package dgtic.core.repository;

import dgtic.core.model.producto.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {

    boolean existsByNombreCategoria(String nombreCategoria);
}