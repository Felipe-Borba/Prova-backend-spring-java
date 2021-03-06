package com.borba.backendprovasenior.item;

import com.borba.backendprovasenior.exception.errors.ConflictError;
import com.borba.backendprovasenior.exception.errors.RecursoNaoEncontrado;
import com.querydsl.core.types.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ItemService {
    @Autowired
    private ItemRepository repository;

    public Page<Item> findAll(Predicate predicate, Pageable pageable) {
        return repository.findAll(predicate, pageable);
    }

    public Item findById(UUID id) {
        return repository.findById(id).orElseThrow(() -> new RecursoNaoEncontrado("Id não encontado: " + id));
    }

    public Item save(Item item) {
        if(item.getActive() == null) {
            item.setActive(true);
        }
        return repository.save(item);
    }

    public Item update(UUID id, Item item) {
        var newItem = this.findById(id);
        newItem.setDescricao(item.getDescricao());
        newItem.setTipo(item.getTipo());
        newItem.setValor(item.getValor());
        newItem.setActive(item.getActive());
        return this.save(newItem);
    }

    public void delete(UUID id) {
        var item = this.findById(id);
        var hasAssociation = !item.getPedidos().isEmpty();
        if (hasAssociation) {
            throw new ConflictError("Não é possível excluir um item associado a um pedido");
        }

        repository.delete(item);
    }
}
