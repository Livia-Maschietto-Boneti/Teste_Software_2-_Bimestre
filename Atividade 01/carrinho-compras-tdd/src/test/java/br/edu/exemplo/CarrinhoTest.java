package br.edu.exemplo;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CarrinhoTest {

    @Test
    void deveIniciarVazio() {
        Carrinho carrinho = new Carrinho();

        assertEquals(0, carrinho.getTotal(), 0.001);
    }

    @Test
    void deveAdicionarProdutos() {
        Carrinho carrinho = new Carrinho();

        carrinho.adicionar(new Produto("Teclado", 100));
        carrinho.adicionar(new Produto("Mouse", 50));

        assertEquals(150, carrinho.getTotal(), 0.001);
    }

    @Test
    void deveRemoverProduto() {
        Carrinho carrinho = new Carrinho();
        Produto produto = new Produto("Mouse", 50);

        carrinho.adicionar(new Produto("Teclado", 100));
        carrinho.adicionar(produto);
        carrinho.remover(produto);

        assertEquals(100, carrinho.getTotal(), 0.001);
    }

    @Test
    void deveAplicarDesconto() {
        Carrinho carrinho = new Carrinho();

        carrinho.adicionar(new Produto("Notebook", 600));

        assertEquals(540, carrinho.getTotal(), 0.001);
    }
    
}