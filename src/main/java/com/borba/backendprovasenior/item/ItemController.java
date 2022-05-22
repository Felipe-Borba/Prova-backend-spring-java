package com.borba.backendprovasenior.item;

import com.querydsl.core.types.Predicate;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springdoc.api.annotations.ParameterObject;
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
    @Operation(
            summary = "List all items paginated", tags = {"Item"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "Return item list paginated")
            }
    )
    public ResponseEntity<Page<Item>> findAll(
            @QuerydslPredicate(root = Item.class) Predicate predicate,
            @ParameterObject Pageable pageable
    ) {
        return ResponseEntity.ok(this.item.findAll(predicate, pageable));
    }

    @GetMapping(value = "/{id}")
    @Operation(
            summary = "Load item by id", tags = {"Item"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "Return item data"),
                    @ApiResponse(responseCode = "400", description = "Item does not exists in database"),
            }
    )
    public ResponseEntity<Item> findById(@PathVariable UUID id) throws RuntimeException {
        return ResponseEntity.ok(this.item.findById(id));
    }

    @PostMapping
    @Operation(
            summary = "Create item", tags = {"Item"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "Return item created"),
                    @ApiResponse(responseCode = "400", description = "Request has invalid body"),
            }
    )
    public ResponseEntity<Item> save(@Valid @RequestBody Item item) {
        return ResponseEntity.ok(this.item.save(item));
    }

    @PutMapping(value = "/{id}")
    @Operation(
            summary = "Update item by id", tags = {"Item"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "Return item updated"),
                    @ApiResponse(responseCode = "400", description = "Item does not exists in database"),
            }
    )
    public ResponseEntity<Item> update(@PathVariable UUID id, @RequestBody Item item) throws RuntimeException {
        return ResponseEntity.ok(this.item.update(id, item));
    }

    @DeleteMapping(value = "/{id}")
    @Operation(
            summary = "Delete item by id", tags = {"Item"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "Item successfully deleted"),
                    @ApiResponse(responseCode = "400", description = "Item does not exists in database"),
                    @ApiResponse(responseCode = "409", description = "Item is associated to pedido")
            }
    )
    public ResponseEntity<String> delete(@PathVariable UUID id) throws RuntimeException {
        this.item.delete(id);
        return ResponseEntity.ok("Deletado");
    }
}
