package com.borba.backendprovasenior.repositorios;

import com.borba.backendprovasenior.entidades.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PedidoRepositorio extends JpaRepository<Pedido, UUID> {
}
