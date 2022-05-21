package com.borba.backendprovasenior.pedido;

import com.borba.backendprovasenior.exception.errors.ConflictError;
import com.borba.backendprovasenior.exception.errors.RecursoNaoEncontrado;
import com.borba.backendprovasenior.item.ItemService;
import com.querydsl.core.types.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PedidoService {
    @Autowired
    private PedidoRepository pedidoRepository;
    @Autowired
    private ItemService itemService;

    public Page<Pedido> findAll(Predicate predicate, Pageable pageable) {
        return pedidoRepository.findAll(predicate, pageable);
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
        newPedido.setStatus(pedido.getStatus());
        return pedidoRepository.save(newPedido);
    }

    public Pedido applyDiscount(UUID id, Double value) {
        var pedido = this.findById(id);
        if(pedido.getStatus() == Pedido.Status.FECHADO) {
            throw new ConflictError("Não é possível aplicar desconto a um pedido fechado");
        }
        pedido.setValorDesconto(value);
        return pedidoRepository.save(pedido);
    }

    public void delete(UUID id) {
        var pedido = this.findById(id);
        pedidoRepository.delete(pedido);
    }

    public void addItem(UUID pedidoId, UUID itemId) {
        var pedido = this.findById(pedidoId);
        var item = this.itemService.findById(itemId);
        if (!item.getActive()) {
            throw new ConflictError("Não é possível adicionar um item desativado no pedido");
        }
        pedido.addItem(item);
        pedidoRepository.save(pedido);
    }

    public void removeItem(UUID pedidoId, UUID itemId) {
        var pedido = this.findById(pedidoId);
        var item = this.itemService.findById(itemId);
        pedido.getPedidoItems().remove(item);
        pedidoRepository.save(pedido);
    }
}
