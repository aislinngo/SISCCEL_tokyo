package dgtic.core.model.pedido;

import dgtic.core.model.producto.Producto;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "DetallePedido")
public class DetallePedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idDetallePedido;

    @NotNull(message = "El pedido es requerido")
    @ManyToOne
    @JoinColumn(name = "idPedido")
    private Pedido pedido;

    @NotNull(message = "El producto es requerido")
    @ManyToOne
    @JoinColumn(name = "idProducto")
    private Producto producto;

    @NotNull(message = "La cantidad de productos es requerida")
    @Min(value = 0, message = "La cantidad de productos no puede ser negativa")
    private Integer cantidadProductos;

    @NotNull(message = "El subtotal es requerido")
    @PositiveOrZero(message = "El subtotal debe ser mayor o igual a 0")
    private Double subtotal;
}
