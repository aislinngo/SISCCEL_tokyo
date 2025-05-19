package dgtic.core.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "tokyo")
public class ProductoClienteController {


    @GetMapping("productos")
    public String productos(Model model) {

        return "principal/productos";
    }

}
