package com.borba.backendprovasenior.item;

import com.borba.backendprovasenior.exception.RecursoNaoEncontrado;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ItemService {
    @Autowired
    private ItemRepository repository;

    public List<Item> findAll() {
        return repository.findAll();
    }

    public Item findById(UUID id)  {
        return repository.findById(id).orElseThrow(() -> new RecursoNaoEncontrado("Id não encontado: " + id));
    }

    public Item save(Item item)  {
        return repository.save(item);
    }

    public Item update(UUID id, Item item)  {
        var newItem = this.findById(id);
        newItem.setDescricao(item.getDescricao());
        newItem.setTipo(item.getTipo());
        newItem.setValor(item.getValor());
        return this.save(newItem);
    }

    public void delete(UUID id)  {
        var item = this.findById(id);
        repository.delete(item);
    }
}
