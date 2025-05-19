package dgtic.core.service.producto;

import dgtic.core.model.producto.Categoria;
import dgtic.core.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoriaServiceImpl implements CategoriaService {

    @Autowired
    CategoriaRepository categoriaRepository;

    @Override
    @Transactional
    public void save(Categoria categoria) {
        categoriaRepository.save(categoria);
    }

    @Override
    @Transactional
    public List<Categoria> findAll() {
        return categoriaRepository.findAll();
    }

    @Override
    public boolean existsByNombreCategoria(String nombreCategoria) {
        return categoriaRepository.existsByNombreCategoria(nombreCategoria);
    }

    @Override
    public Categoria findById(Integer idCategoria) {
        return categoriaRepository.findById(idCategoria).orElse(null);
    }
}