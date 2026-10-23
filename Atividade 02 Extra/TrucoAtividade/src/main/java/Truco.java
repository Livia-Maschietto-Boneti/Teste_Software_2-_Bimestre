
import java.util.List;

public class Truco {

    private final int participantes;
    private final int[] pontos = new int[3];
    private final int[] vazasVencidas = new int[3];

    private int valorMao = 1;
    private int vazasJogadas = 0;
    private int equipeVencedora = 0;

    private boolean trucoPedido = false;
    private boolean encerrado = false;

    public Truco(int participantes) {
        if (!participantesValidos(participantes)) {
            throw new IllegalArgumentException(
                    "A partida exige 2 jogadores ou 4 jogadores em duplas."
            );
        }

        this.participantes = participantes;
    }

    public static boolean participantesValidos(int quantidade) {
        return quantidade == 2 || quantidade == 4;
    }

    public boolean maoValida(List<Carta> cartas) {
        return cartas != null && cartas.size() == 3;
    }

    public int vencedorVaza(List<Carta> cartas, Carta vira) {
        if (cartas == null || cartas.size() != 2) {
            throw new IllegalArgumentException(
                    "Uma vaza precisa de exatamente duas cartas."
            );
        }

        if (vira == null || cartas.get(0) == null
                || cartas.get(1) == null) {
            throw new IllegalArgumentException(
                    "As cartas e a vira são obrigatórias."
            );
        }

        int comparacao = cartas.get(0).comparar(
                cartas.get(1), vira
        );

        if (comparacao > 0) return 1;
        if (comparacao < 0) return 2;

        return 0;
    }

    public void registrarVaza(int equipe) {
        verificarPartidaAtiva();

        if (equipe != 1 && equipe != 2) {
            throw new IllegalArgumentException(
                    "Equipe inválida."
            );
        }

        if (equipeVencedora != 0 || vazasJogadas >= 3) {
            throw new IllegalStateException(
                    "Esta mão já foi encerrada."
            );
        }

        vazasJogadas++;
        vazasVencidas[equipe]++;

        if (vazasVencidas[equipe] == 2) {
            equipeVencedora = equipe;
            adicionarPontos(equipe, valorMao);
        }
    }

    public boolean pedirTruco() {
        verificarPartidaAtiva();

        if (trucoPedido) {
            return false;
        }

        trucoPedido = true;
        valorMao = 3;

        return true;
    }

    public void adicionarPontos(int equipe, int quantidade) {
        verificarPartidaAtiva();

        if (equipe != 1 && equipe != 2) {
            throw new IllegalArgumentException(
                    "Equipe inexistente."
            );
        }

        if (quantidade <= 0) {
            throw new IllegalArgumentException(
                    "A quantidade de pontos deve ser positiva."
            );
        }

        pontos[equipe] += quantidade;

        if (pontos[equipe] >= 12) {
            encerrado = true;
        }
    }

    public void novaMao() {
        verificarPartidaAtiva();

        valorMao = 1;
        vazasJogadas = 0;
        vazasVencidas[1] = 0;
        vazasVencidas[2] = 0;
        equipeVencedora = 0;
        trucoPedido = false;
    }

    private void verificarPartidaAtiva() {
        if (encerrado) {
            throw new IllegalStateException(
                    "A partida já foi encerrada."
            );
        }
    }

    public int getParticipantes() {
        return participantes;
    }

    public int getValorMao() {
        return valorMao;
    }

    public int getVazasJogadas() {
        return vazasJogadas;
    }

    public int getVazasVencidas(int equipe) {
        if (equipe != 1 && equipe != 2) {
            throw new IllegalArgumentException(
                    "Equipe inexistente."
            );
        }

        return vazasVencidas[equipe];
    }

    public int getEquipeVencedora() {
        return equipeVencedora;
    }

    public int getPontos(int equipe) {
        if (equipe != 1 && equipe != 2) {
            throw new IllegalArgumentException(
                    "Equipe inexistente."
            );
        }

        return pontos[equipe];
    }

    public boolean isEncerrado() {
        return encerrado;
    }

    public boolean podeJogarVaza() {
        return !encerrado
                && equipeVencedora == 0
                && vazasJogadas < 3;
    }
}
