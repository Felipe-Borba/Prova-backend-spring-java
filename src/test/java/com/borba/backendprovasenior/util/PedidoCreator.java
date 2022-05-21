package com.borba.backendprovasenior.util;

import com.borba.backendprovasenior.item.Item;
import com.borba.backendprovasenior.pedido.Pedido;

import java.util.HashSet;

import static com.borba.backendprovasenior.util.ItemCreator.createProduto;
import static com.borba.backendprovasenior.util.ItemCreator.createServico;

public class PedidoCreator {

    public static Pedido createPedido() {
        var pedido = new Pedido();
        pedido.setStatus(Pedido.Status.ABERTO);
        pedido.setValorDesconto(0.3);
        pedido.setDescricao("pedido");
        return pedido;
    }

    public static Pedido createPedidoWithProdutoServico() {
        var pedido = createPedido();
        var servico = createServico();
        var produto = createProduto();
        var itemPedido = new HashSet<Item>();
        itemPedido.add(servico);
        itemPedido.add(produto);
        pedido.setPedidoItems(itemPedido);
        return pedido;
    }
}
