package com.borba.backendprovasenior.item;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ItemControllerAcceptanceTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void shouldReturnListOfItems() throws Exception {
        var request = get("/item");
        mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("67967d13-a8e4-4204-a8ff-60c21c66f6e2"))
                .andExpect(jsonPath("$[0].tipo").value("PRODUTO"))
                .andExpect(jsonPath("$[0].valor").value(10.5))
                .andExpect(jsonPath("$[0].descricao").value("fio"))
                .andExpect(jsonPath("$.length()").value(3));

    }

    @Test
    public void shouldDeleteOneItem() throws Exception {
        var request = delete("/item/67967d13-a8e4-4204-a8ff-60c21c66f6e2");
        mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value("Deletado"))
                .andReturn();

        mvc.perform(get("/item/67967d13-a8e4-4204-a8ff-60c21c66f6e2"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Id não encontado: 67967d13-a8e4-4204-a8ff-60c21c66f6e2"));
    }

    @Test
    public void shouldLoadItemById() throws Exception {
        mvc.perform(get("/item/67967d13-a8e4-4204-a8ff-60c21c66f6e2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("67967d13-a8e4-4204-a8ff-60c21c66f6e2"))
                .andExpect(jsonPath("$.tipo").value("PRODUTO"))
                .andExpect(jsonPath("$.valor").value(10.5))
                .andExpect(jsonPath("$.descricao").value("fio"));
    }

    @Test
    public void shouldUpdateItem() throws Exception {
        var requestBody = new JSONObject();
        requestBody.put("descricao", "tecido");
        requestBody.put("tipo", "SERVICO");
        requestBody.put("valor", 5);

        mvc.perform(put("/item/67967d13-a8e4-4204-a8ff-60c21c66f6e2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody.toString())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("67967d13-a8e4-4204-a8ff-60c21c66f6e2"))
                .andExpect(jsonPath("$.tipo").value("SERVICO"))
                .andExpect(jsonPath("$.valor").value(5))
                .andExpect(jsonPath("$.descricao").value("tecido"));

        mvc.perform(get("/item/67967d13-a8e4-4204-a8ff-60c21c66f6e2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("67967d13-a8e4-4204-a8ff-60c21c66f6e2"))
                .andExpect(jsonPath("$.tipo").value("SERVICO"))
                .andExpect(jsonPath("$.valor").value(5))
                .andExpect(jsonPath("$.descricao").value("tecido"));
    }

    @Test
    public void shouldCreateItem() throws Exception {
        var requestBody = new JSONObject();
        requestBody.put("descricao", "tecido");
        requestBody.put("tipo", "SERVICO");
        requestBody.put("valor", 5);

        var result = mvc.perform(post("/item")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody.toString())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tipo").value("SERVICO"))
                .andExpect(jsonPath("$.valor").value(5))
                .andExpect(jsonPath("$.descricao").value("tecido")).andReturn();

        var responseObj = new JSONObject(result.getResponse().getContentAsString());
        System.out.println(responseObj);
        var id = responseObj.get("id").toString();
        mvc.perform(get("/item/"+id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.tipo").value("SERVICO"))
                .andExpect(jsonPath("$.valor").value(5))
                .andExpect(jsonPath("$.descricao").value("tecido"));
    }

}
