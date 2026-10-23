

import org.junit.jupiter.api.Test;


import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TrucoTest {

    private Carta carta(Carta.Valor valor, Carta.Naipe naipe) {
        return new Carta(valor, naipe);
    }

    @Test
    void deveRejeitarPartidaComUmJogador() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Truco(1)
        );
    }

    @Test
    void devePermitirPartidaComDoisJogadores() {
        assertDoesNotThrow(() -> new Truco(2));
    }

    @Test
    void devePermitirPartidaComDuasDuplas() {
        assertDoesNotThrow(() -> new Truco(4));
    }

    @Test
    void deveAceitarDistribuicaoDeTresCartas() {
        Truco truco = new Truco(2);

        List<Carta> cartas = List.of(
                carta(Carta.Valor.AS, Carta.Naipe.PAUS),
                carta(Carta.Valor.DOIS, Carta.Naipe.COPAS),
                carta(Carta.Valor.TRES, Carta.Naipe.OUROS)
        );

        assertTrue(truco.maoValida(cartas));
    }

    @Test
    void deveRejeitarDistribuicaoDeQuatroCartas() {
        Truco truco = new Truco(2);

        List<Carta> cartas = List.of(
                carta(Carta.Valor.AS, Carta.Naipe.PAUS),
                carta(Carta.Valor.DOIS, Carta.Naipe.COPAS),
                carta(Carta.Valor.TRES, Carta.Naipe.OUROS),
                carta(Carta.Valor.REI, Carta.Naipe.ESPADAS)
        );

        assertFalse(truco.maoValida(cartas));
    }

    @Test
    void deveVencerComCartaDeMaiorForca() {
        Carta vira = carta(
                Carta.Valor.SEIS, Carta.Naipe.OUROS
        );

        List<Carta> jogadas = List.of(
                carta(Carta.Valor.AS, Carta.Naipe.PAUS),
                carta(Carta.Valor.TRES, Carta.Naipe.COPAS)
        );

        Truco truco = new Truco(2);

        assertEquals(2, truco.vencedorVaza(jogadas, vira));
    }

    @Test
    void naoDeveDeclararVitoriaComApenasUmaVaza() {
        Truco truco = new Truco(2);

        truco.registrarVaza(1);

        assertEquals(1, truco.getVazasVencidas(1));
        assertEquals(0, truco.getEquipeVencedora());
        assertEquals(0, truco.getPontos(1));
    }

    @Test
    void deveIniciarMaoValendoUmPonto() {
        Truco truco = new Truco(2);

        assertEquals(1, truco.getValorMao());
    }

    @Test
    void deveRejeitarPontuacaoNegativa() {
        Truco truco = new Truco(2);

        assertThrows(
                IllegalArgumentException.class,
                () -> truco.adicionarPontos(1, -1)
        );
    }

    @Test
    void devePermitirPedirTrucoEAumentarValorDaMao() {
        Truco truco = new Truco(2);

        assertTrue(truco.pedirTruco());
    }

    @Test
    void deveEncerrarPartidaAoAlcancarDozePontos() {
        Truco truco = new Truco(2);

        truco.adicionarPontos(1, 12);

        assertEquals(12, truco.getPontos(1));
        assertTrue(truco.isEncerrado());
    }

    @Test
    void naoDeveEncerrarPartidaComOnzePontos() {
        Truco truco = new Truco(2);

        truco.adicionarPontos(1, 11);

        assertEquals(11, truco.getPontos(1));
        assertFalse(truco.isEncerrado());
    }

    @Test
    void deveIdentificarEmpateEntreCartasIguais() {
        Carta vira = carta(
                Carta.Valor.SEIS, Carta.Naipe.OUROS
        );

        List<Carta> jogadas = List.of(
                carta(Carta.Valor.AS, Carta.Naipe.PAUS),
                carta(Carta.Valor.AS, Carta.Naipe.PAUS)
        );

        Truco truco = new Truco(2);

        assertEquals(0, truco.vencedorVaza(jogadas, vira));
    }

    @Test
    void deveAumentarValorDaMaoParaTresPontos() {
        Truco truco = new Truco(2);

        assertTrue(truco.pedirTruco());
        assertEquals(3, truco.getValorMao());
    }

    @Test
    void deveRejeitarSegundoPedidoDeTruco() {
        Truco truco = new Truco(2);

        assertTrue(truco.pedirTruco());
        assertFalse(truco.pedirTruco());
        assertEquals(3, truco.getValorMao());
    }

    @Test
    void deveRejeitarPontuacaoZeroOuNegativa() {
        Truco truco = new Truco(2);

        assertThrows(
                IllegalArgumentException.class,
                () -> truco.adicionarPontos(1, 0)
        );

        assertThrows(
                IllegalArgumentException.class,
                () -> truco.adicionarPontos(1, -5)
        );
    }

    @Test
    void deveRejeitarJogadorInexistente() {
        Truco truco = new Truco(2);

        assertThrows(
                IllegalArgumentException.class,
                () -> truco.adicionarPontos(3, 1)
        );
    }

    @Test
    void naoDevePermitirPontuarDepoisDoFimDoJogo() {
        Truco truco = new Truco(2);
        truco.adicionarPontos(1, 12);

        assertThrows(
                IllegalStateException.class,
                () -> truco.adicionarPontos(2, 1)
        );
    }

    @Test
    void naoDevePermitirPedirTrucoDepoisDoFimDoJogo() {
        Truco truco = new Truco(2);
        truco.adicionarPontos(1, 12);

        assertThrows(
                IllegalStateException.class,
                truco::pedirTruco
        );
    }

    @Test
    void deveAtualizarPontuacaoDoJogadorUm() {
        Truco truco = new Truco(2);

        truco.adicionarPontos(1, 2);

        assertEquals(2, truco.getPontos(1));
        assertEquals(0, truco.getPontos(2));
    }
}
