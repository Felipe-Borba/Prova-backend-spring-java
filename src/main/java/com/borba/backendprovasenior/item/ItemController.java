package com.borba.backendprovasenior.item;

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
    private ItemService item;

    @GetMapping
    public ResponseEntity<List<Item>> findAll() {
        return ResponseEntity.ok(this.item.findAll());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Item> findById(@PathVariable UUID id) throws RuntimeException {
        return ResponseEntity.ok(this.item.findById(id));
    }

    @PostMapping
    public ResponseEntity<Item> insert(@RequestBody Item item) {
        return ResponseEntity.ok(this.item.save(item));
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Item> update(@PathVariable UUID id, @RequestBody Item item) throws RuntimeException {
        return ResponseEntity.ok(this.item.update(id, item));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Map<String, Boolean>> delete(@PathVariable UUID id) throws RuntimeException {
        this.item.delete(id);
        return ResponseEntity.ok(Map.of("Deletado", true));
    }
}
