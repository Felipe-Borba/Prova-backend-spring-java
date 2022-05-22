package com.borba.backendprovasenior.pedido;

import com.querydsl.core.types.Predicate;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
@RequestMapping(value = "/pedido")
public class PedidoController {

    @Autowired
    private PedidoService pedido;

    @GetMapping
    @Operation(
            summary = "List pedidos paginated", tags = {"Pedido"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "Return pedido list paginated")
            }
    )
    public ResponseEntity<Page<Pedido>> findAll(
            @QuerydslPredicate(root = Pedido.class) Predicate predicate,
            @ParameterObject Pageable pageable
    ) {
        return ResponseEntity.ok(this.pedido.findAll(predicate, pageable));
    }

    @GetMapping(value = "/{id}")
    @Operation(
            summary = "Load pedido by id", tags = {"Pedido"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "Return pedido"),
                    @ApiResponse(responseCode = "400", description = "Pedido does not exists in database"),
            }
    )
    public ResponseEntity<Pedido> findById(@PathVariable UUID id)  {
        return ResponseEntity.ok(this.pedido.findById(id));
    }

    @PostMapping
    @Operation(
            summary = "Create pedido", tags = {"Pedido"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "Return created pedido"),
            }
    )
    public ResponseEntity<Pedido> create(@Valid @RequestBody Pedido pedido) {
        return ResponseEntity.ok(this.pedido.save(pedido));
    }

    @PutMapping(value = "/{id}")
    @Operation(
            summary = "Update pedido by id", tags = {"Pedido"},
            parameters = {
                    @Parameter(name = "descricao", example = "Table Production order"),
                    @Parameter(name = "status", example = "ABERTO"),
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Return updated pedido"),
                    @ApiResponse(responseCode = "400", description = "Pedido does not exists in database"),
            }
    )
    public ResponseEntity<Pedido> update(@PathVariable UUID id, @RequestBody Pedido pedido)  {
        return ResponseEntity.ok(this.pedido.update(id, pedido));
    }

    @DeleteMapping(value = "/{id}")
    @Operation(
            summary = "Delete pedido by id", tags = {"Pedido"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "Pedido deleted"),
                    @ApiResponse(responseCode = "400", description = "Pedido does not exists in database"),
            }
    )
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        this.pedido.delete(id);
        return ResponseEntity.ok("Deletado");
    }

    @PutMapping(value = "/{pedidoId}/adicionar-item/{itemId}")
    @Operation(
            summary = "Add item in pedido", tags = {"Pedido"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "Item added in pedido"),
                    @ApiResponse(responseCode = "400", description = "Pedido or item does not exists in database"),
                    @ApiResponse(responseCode = "409", description = "Item is disabled")
            }
    )
    public ResponseEntity<String> addItem(@PathVariable UUID pedidoId, @PathVariable UUID itemId) {
        this.pedido.addItem(pedidoId, itemId);
        return ResponseEntity.ok("Item adicionado");
    }

    @PutMapping(value = "/{pedidoId}/remover-item/{itemId}")
    @Operation(
            summary = "Remove item from pedido", tags = {"Pedido"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "Item removed from pedido if it is associated"),
                    @ApiResponse(responseCode = "400", description = "Item does not exists in database"),
            }
    )
    public ResponseEntity<String> removeItem(@PathVariable UUID pedidoId, @PathVariable UUID itemId) {
        this.pedido.removeItem(pedidoId, itemId);
        return ResponseEntity.ok("Item removido");
    }

    @PutMapping(value = "/{pedidoId}/desconto")
    @Operation(
            summary = "Update pedido discount", tags = {"Pedido"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "Return pedido with new discount"),
                    @ApiResponse(responseCode = "400", description = "Pedido does not exists in database"),
                    @ApiResponse(responseCode = "409", description = "Pedido status is closed")
            }
    )
    public ResponseEntity<Pedido> applyDiscount(@PathVariable UUID pedidoId, @RequestBody Pedido pedido) {
        return ResponseEntity.ok(this.pedido.applyDiscount(pedidoId, pedido.getValorDesconto()));
    }
}
