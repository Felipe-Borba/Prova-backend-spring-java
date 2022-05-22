package com.borba.backendprovasenior.pedido;

import com.borba.backendprovasenior.item.Item;
import org.json.JSONObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class PedidoControllerAcceptanceTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private PedidoRepository repository;

    @Test
    public void whenCallCreatePedido_shouldCreatePedido_IfAllParamsAreValid() throws Exception {
        var requestBody = new JSONObject();
        requestBody.put("descricao", "pedido teste");
        requestBody.put("valorDesconto", 0.5);
        requestBody.put("status", "ABERTO");

        var result = mvc.perform(post("/pedido")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody.toString())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.descricao").value("pedido teste"))
                .andExpect(jsonPath("$.valorDesconto").value(0.5))
                .andExpect(jsonPath("$.valorTotalServico").value(0))
                .andExpect(jsonPath("$.valorTotalProduto").value(0))
                .andExpect(jsonPath("$.valorTotalPedido").value(0))
                .andExpect(jsonPath("$.status").value("ABERTO"))
                .andExpect(jsonPath("$.pedidoItems.length()").value(0))
                .andReturn();

        var responseObj = new JSONObject(result.getResponse().getContentAsString());
        var id = responseObj.get("id").toString();
        var pedido = this.repository.findById(UUID.fromString(id)).get();

        assertThat(pedido.getDescricao()).isEqualTo("pedido teste");
        assertThat(pedido.getValorDesconto()).isEqualTo(0.5);
        assertThat(pedido.getStatus()).isEqualTo(Pedido.Status.ABERTO);
    }

    @Test
    public void whenCallCreatePedido_shouldReturnError_IfAllParamsAreMissing() throws Exception {
        var requestBody = new JSONObject();

        var result = mvc.perform(post("/pedido")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody.toString())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message[*]").value("Descrição é obrigatório"))
                .andReturn();

    }

    @Test
    public void whenCallListPedido_shouldReturnPagedListOfPedido() throws Exception {
        mvc.perform(get("/pedido"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value("0efd4845-fc94-46d4-a36b-591375526042"))
                .andExpect(jsonPath("$.content[0].descricao").value("pedido1"))
                .andExpect(jsonPath("$.content[0].valorDesconto").value(0))
                .andExpect(jsonPath("$.content[0].valorTotalServico").value(50.0))
                .andExpect(jsonPath("$.content[0].valorTotalProduto").value(4.5))
                .andExpect(jsonPath("$.content[0].valorTotalPedido").value(54.5))
                .andExpect(jsonPath("$.content[0].status").value("ABERTO"))
                .andExpect(jsonPath("$.content[0].pedidoItems.length()").value(2))
                .andExpect(jsonPath("$.content.length()").value(1))
                .andReturn();
    }

    @Test
    public void whenCallDeletePedido_shouldDeletePedido_IfPedidoExits() throws Exception {
        var request = delete("/pedido/0efd4845-fc94-46d4-a36b-591375526042");
        mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value("Deletado"))
                .andReturn();

        var pedido = this.repository.existsById(UUID.fromString("0efd4845-fc94-46d4-a36b-591375526042"));
        assertThat(pedido).isFalse();
    }

    @Test
    public void whenCallDeletePedido_shouldNotDeletePedido_IfPedidoDoNotExists() throws Exception {
        var request = delete("/pedido/d990bc28-0768-4b64-8e32-4945ffc4a998");
        mvc.perform(request)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message.[*]").value("Id não encontado: d990bc28-0768-4b64-8e32-4945ffc4a998"))
                .andReturn();

        var item = this.repository.existsById(UUID.fromString("0efd4845-fc94-46d4-a36b-591375526042"));
        assertThat(item).isTrue();
    }

    @Test
    public void whenCallGetPedido_shouldReturnPedido_ifExistInDatabase() throws Exception {
        mvc.perform(get("/pedido/0efd4845-fc94-46d4-a36b-591375526042"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("0efd4845-fc94-46d4-a36b-591375526042"))
                .andExpect(jsonPath("$.descricao").value("pedido1"))
                .andExpect(jsonPath("$.valorDesconto").value(0.0))
                .andExpect(jsonPath("$.valorTotalServico").value(50))
                .andExpect(jsonPath("$.valorTotalProduto").value(4.5))
                .andExpect(jsonPath("$.valorTotalPedido").value(54.5))
                .andExpect(jsonPath("$.status").value("ABERTO"))
                .andExpect(jsonPath("$.pedidoItems.length()").value(2))
                .andReturn();
    }

    @Test
    public void whenCallGetPedido_shouldReturnError_ifNotExistInDatabase() throws Exception {
        mvc.perform(get("/pedido/d990bc28-0768-4b64-8e32-4945ffc4a998"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message.[*]").value("Id não encontado: d990bc28-0768-4b64-8e32-4945ffc4a998"))
                .andReturn();
    }

    @Test
    public void whenCallUpdatePedido_shouldUpdateOnlyStatusAndDescription_IfAllParamsAreValid() throws Exception {
        var requestBody = new JSONObject();
        requestBody.put("descricao", "pedido teste");
        requestBody.put("valorDesconto", 0.5);
        requestBody.put("status", "FECHADO");

        var result = mvc.perform(put("/pedido/0efd4845-fc94-46d4-a36b-591375526042")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody.toString())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("0efd4845-fc94-46d4-a36b-591375526042"))
                .andExpect(jsonPath("$.descricao").value("pedido teste"))
                .andExpect(jsonPath("$.valorDesconto").value(0.0))
                .andExpect(jsonPath("$.valorTotalServico").value(50))
                .andExpect(jsonPath("$.valorTotalProduto").value(4.5))
                .andExpect(jsonPath("$.valorTotalPedido").value(54.5))
                .andExpect(jsonPath("$.status").value("FECHADO"))
                .andExpect(jsonPath("$.pedidoItems.length()").value(2))
                .andReturn();

        var responseObj = new JSONObject(result.getResponse().getContentAsString());
        var id = responseObj.get("id").toString();
        var pedido = this.repository.findById(UUID.fromString(id)).get();

        assertThat(pedido.getDescricao()).isEqualTo("pedido teste");
        assertThat(pedido.getValorDesconto()).isEqualTo(0);
        assertThat(pedido.getStatus()).isEqualTo(Pedido.Status.FECHADO);
    }

    @Test
    void whenCallRemovePedidoItem_shouldRemoveItemFromPedido_ifItemExistInPedido() throws Exception {
        var result = mvc.perform(put("/pedido/0efd4845-fc94-46d4-a36b-591375526042/remover-item/d4530f2f-f247-47a8-b779-4acdf84508a7"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value("Item removido"))
                .andReturn();

        var pedido = this.repository.findById(UUID.fromString("0efd4845-fc94-46d4-a36b-591375526042")).get();

        assertThat(pedido.getPedidoItems()).hasSize(1);
    }

    @Test
    void whenCallRemovePedidoItem_shouldNotRemoveItemFromPedido_ifItemDoNotExistInPedido() throws Exception {
        var result = mvc.perform(put("/pedido/0efd4845-fc94-46d4-a36b-591375526042/remover-item/67967d13-a8e4-4204-a8ff-60c21c66f6e2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value("Item removido"))
                .andReturn();

        var pedido = this.repository.findById(UUID.fromString("0efd4845-fc94-46d4-a36b-591375526042")).get();

        assertThat(pedido.getPedidoItems()).hasSize(2);
    }
}
