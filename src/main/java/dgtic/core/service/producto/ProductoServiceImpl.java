package dgtic.core.service.producto;

import dgtic.core.model.producto.Categoria;
import dgtic.core.model.producto.Producto;
import dgtic.core.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoServiceImpl implements ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    @Override
    public void save(Producto producto) {
        productoRepository.save(producto);
    }

    @Override
    public List<Producto> findAll() {
        return productoRepository.findAll();
    }

    @Override
    public boolean existsByNombreProducto(String nombreProducto) {
        return productoRepository.existsByNombreProducto(nombreProducto);
    }

    @Override
    public boolean existsBySku(String sku) {
        return productoRepository.existsBySku(sku);
    }


    public Page<Producto> findAllPage(Pageable pageable) {
        return productoRepository.findAll(pageable);
    }

    @Override
    public Page<Producto> findProductosByCategoria(Integer idCategoria, Pageable pageable) {
        if (idCategoria == null || idCategoria == 0) {
            return productoRepository.findAll(pageable);
        }
        return productoRepository.findByCategoria_IdCategoria(idCategoria, pageable);
    }

    @Override
    public Page<Producto> obtenerProductosConStock(PageRequest pageable) {
        return productoRepository.findByInventario_NumeroExistenciasGreaterThan(0, pageable);
    }

    @Override
    public Page<Producto> obtenerProductosPorCategoria(Categoria categoria, PageRequest pageable) {
        return productoRepository.findByInventario_NumeroExistenciasGreaterThanAndCategoria(0, categoria, pageable);
    }

}