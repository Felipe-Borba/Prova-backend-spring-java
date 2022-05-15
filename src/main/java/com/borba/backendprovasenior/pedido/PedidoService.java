package com.borba.backendprovasenior.pedido;

import com.borba.backendprovasenior.exception.RecursoNaoEncontrado;
import com.borba.backendprovasenior.item.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PedidoService {
    @Autowired
    private PedidoRepository pedidoRepository;
    @Autowired
    private ItemService itemService;

    public List<Pedido> findAll() {
        return pedidoRepository.findAll();
    }

    public Pedido findById(UUID id) {
        return pedidoRepository.findById(id).orElseThrow(() -> new RecursoNaoEncontrado("Id não encontado: " + id));
    }

    public Pedido save(Pedido pedido) {
        return pedidoRepository.save(pedido);
    }

    public Pedido update(UUID id, Pedido pedido) {
        var newPedido = this.findById(id);
        newPedido.setDescricao(pedido.getDescricao());
        newPedido.setValorDesconto(pedido.getValorDesconto());
        newPedido.setPedidoItems(pedido.getPedidoItems());
        return pedidoRepository.save(newPedido);
    }

    public void delete(UUID id) {
        var pedido = this.findById(id);
        pedidoRepository.delete(pedido);
    }

    public void addItem(UUID pedidoId, UUID itemId) {
        var pedido = this.findById(pedidoId);
        var item = this.itemService.findById(itemId);
        pedido.addItem(item);
        pedidoRepository.save(pedido);
    }
}
