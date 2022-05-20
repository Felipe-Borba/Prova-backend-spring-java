package com.borba.backendprovasenior.item;

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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ItemControllerAcceptanceTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ItemRepository repository;

    @Test
    public void whenCallListItem_shouldReturnListOfItems() throws Exception {
        var request = get("/item");
        mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value("67967d13-a8e4-4204-a8ff-60c21c66f6e2"))
                .andExpect(jsonPath("$.content[0].tipo").value("PRODUTO"))
                .andExpect(jsonPath("$.content[0].valor").value(10.5))
                .andExpect(jsonPath("$.content[0].descricao").value("fio"))
                .andExpect(jsonPath("$.content.length()").value(3))
                .andReturn();

    }

    @Test
    public void whenCallDeleteItem_shouldDeleteItem_IfItemIsNotAssociatedToPedido() throws Exception {
        var request = delete("/item/67967d13-a8e4-4204-a8ff-60c21c66f6e2");
        mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value("Deletado"))
                .andReturn();

        var item = this.repository.existsById(UUID.fromString("67967d13-a8e4-4204-a8ff-60c21c66f6e2"));
        assertThat(item).isFalse();
    }

    @Test
    public void whenCallDeleteItem_shouldNotDeleteItem_IfItemIsAssociatedToPedido() throws Exception {
        var request = delete("/item/d4530f2f-f247-47a8-b779-4acdf84508a7");
        mvc.perform(request)
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message.[*]").value("Não é possível excluir um item associado a um pedido"))
                .andReturn();

        var item = this.repository.existsById(UUID.fromString("d4530f2f-f247-47a8-b779-4acdf84508a7"));
        assertThat(item).isTrue();
    }

    @Test
    public void whenCallGetItem_shouldReturnItem_ifItemExistInDatabase() throws Exception {
        mvc.perform(get("/item/67967d13-a8e4-4204-a8ff-60c21c66f6e2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("67967d13-a8e4-4204-a8ff-60c21c66f6e2"))
                .andExpect(jsonPath("$.tipo").value("PRODUTO"))
                .andExpect(jsonPath("$.valor").value(10.5))
                .andExpect(jsonPath("$.descricao").value("fio"))
                .andReturn();
    }

    @Test
    public void whenCallGetItem_shouldReturnError_ifItemDoNotExistInDatabase() throws Exception {
        mvc.perform(get("/item/3f7b79f4-5688-4ae8-bcf9-7b0ed0c989a5"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message.[*]").value("Id não encontado: 3f7b79f4-5688-4ae8-bcf9-7b0ed0c989a5"))
                .andReturn();
    }

    @Test
    public void whenCallUpdateItem_shouldUpdateItem_IfAllParamsAreValid() throws Exception {
        var requestBody = new JSONObject();
        requestBody.put("descricao", "cortar");
        requestBody.put("tipo", "SERVICO");
        requestBody.put("valor", 5.55);

        mvc.perform(put("/item/67967d13-a8e4-4204-a8ff-60c21c66f6e2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody.toString())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("67967d13-a8e4-4204-a8ff-60c21c66f6e2"))
                .andExpect(jsonPath("$.tipo").value("SERVICO"))
                .andExpect(jsonPath("$.valor").value(5.55))
                .andExpect(jsonPath("$.descricao").value("cortar"))
                .andReturn();

        var item = this.repository.findById(UUID.fromString("67967d13-a8e4-4204-a8ff-60c21c66f6e2")).get();
        assertThat(item.getActive()).isTrue();
        assertThat(item.getTipo()).isEqualTo(Item.Tipo.SERVICO);
        assertThat(item.getValor()).isEqualTo(5.55);
        assertThat(item.getDescricao()).isEqualTo("cortar");
    }

    @Test
    public void whenCallCreateItem_shouldCreateItem_ifAllParamsAreValid() throws Exception {
        var requestBody = new JSONObject();
        requestBody.put("descricao", "tecido");
        requestBody.put("tipo", "PRODUTO");
        requestBody.put("valor", 5.75);

        var result = mvc.perform(post("/item")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody.toString())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tipo").value("PRODUTO"))
                .andExpect(jsonPath("$.valor").value(5.75))
                .andExpect(jsonPath("$.descricao").value("tecido"))
                .andReturn();

        var responseObj = new JSONObject(result.getResponse().getContentAsString());
        var id = responseObj.get("id").toString();
        var item = this.repository.findById(UUID.fromString(id)).get();
        assertThat(item.getActive()).isTrue();
        assertThat(item.getTipo()).isEqualTo(Item.Tipo.PRODUTO);
        assertThat(item.getValor()).isEqualTo(5.75);
        assertThat(item.getDescricao()).isEqualTo("tecido");
    }

    @Test
    public void whenCallCreateItem_shouldReturnError_ifCalledWithoutParams() throws Exception {
        var requestBody = new JSONObject();

        mvc.perform(post("/item")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody.toString())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message.tipo").value("must not be null"))
                .andExpect(jsonPath("$.message.valor").value("must not be null"))
                .andExpect(jsonPath("$.message.descricao").value("Descrição é obrigatório"))
                .andReturn();
    }

    @Test
    public void whenCallCreateItem_shouldReturnError_ifValorIsLessThanZero() throws Exception {
        var requestBody = new JSONObject();
        requestBody.put("valor", -1);

        mvc.perform(post("/item")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody.toString())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message.tipo").value("must not be null"))
                .andExpect(jsonPath("$.message.valor").value("must be greater than or equal to 0"))
                .andExpect(jsonPath("$.message.descricao").value("Descrição é obrigatório"))
                .andReturn();
    }
}
