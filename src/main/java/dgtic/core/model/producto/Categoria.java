package dgtic.core.model.producto;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Categorias")

public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idCategoria;

    @NotNull(message = "Categoría es requerido")
    @NotBlank(message = "Categoría no puede estar vacío")
    @Size(min = 1, max = 50, message = "El nombre de la categoría debe tener entre 1-50 caracteres")
    private String nombreCategoria;
}
