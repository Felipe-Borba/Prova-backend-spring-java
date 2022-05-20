package com.borba.backendprovasenior.pedido;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
}
