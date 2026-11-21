package br.edu.exemplo;
import br.edu.exemplo.model.Produto;
import br.edu.exemplo.repository.ProdutoRepository;
import br.edu.exemplo.service.ProdutoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest @Transactional
class ProdutoServiceIntegrationTest {
 @Autowired ProdutoService service;
 @Autowired ProdutoRepository repository;
 @Test void deveCadastrarERecuperarProduto(){
  Produto salvo = service.cadastrar("Teclado", 150.0);
  assertNotNull(salvo.getId());
  repository.flush();
  Produto produto = repository.findById(salvo.getId()).orElseThrow();
  assertEquals("Teclado", produto.getNome());
  assertEquals(150.0, produto.getPreco());
 }
 @Test void naoDevePersistirProdutoComNomeEmBranco(){
  long antes = repository.count();
  assertThrows(IllegalArgumentException.class, () -> service.cadastrar("", 80.0));
  long depois = repository.count();
  assertEquals(antes, depois);
 }
 @Test void deveAtualizarPrecoPersistido(){
  Produto salvo = service.cadastrar("Mouse", 80.0);
  service.atualizarPreco(salvo.getId(), 95.0);
  repository.flush();
  Produto produto = repository.findById(salvo.getId()).orElseThrow();
  assertEquals(95.0, produto.getPreco());
 }
}
