package com.borba.backendprovasenior.pedido;

import com.borba.backendprovasenior.exception.errors.ConflictError;
import com.borba.backendprovasenior.item.ItemService;
import com.borba.backendprovasenior.util.ItemCreator;
import com.borba.backendprovasenior.util.PedidoCreator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.HashSet;
import java.util.Optional;
import java.util.UUID;

@ExtendWith(SpringExtension.class)
class PedidoServiceTest {

    @InjectMocks
    private PedidoService pedidoService;

    @Mock
    private PedidoRepository pedidoRepositoryMock;

    @Mock
    private ItemService itemServiceMock;

    @BeforeEach
    void setup() {
        Pedido pedido = PedidoCreator.createPedidoWithProdutoServico();
        BDDMockito.when(pedidoRepositoryMock.save(ArgumentMatchers.any()))
                .thenReturn(pedido);

        BDDMockito.when(pedidoRepositoryMock.findById(ArgumentMatchers.any()))
                .thenReturn(Optional.of(pedido));
    }

    @Nested
    class update {

        @Nested
        class when_update_all_params {

            private Pedido updatedPedido;

            @BeforeEach
            void update_all_params() {
                Pedido pedido = new Pedido();
                pedido.setDescricao("descrição atualizada");
                pedido.setStatus(Pedido.Status.FECHADO);
                pedido.setValorDesconto(0.7);
                pedido.setPedidoItems(new HashSet<>());

                updatedPedido = pedidoService.update(UUID.randomUUID(), pedido);
            }

            @Test
            void should_update_descricao() {
                Assertions.assertThat(updatedPedido.getDescricao()).isEqualTo("descrição atualizada");
            }

            @Test
            void should_update_status() {
                Assertions.assertThat(updatedPedido.getStatus()).isEqualTo(Pedido.Status.FECHADO);
            }

            @Test
            void should_not_update_valorDesconto() {
                Assertions.assertThat(updatedPedido.getValorDesconto()).isEqualTo(0.3);
            }

            @Test
            void should_not_update_pedidoItems() {
                Assertions.assertThat(updatedPedido.getPedidoItems()).hasSize(2);
            }
        }
    }

    @Nested
    class applyDiscount {

        @Nested
        class when_pedido_status_is_fechado {

            @BeforeEach
            void test() {
                var pedido = PedidoCreator.createPedidoWithProdutoServico();
                pedido.setStatus(Pedido.Status.FECHADO);

                BDDMockito.when(pedidoRepositoryMock.findById(ArgumentMatchers.any()))
                        .thenReturn(Optional.of(pedido));
            }

            @Test
            void should_throw_error() {
                var exception = Assertions.catchThrowable(
                        () -> pedidoService.applyDiscount(UUID.randomUUID(), 1.0)
                );

                Assertions.assertThat(exception)
                        .isExactlyInstanceOf(ConflictError.class)
                        .hasMessageContaining("Não é possível aplicar desconto a um pedido fechado");
            }
        }

        @Nested
        class when_pedido_status_is_aberto {

            private Pedido pedidoWithDiscount;

            @BeforeEach
            void test() {
                pedidoWithDiscount = pedidoService.applyDiscount(UUID.randomUUID(), 0.9);
            }

            @Test
            void should_update_pedido_valorDesconto() {
                Assertions.assertThat(pedidoWithDiscount.getValorDesconto()).isEqualTo(0.9);
            }
        }
    }

    @Nested
    class addItem {

        @Nested
        class when_item_status_is_not_active {

            @BeforeEach
            void test() {
                var item = ItemCreator.createProduto();
                item.setActive(false);

                BDDMockito.when(itemServiceMock.findById(ArgumentMatchers.any())).thenReturn(item);
            }

            @Test
            void should_throw_error() {
                var exception = Assertions.catchThrowable(
                        () -> pedidoService.addItem(UUID.randomUUID(), ArgumentMatchers.any())
                );

                Assertions.assertThat(exception)
                        .isExactlyInstanceOf(ConflictError.class)
                        .hasMessageContaining("Não é possível adicionar um item desativado no pedido");
            }
        }

        @Nested
        class when_item_status_is_active {

            @BeforeEach
            void test() {
                var item = ItemCreator.createProduto();
                item.setActive(true);

                BDDMockito.when(itemServiceMock.findById(ArgumentMatchers.any())).thenReturn(item);

                pedidoService.addItem(UUID.randomUUID(), ArgumentMatchers.any());
            }

            @Test
            void should_add_item_to_pedido() {
                Assertions.assertThat(pedidoRepositoryMock.save(ArgumentMatchers.any()).getPedidoItems())
                        .hasSize(3);
            }
        }
    }
}