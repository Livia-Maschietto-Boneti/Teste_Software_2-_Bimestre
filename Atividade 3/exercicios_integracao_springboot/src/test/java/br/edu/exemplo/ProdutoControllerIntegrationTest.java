package br.edu.exemplo;
import com.fasterxml.jackson.databind.JsonNode;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.springframework.http.MediaType;
@SpringBootTest @AutoConfigureMockMvc @Transactional
class ProdutoControllerIntegrationTest {
 @Autowired MockMvc mockMvc;
 @Autowired com.fasterxml.jackson.databind.ObjectMapper mapper;
 @Test void deveCadastrarProdutoPelaApi() throws Exception {
  mockMvc.perform(post("/produtos")
      .contentType(MediaType.APPLICATION_JSON)
      .content("{\"nome\":\"Mouse\",\"preco\":80.0}"))
    .andExpect(status().isOk())
    .andExpect(jsonPath("$.id").isNumber())
    .andExpect(jsonPath("$.nome").value("Mouse"))
    .andExpect(jsonPath("$.preco").value(80.0));
 }
 @Test void deveBuscarProdutoPelaApi() throws Exception {
  MvcResult resposta = mockMvc.perform(post("/produtos")
      .contentType(MediaType.APPLICATION_JSON)
      .content("{\"nome\":\"Mouse\",\"preco\":80.0}"))
    .andExpect(status().isOk())
    .andReturn();

  JsonNode json = mapper.readTree(resposta.getResponse().getContentAsString());
  long id = json.get("id").asLong();

  mockMvc.perform(get("/produtos/" + id))
    .andExpect(status().isOk())
    .andExpect(jsonPath("$.id").value(id))
    .andExpect(jsonPath("$.nome").value("Mouse"))
    .andExpect(jsonPath("$.preco").value(80.0));
 }
}
