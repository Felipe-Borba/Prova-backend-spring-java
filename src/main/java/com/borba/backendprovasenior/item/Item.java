package com.borba.backendprovasenior.item;

import com.borba.backendprovasenior.pedido.Pedido;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "tb_item")
public class Item {
    public enum Tipo {
        PRODUTO,
        SERVICO
    }

    @Id
    @GeneratedValue
    private UUID id;

    @NotBlank(message = "Descrição é obrigatório")
    private String descricao;

    @Enumerated(EnumType.STRING)
    private Tipo tipo;

    @NotBlank(message = "Valor não é obrigatório")
    private Double valor;

    private Boolean active;

    @JsonIgnore
    @ManyToMany(mappedBy = "pedidoItems")
    private Set<Pedido> pedidos = new HashSet<>();
}
