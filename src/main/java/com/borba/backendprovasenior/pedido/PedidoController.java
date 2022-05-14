package com.borba.backendprovasenior.pedido;

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
    private PedidoUseCase pedido;

    @GetMapping
    public ResponseEntity<List<Pedido>> findAll() {
        return ResponseEntity.ok(this.pedido.findAll());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Pedido> findById(@PathVariable UUID id)  {
        return ResponseEntity.ok(this.pedido.findById(id));
    }

    @PostMapping
    public ResponseEntity<Pedido> insert(@RequestBody Pedido pedido) {
        return ResponseEntity.ok(this.pedido.save(pedido));
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Pedido> update(@PathVariable UUID id, @RequestBody Pedido pedido)  {
        return ResponseEntity.ok(this.pedido.update(id, pedido));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Map<String, Boolean>> delete(@PathVariable UUID id) {
        this.pedido.delete(id);
        return ResponseEntity.ok(Map.of("Deletado", true));
    }
}
