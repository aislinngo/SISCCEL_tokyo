package dgtic.core.model.pedido;

import dgtic.core.model.producto.Producto;
import dgtic.core.model.usuario.Usuario;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Carrito")
public class Carrito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idCarrito;

    @NotNull(message = "El usuario es requerido")
    @ManyToOne
    @JoinColumn(name = "idUsuario")
    @ToString.Exclude
    private Usuario usuario;

    @NotNull(message = "El producto es requerido")
    @ManyToOne
    @JoinColumn(name = "idProducto")
    private Producto producto;

    @NotNull(message = "La cantidad de productos es requerida")
    @Min(value = 1, message = "Debe haber al menos un producto en el carrito")
    private Integer cantidadProductos;

    @NotNull(message = "El subtotal es requerido")
    @PositiveOrZero(message = "El subtotal debe ser mayor o igual a 0")
    private Double subtotal;
}
