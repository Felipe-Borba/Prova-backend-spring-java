package com.borba.backendprovasenior.repositorios;

import com.borba.backendprovasenior.entidades.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ItemRepositorio extends JpaRepository<Item, UUID> {
}
