package com.borba.backendprovasenior.pedido;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PedidoRepositorio extends JpaRepository<Pedido, UUID> {
}
