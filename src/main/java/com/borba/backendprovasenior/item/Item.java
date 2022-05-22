package com.borba.backendprovasenior.item;

import com.borba.backendprovasenior.pedido.Pedido;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@Table(name = "tb_item")
public class Item {
    public enum Tipo {
        PRODUTO,
        SERVICO
    }

    @Id
    @GeneratedValue
    @Schema(description = "This is item's identifier", hidden = true)
    private UUID id;

    @NotBlank(message = "Descrição é obrigatório")
    @Schema(description = "This is item's description", example = "Roda")
    private String descricao;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Schema(description = "This is item's type", example = "PRODUTO")
    private Tipo tipo;

    @NotNull
    @PositiveOrZero
    @Schema(description = "This is item's value", example = "50.75")
    private Double valor;

    @Schema(description = "This is the item active flag", example = "true", defaultValue = "true")
    private Boolean active;

    @JsonIgnore
    @ManyToMany(mappedBy = "pedidoItems")
    private Set<Pedido> pedidos = new HashSet<>();
}
