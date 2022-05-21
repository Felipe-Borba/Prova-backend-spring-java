package com.borba.backendprovasenior.item;

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
@RequestMapping(value = "/item")
public class ItemController {

    @Autowired
    private ItemService item;


    @GetMapping
    public ResponseEntity<Page<Item>> findAll(
            @QuerydslPredicate(root = Item.class) Predicate predicate,
            Pageable pageable
    ) {
        return ResponseEntity.ok(this.item.findAll(predicate, pageable));
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
