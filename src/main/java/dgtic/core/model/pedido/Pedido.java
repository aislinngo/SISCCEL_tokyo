package dgtic.core.model.pedido;

import dgtic.core.model.usuario.Usuario;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Pedidos")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idPedido;

    @NotNull(message = "El usuario es requerido")
    @ManyToOne
    @JoinColumn(name = "idUsuario")
    private Usuario usuario;

    @NotNull(message = "El estatus del pedido es requerido")
    @ManyToOne
    @JoinColumn(name = "idEstatusPedido")
    private EstatusPedido estatusPedido;

    @NotNull(message = "La fecha del pedido es requerida")
    private LocalDateTime fechaPedido;

    @NotNull(message = "El total del pedido es requerido")
    @DecimalMin(value = "0.00", inclusive = true, message = "El total del pedido debe ser mayor o igual a 0")
    private BigDecimal totalPedido;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DetallePedido> detallePedidos;

}
