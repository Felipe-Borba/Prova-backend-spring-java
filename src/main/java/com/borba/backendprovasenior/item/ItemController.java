package com.borba.backendprovasenior.item;

import com.borba.backendprovasenior.exception.RecursoNaoEncontrado;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping(value = "/item")
public class ItemController {

    @Autowired
    private ItemRepositorio repositorio;

    @GetMapping
    public ResponseEntity<List<Item>> findAll() {
        return ResponseEntity.ok(repositorio.findAll());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Item> findById(@PathVariable UUID id) throws RuntimeException {
        var item = repositorio.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontrado("Id não encontado: " + id));
        return ResponseEntity.ok(item);
    }

    @PostMapping
    public ResponseEntity<Item> insert(@RequestBody Item item) {
        var result = repositorio.save(item);
        return ResponseEntity.ok(result);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Item> update(@PathVariable UUID id, @RequestBody Item item) throws RuntimeException {
        var newItem = repositorio.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontrado("Id não encontado: " + id));

        newItem.setDescricao(item.getDescricao());
        newItem.setTipo(item.getTipo());
        newItem.setValor(item.getValor());
        return ResponseEntity.ok(repositorio.save(newItem));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Map<String, Boolean>> delete(@PathVariable UUID id) throws RuntimeException {
        var item = repositorio.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontrado("Id não encontado: " + id));

        repositorio.delete(item);
        return ResponseEntity.ok(Map.of("Deletado", true));
    }
}
