package com.borba.backendprovasenior.item;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.UUID;

public interface ItemRepository extends JpaRepository<Item, UUID>, QuerydslPredicateExecutor<Item> {
}
