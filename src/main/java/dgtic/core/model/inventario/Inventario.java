package dgtic.core.model.inventario;

import dgtic.core.model.producto.Producto;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Inventarios")
public class Inventario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idInventario;

    @NotNull(message = "El producto es requerido")
    @OneToOne
    @JoinColumn(name = "idProducto", unique = true)
    private Producto producto;

    @NotNull(message = "El número de existencias es requerido")
    @Min(value = 0, message = "El número de existencias no puede ser negativo")
    private Integer numeroExistencias;

    private LocalDateTime ultimoInventario;
}
