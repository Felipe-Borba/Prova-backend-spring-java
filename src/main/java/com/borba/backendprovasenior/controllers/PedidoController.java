package com.borba.backendprovasenior.controllers;

import com.borba.backendprovasenior.entidades.Pedido;
import com.borba.backendprovasenior.exception.RecursoNaoEncontrado;
import com.borba.backendprovasenior.repositorios.PedidoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping(value = "/pedido")
public class PedidoController {

    @Autowired
    private PedidoRepositorio repositorio;

    @GetMapping
    public ResponseEntity<List<Pedido>> findAll() {
        return ResponseEntity.ok(repositorio.findAll());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Pedido> findById(@PathVariable UUID id) throws RuntimeException {
        var pedido = repositorio.findById(id).orElseThrow(() -> new RecursoNaoEncontrado("Id não encontado: " + id));
        return ResponseEntity.ok(pedido);
    }

    @PostMapping
    public ResponseEntity<Pedido> insert(@RequestBody Pedido pedido) {
        var result = repositorio.save(pedido);
        return ResponseEntity.ok(result);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Pedido> update(@PathVariable UUID id, @RequestBody Pedido pedido) throws RuntimeException {
        var newPedido = repositorio.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontrado("Id não encontado: " + id));

        newPedido.setDescricao(pedido.getDescricao());
        newPedido.setValorDesconto(pedido.getValorDesconto());
        newPedido.setPedidoItems(pedido.getPedidoItems());
        return ResponseEntity.ok(repositorio.save(newPedido));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Map<String, Boolean>> delete(@PathVariable UUID id) throws RuntimeException {
        var pedido = repositorio.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontrado("Id não encontado: " + id));

        repositorio.delete(pedido);
        return ResponseEntity.ok(Map.of("Deletado", true));
    }
}
