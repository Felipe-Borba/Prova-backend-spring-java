package com.borba.backendprovasenior.pedido;

import com.borba.backendprovasenior.item.Item;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.HashSet;

@ExtendWith(SpringExtension.class)
class PedidoTest {
    @InjectMocks
    private Pedido pedido;

    private Item createProduto() {
        var produto = new Item();
        produto.setActive(true);
        produto.setDescricao("produto");
        produto.setTipo(Item.Tipo.PRODUTO);
        produto.setValor(10.0);
        return produto;
    }

    private Item createServico() {
        var servico = new Item();
        servico.setActive(true);
        servico.setDescricao("serviço");
        servico.setTipo(Item.Tipo.SERVICO);
        servico.setValor(5.4);
        return servico;
    }

    private Pedido createPedido() {
        var pedido = new Pedido();
        pedido.setStatus(Pedido.Status.ABERTO);
        pedido.setValorDesconto(0.3);
        pedido.setDescricao("pedido");
        return pedido;
    }

    @Test
    public void whenCreatePedido_shouldSumCorrectValorTotal_ifPedidoHaveProdutoServicoAndDescontoZero() {
        var pedido = createPedido();
        var servico = createServico();
        var produto = createProduto();
        var itemPedido = new HashSet<Item>();
        itemPedido.add(servico);
        itemPedido.add(produto);
        pedido.setPedidoItems(itemPedido);
        pedido.setValorDesconto(0.0);

        Assertions.assertThat(pedido.getValorTotalProduto()).isEqualTo(10);
        Assertions.assertThat(pedido.getValorTotalServico()).isEqualTo(5.4);
        Assertions.assertThat(pedido.getValorTotalPedido()).isEqualTo(15.4);
    }

    @Test
    public void whenCreatePedido_shouldSumCorrectValorTotal_ifPedidoHaveProdutoServicoAndDescontoFiftyPercent() {
        var pedido = createPedido();
        var servico = createServico();
        var produto = createProduto();
        var itemPedido = new HashSet<Item>();
        itemPedido.add(servico);
        itemPedido.add(produto);
        pedido.setPedidoItems(itemPedido);
        pedido.setValorDesconto(0.5);

        Assertions.assertThat(pedido.getValorTotalProduto()).isEqualTo(5);
        Assertions.assertThat(pedido.getValorTotalServico()).isEqualTo(5.4);
        Assertions.assertThat(pedido.getValorTotalPedido()).isEqualTo(10.4);
    }

    @Test
    public void whenCreatePedido_shouldSumCorrectValorTotal_ifPedidoHaveProdutoServicoAndDescontoSeventyFivePercent() {
        var pedido = createPedido();
        var servico = createServico();
        var produto = createProduto();
        var itemPedido = new HashSet<Item>();
        itemPedido.add(servico);
        itemPedido.add(produto);
        pedido.setPedidoItems(itemPedido);
        pedido.setValorDesconto(0.75);

        Assertions.assertThat(pedido.getValorTotalProduto()).isEqualTo(2.5);
        Assertions.assertThat(pedido.getValorTotalServico()).isEqualTo(5.4);
        Assertions.assertThat(pedido.getValorTotalPedido()).isEqualTo(7.9);
    }
}