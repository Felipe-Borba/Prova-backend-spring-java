package com.borba.backendprovasenior.pedido;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.UUID;

public interface PedidoRepository extends JpaRepository<Pedido, UUID>, QuerydslPredicateExecutor<Pedido> {
}
