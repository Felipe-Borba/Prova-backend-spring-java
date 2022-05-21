package com.borba.backendprovasenior.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/item")
public class ItemController {

    @Autowired
    private ItemService item;

    @Autowired
    private ItemCustomRepository itemCustomRepository;

    @GetMapping(value = "/custom")
    public ResponseEntity<List<Item>> find(
            @RequestParam(value = "descricao", required = false) String descricao,
            @RequestParam(value = "tipo", required = false) Item.Tipo tipo,
            @RequestParam(value = "active", required = false) Boolean active
    ) {
        var itemList = this.itemCustomRepository.find(descricao,tipo,active);
        return ResponseEntity.ok(itemList);
    }

    @GetMapping
    public ResponseEntity<Page<Item>> findAll(Pageable pageable) {
        return ResponseEntity.ok(this.item.findAll(pageable));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Item> findById(@PathVariable UUID id) throws RuntimeException {
        return ResponseEntity.ok(this.item.findById(id));
    }

    @PostMapping
    public ResponseEntity<Item> save(@Valid @RequestBody Item item) {
        return ResponseEntity.ok(this.item.save(item));
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Item> update(@PathVariable UUID id, @RequestBody Item item) throws RuntimeException {
        return ResponseEntity.ok(this.item.update(id, item));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) throws RuntimeException {
        this.item.delete(id);
        return ResponseEntity.ok("Deletado");
    }
}
