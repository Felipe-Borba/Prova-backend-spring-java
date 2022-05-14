package com.borba.backendprovasenior.pedido;

import com.borba.backendprovasenior.exception.RecursoNaoEncontrado;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PedidoService {
    @Autowired
    private PedidoRepository repository;

    public List<Pedido> findAll() {
        return repository.findAll();
    }

    public Pedido findById(UUID id)  {
        return repository.findById(id).orElseThrow(() -> new RecursoNaoEncontrado("Id não encontado: " + id));
    }

    public Pedido save(Pedido pedido)  {
        return repository.save(pedido);
    }

    public Pedido update(UUID id, Pedido pedido)  {
        var newPedido = this.findById(id);
        newPedido.setDescricao(pedido.getDescricao());
        newPedido.setValorDesconto(pedido.getValorDesconto());
        newPedido.setPedidoItems(pedido.getPedidoItems());
        return repository.save(newPedido);
    }

    public void delete(UUID id)  {
        var pedido = this.findById(id);
        repository.delete(pedido);
    }
}
