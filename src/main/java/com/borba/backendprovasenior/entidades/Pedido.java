package com.borba.backendprovasenior.entidades;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "tb_pedido")
public class Pedido {

    @Id
    @GeneratedValue
    private UUID id;
    private String descricao;
    private Double valorDesconto;
    private Double valorTotal;

    @OneToMany
    private Set<ItemPedido> items;

    public Double getValorTotal() {
        var valorTotal = 0.0;
        for(ItemPedido item : items) {
            valorTotal += item.getValor();
        }
        return valorTotal;
    }
}
