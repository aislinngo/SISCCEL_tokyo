package dgtic.core.controller;

import dgtic.core.model.producto.Categoria;
import dgtic.core.model.producto.Producto;
import dgtic.core.service.producto.CategoriaService;
import dgtic.core.service.producto.ProductoService;
import dgtic.core.util.Archivos;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;


@Controller
@RequestMapping("/tokyo/administracion/producto")
public class ProductoController {

    @Autowired
    private CategoriaService categoriaService;

    @Autowired
    private ProductoService productoService;


    @Autowired
    MessageSource mensaje;

    @Value("${ejemplo.imagen.ruta}")
    private String archivoRuta;

//   ---------- CATEGORÍA DEL PRODUCTO -----------------------------------------------------

//    Pantalla para guardar categoría
    @GetMapping("ver-guardar-categoria")
    public String verGuardarCategoria(Model model) {
        model.addAttribute("categoria", new Categoria());
        return "principal/guardar-categoria";
    }
    @PostMapping("recibir-categoria")
    public String GuardarCategoria(@Valid Categoria categoria,
                                   BindingResult bindingResult,
                                   Model modelo){
        if(bindingResult.hasErrors()){
            for(ObjectError error: bindingResult.getAllErrors()){
                System.out.println("Error "+ error.getDefaultMessage());
            }
            return "principal/guardar-categoria";
        }

        // Validar duplicados antes de guardar
        if (categoriaService.existsByNombreCategoria(categoria.getNombreCategoria())) {
            String msg = mensaje.getMessage("Error.categoria.duplicada",
                    null, LocaleContextHolder.getLocale());
            bindingResult.rejectValue("nombreCategoria", "nombreCategoria", msg);
            return "principal/guardar-categoria";
        }

        try {
            categoriaService.save(categoria);
            modelo.addAttribute("modalMensaje", "Se ha guardado con éxito la categoría: " + categoria.getNombreCategoria());
        } catch (Exception e) {
            e.printStackTrace();
            modelo.addAttribute("modalMensaje", "Ha ocurrido un error inesperado.");
            return "principal/guardar-categoria";
        }

        modelo.addAttribute("categoria", new Categoria());
        return "principal/guardar-categoria";
    }

    //   ---------- GUARDAR NUEVO PRODUCTO -----------------------------------------------------


    @GetMapping("guardar-producto")
    public String mostrarFormularioProducto(Model model) {
        model.addAttribute("producto", new Producto());
        model.addAttribute("categorias", categoriaService.findAll());
        return "principal/guardar-producto";
    }
    @PostMapping("guardar-producto")
    public String guardarProducto(@Valid Producto producto,
                                  BindingResult bindingResult,
                                  @RequestParam("imagenArchivo") MultipartFile imagenArchivo,
                                  Model modelo,
                                  RedirectAttributes redirectAttributes) {

        // Validaciones básicas
        if (imagenArchivo.isEmpty()) {
            bindingResult.rejectValue("imagen",
                    "imagenArchivo.empty", "El archivo de imagen no puede estar vacío");
        }

        if (producto.getCategoria() == null || producto.getCategoria().getIdCategoria() == -1) {
            bindingResult.rejectValue("categoria",
                    "categoria.idCategoria", "La categoría no puede estar vacía");
        }

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("modalMensaje", "Por favor complete todos los campos requeridos");
            modelo.addAttribute("categorias", categoriaService.findAll());
            return "redirect:/tokyo/administracion/producto/guardar-producto";
        }

        if (productoService.existsByNombreProducto(producto.getNombreProducto())) {
            redirectAttributes.addFlashAttribute("modalMensaje", "Error: Ya existe un producto con este nombre");
            return "redirect:/tokyo/administracion/producto/guardar-producto";
        }

        if (producto.getSku() != null && productoService.existsBySku(producto.getSku())) {
            redirectAttributes.addFlashAttribute("modalMensaje", "Error: El SKU ya está registrado para otro producto");
            return "redirect:/tokyo/administracion/producto/guardar-producto";
        }
        if (!imagenArchivo.isEmpty()) {
            String nombreArchivo = imagenArchivo.getOriginalFilename();
            String imagenRuta = Archivos.almacenar(imagenArchivo, archivoRuta);
            if (imagenRuta != null) {
                producto.setImagen(nombreArchivo);
            } else {
                redirectAttributes.addFlashAttribute(
                        "modalMensaje", "Error al subir la imagen.");
                return "redirect:/tokyo/administracion/producto/guardar-producto";
            }
        }
        if (producto.getFechaCreacion() == null) {
            producto.setFechaCreacion(LocalDateTime.now());
        }

        try {
            productoService.save(producto);
            redirectAttributes.addFlashAttribute(
                    "modalMensaje", "Producto guardado exitosamente.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute(
                    "modalMensaje", "Error inesperado.");
            return "redirect:/tokyo/administracion/producto/guardar-producto";
        }
        return "redirect:/tokyo/administracion/producto/guardar-producto";
    }

}
