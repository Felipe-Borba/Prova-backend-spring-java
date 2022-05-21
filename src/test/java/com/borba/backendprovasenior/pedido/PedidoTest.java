package com.borba.backendprovasenior.pedido;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static com.borba.backendprovasenior.util.PedidoCreator.createPedidoWithProdutoServico;

class PedidoTest {

    @Test
    public void whenCreatePedido_shouldSumCorrectValorTotal_ifPedidoHaveProdutoServicoAndDescontoZero() {
        var pedido = createPedidoWithProdutoServico();

        pedido.setValorDesconto(0.0);

        Assertions.assertThat(pedido.getValorTotalProduto()).isEqualTo(10);
        Assertions.assertThat(pedido.getValorTotalServico()).isEqualTo(5.4);
        Assertions.assertThat(pedido.getValorTotalPedido()).isEqualTo(15.4);
    }

    @Test
    public void whenCreatePedido_shouldSumCorrectValorTotal_ifPedidoHaveProdutoServicoAndDescontoFiftyPercent() {
        var pedido = createPedidoWithProdutoServico();

        pedido.setValorDesconto(0.5);

        Assertions.assertThat(pedido.getValorTotalProduto()).isEqualTo(5);
        Assertions.assertThat(pedido.getValorTotalServico()).isEqualTo(5.4);
        Assertions.assertThat(pedido.getValorTotalPedido()).isEqualTo(10.4);
    }

    @Test
    public void whenCreatePedido_shouldSumCorrectValorTotal_ifPedidoHaveProdutoServicoAndDescontoSeventyFivePercent() {
        var pedido = createPedidoWithProdutoServico();

        pedido.setValorDesconto(0.75);

        Assertions.assertThat(pedido.getValorTotalProduto()).isEqualTo(2.5);
        Assertions.assertThat(pedido.getValorTotalServico()).isEqualTo(5.4);
        Assertions.assertThat(pedido.getValorTotalPedido()).isEqualTo(7.9);
    }
}