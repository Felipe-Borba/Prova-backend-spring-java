package com.borba.backendprovasenior.item;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ItemRepositorio extends JpaRepository<Item, UUID> {
}
