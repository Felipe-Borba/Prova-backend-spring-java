package com.borba.backendprovasenior.util;

import com.borba.backendprovasenior.item.Item;

public class ItemCreator {

    public static Item createProduto() {
        var produto = new Item();
        produto.setActive(true);
        produto.setDescricao("produto");
        produto.setTipo(Item.Tipo.PRODUTO);
        produto.setValor(10.0);
        return produto;
    }

    public static Item createServico() {
        var servico = new Item();
        servico.setActive(true);
        servico.setDescricao("serviço");
        servico.setTipo(Item.Tipo.SERVICO);
        servico.setValor(5.4);
        return servico;
    }
}
