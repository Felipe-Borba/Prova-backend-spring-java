package com.borba.backendprovasenior.pedido;

import com.borba.backendprovasenior.item.Item;
import com.querydsl.core.types.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping(value = "/pedido")
public class PedidoController {

    @Autowired
    private PedidoService pedido;

    @GetMapping
    public ResponseEntity<Page<Pedido>> findAll(
            @QuerydslPredicate(root = Pedido.class) Predicate predicate,
            Pageable pageable
    ) {
        return ResponseEntity.ok(this.pedido.findAll(predicate, pageable));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Pedido> findById(@PathVariable UUID id)  {
        return ResponseEntity.ok(this.pedido.findById(id));
    }

    @PostMapping
    public ResponseEntity<Pedido> insert(@Valid @RequestBody Pedido pedido) {
        return ResponseEntity.ok(this.pedido.save(pedido));
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Pedido> update(@PathVariable UUID id, @RequestBody Pedido pedido)  {
        return ResponseEntity.ok(this.pedido.update(id, pedido));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        this.pedido.delete(id);
        return ResponseEntity.ok("Deletado");
    }

    @PutMapping(value = "/{pedidoId}/adicionar-item/{itemId}")
    public ResponseEntity<String> addItem(@PathVariable UUID pedidoId, @PathVariable UUID itemId) {
        this.pedido.addItem(pedidoId, itemId);
        return ResponseEntity.ok("Item adicionado");
    }

    @PutMapping(value = "/{pedidoId}/remover-item/{itemId}")
    public ResponseEntity<String> removeItem(@PathVariable UUID pedidoId, @PathVariable UUID itemId) {
        this.pedido.removeItem(pedidoId, itemId);
        return ResponseEntity.ok("Item removido");
    }

    @PutMapping(value = "/{pedidoId}/desconto")
    public ResponseEntity<Pedido> applyDiscount(@PathVariable UUID pedidoId, @RequestBody Pedido pedido) {
        return ResponseEntity.ok(this.pedido.applyDiscount(pedidoId, pedido.getValorDesconto()));
    }
}
