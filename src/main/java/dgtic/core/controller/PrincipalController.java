package dgtic.core.controller;

import dgtic.core.model.producto.Categoria;
import dgtic.core.model.producto.Producto;
import dgtic.core.service.producto.CategoriaService;
import dgtic.core.service.producto.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "tokyo")
public class PrincipalController {

    @Autowired
    private CategoriaService categoriaService;

    @Autowired
    private ProductoService productoService;

    @GetMapping("/")
    public String principal(@RequestParam(defaultValue = "0") int page,
                            @RequestParam(required = false) Integer idCategoria,
                            Model model) {
        PageRequest pageable = PageRequest.of(page, 8);
        Page<Producto> productos;

        if (idCategoria != null) {
            Categoria categoria = categoriaService.findById(idCategoria);
            productos = categoria != null
                    ? productoService.obtenerProductosPorCategoria(categoria, pageable)
                    : productoService.obtenerProductosConStock(pageable);
        } else {
            productos = productoService.obtenerProductosConStock(pageable);
        }

        model.addAttribute("productos", productos.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", productos.getTotalPages());
        model.addAttribute("categorias", categoriaService.findAll());
        model.addAttribute("idCategoria", idCategoria);

        return "principal";
    }
}
