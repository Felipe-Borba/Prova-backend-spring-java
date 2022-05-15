package com.borba.backendprovasenior.pedido;

import com.borba.backendprovasenior.item.Item;
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
@Table(name = "tb_pedido")
public class Pedido {

    @Id
    @GeneratedValue
    private UUID id;
    private String descricao;
    private Double valorDesconto;
    private Double valorTotal;

    @ManyToMany
    @JoinTable(
            name = "tb_pedido_item",
            joinColumns = @JoinColumn(name = "pedido_id"),
            inverseJoinColumns = @JoinColumn(name = "item_id")
    )
    private Set<Item> PedidoItems = new HashSet<>();

    public Double getValorTotal() {
        var valorTotal = 0.0;
        for (Item item : PedidoItems) {
            if (item.getTipo() == Item.Tipo.PRODUTO){
                valorTotal += (item.getValor() * this.valorDesconto);
            } else {
                valorTotal += item.getValor();
            }
        }
        return valorTotal;
    }

    public void setValorDesconto(Double valorDesconto) {
        if (valorDesconto > 0 && valorDesconto < 1) {
            throw new RuntimeException("O valor do desconto precisa ser um percentual entre 0 e 1");
        }
        this.valorDesconto = valorDesconto;
    }

    public void addItem(Item item) {
        this.PedidoItems.add(item);
    }
}
