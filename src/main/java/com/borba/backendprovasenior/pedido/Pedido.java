package com.borba.backendprovasenior.pedido;

import com.borba.backendprovasenior.item.Item;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@Table(name = "tb_pedido")
public class Pedido {
    public enum Status {
        ABERTO, FECHADO
    }

    @Id
    @GeneratedValue
    @Schema(description = "This is pedido's identifier", hidden = true)
    private UUID id;

    @NotBlank(message = "Descrição é obrigatório")
    @Schema(description = "This is the pedido's description", example = "Pedido de carro")
    private String descricao;

    @Min(value = 0, message = "Valor do desconto não pode ser menor que 0")
    @Max(value = 1, message = "Valor do desconto não pode ser maior que 1")
    @Schema(description = "This is the pedido's discount value", example = "0.7", defaultValue = "0")
    private Double valorDesconto = 0.0;

    @Transient
    @Schema(description = "This field is calculated from pedidoItems")
    private Double valorTotalServico = 0.0;

    @Transient
    @Schema(description = "This field is calculated from pedidoItems")
    private Double valorTotalProduto = 0.0;

    @Transient
    @Schema(description = "This field is calculated from pedidoItems")
    private Double valorTotalPedido = 0.0;

    @Enumerated(EnumType.STRING)
    @Schema(description = "This is the pedido's status", defaultValue = "ABERTO" )
    private Pedido.Status status = Status.ABERTO;

    @ManyToMany
    @JoinTable(
            name = "tb_pedido_item",
            joinColumns = @JoinColumn(name = "pedido_id"),
            inverseJoinColumns = @JoinColumn(name = "item_id")
    )
    private Set<Item> pedidoItems = new HashSet<>();


    public Double getValorTotalPedido() {
        return getValorTotalProduto() + getValorTotalServico();
    }

    public Double getValorTotalServico() {
        var valorTotal = 0.0;
        for (Item item : pedidoItems) {
            if (item.getTipo() == Item.Tipo.SERVICO) {
                valorTotal += item.getValor();
            }
        }
        return valorTotal;
    }

    public Double getValorTotalProduto() {
        var valorTotal = 0.0;
        for (Item item : pedidoItems) {
            if (item.getTipo() == Item.Tipo.PRODUTO) {
                valorTotal += item.getValor();
            }
        }
        return valorTotal * (1 - valorDesconto);
    }

    public void addItem(Item item) {
        this.pedidoItems.add(item);
    }
}
