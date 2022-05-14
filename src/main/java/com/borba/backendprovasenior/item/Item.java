package com.borba.backendprovasenior.item;

import com.borba.backendprovasenior.pedido.Pedido;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "tb_item")
public class Item {
    @Id
    @GeneratedValue
    private UUID id;
    private String descricao;
    private String tipo; //todo refactor para um enum
    private Double valor;

    @JsonIgnore
    @ManyToMany(mappedBy = "PedidoItems")
    private Set<Pedido> pedidos = new HashSet<>();
}
