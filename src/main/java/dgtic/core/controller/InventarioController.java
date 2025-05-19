package dgtic.core.controller;

import dgtic.core.model.producto.Categoria;
import dgtic.core.model.producto.Producto;
import dgtic.core.service.inventario.InventarioService;
import dgtic.core.service.producto.CategoriaService;
import dgtic.core.service.producto.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/tokyo/administracion/inventario")
public class InventarioController {

    @Autowired
    private InventarioService inventarioService;

    @Autowired
    private CategoriaService categoriaService;

    @Autowired
    private ProductoService productoService;

    @GetMapping("ver")
    public String verInventario(Model model,
                                @RequestParam(name = "page", defaultValue = "0") int page,
                                @RequestParam(name = "idCategoria", defaultValue = "0") Integer idCategoria) {
        // Se obtienen los productos paginados filtrados por categoría (0 = todas)
        Page<Producto> productosPage = productoService.findProductosByCategoria(idCategoria, PageRequest.of(page, 4));

        // Crear un mapa con el id del producto y la cantidad actual de inventario
        Map<Integer, Integer> inventoryMap = new HashMap<>();
        for (Producto p : productosPage.getContent()) {
            Integer existencias = inventarioService.obtenerExistenciasPorProducto(p.getIdProducto());
            inventoryMap.put(p.getIdProducto(), existencias != null ? existencias : 0);
        }
        Iterable<Categoria> categorias = categoriaService.findAll();
        model.addAttribute("productosPage", productosPage);
        model.addAttribute("categorias", categorias);
        model.addAttribute("idCategoriaSeleccionada", idCategoria);
        model.addAttribute("inventoryMap", inventoryMap);
        return "principal/admin/inventario";
    }

    @PostMapping("actualizar")
    public String actualizarInventario(@RequestParam("idProducto") Integer idProducto,
                                       @RequestParam("nuevaCantidad") Integer nuevaCantidad,
                                       @RequestParam(name = "page", defaultValue = "0") int page,
                                       @RequestParam(name = "idCategoria", defaultValue = "0") Integer idCategoria,
                                       RedirectAttributes redirectAttributes) {
        inventarioService.actualizarInventario(idProducto, nuevaCantidad);
        redirectAttributes.addFlashAttribute("modalMensaje", "Inventario actualizado correctamente.");

        // Redirigir manteniendo el filtro y la paginación actual
        return "redirect:/tokyo/administracion/inventario/ver?page=" + page + "&idCategoria=" + idCategoria;
    }

    @GetMapping("/generar-pdf")
    public String generarInventarioPdf(@RequestParam(name = "idCategoria", defaultValue = "0") Integer idCategoria,
                                       @RequestParam(required = false) String format,
                                       Model model) {
        // Para el PDF se cargan todos los productos filtrados, sin paginación
        List<Producto> productos = productoService.findProductosByCategoria(idCategoria, PageRequest.of(0, Integer.MAX_VALUE))
                .getContent();
        Map<Integer, Integer> inventoryMap = new HashMap<>();
        productos.forEach(p -> {
            Integer existencias = inventarioService.obtenerExistenciasPorProducto(p.getIdProducto());
            inventoryMap.put(p.getIdProducto(), existencias != null ? existencias : 0);
        });
        model.addAttribute("productos", productos);
        model.addAttribute("inventoryMap", inventoryMap);
        // Retornar la vista PDF si se solicita el formato "pdf"
        if ("pdf".equalsIgnoreCase(format)) {
            return "inventario-pdf";
        }
        // De lo contrario, redirige a la vista normal
        return "inventario";
    }
}
