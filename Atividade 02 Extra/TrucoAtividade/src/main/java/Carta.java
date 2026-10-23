
import java.util.Objects;


public class Carta {

    public enum Valor {
        QUATRO, CINCO, SEIS, SETE,
        RAINHA, VALETE, REI, AS, DOIS, TRES
    }

    public enum Naipe {
        OUROS, ESPADAS, COPAS, PAUS
    }

    private final Valor valor;
    private final Naipe naipe;

    public Carta(Valor valor, Naipe naipe) {
        this.valor = Objects.requireNonNull(valor);
        this.naipe = Objects.requireNonNull(naipe);
    }

    public Valor getValor() {
        return valor;
    }

    public Naipe getNaipe() {
        return naipe;
    }

    private int indiceTruco() {
        return switch (valor) {
            case QUATRO -> 0;
            case CINCO -> 1;
            case SEIS -> 2;
            case SETE -> 3;
            case RAINHA -> 4;
            case VALETE -> 5;
            case REI -> 6;
            case AS -> 7;
            case DOIS -> 8;
            case TRES -> 9;
        };
    }

    private int forcaManilha() {
        return switch (naipe) {
            case OUROS -> 1;
            case ESPADAS -> 2;
            case COPAS -> 3;
            case PAUS -> 4;
        };
    }

    public int comparar(Carta outra, Carta vira) {
        Objects.requireNonNull(outra);
        Objects.requireNonNull(vira);

        int indiceManilha = (vira.indiceTruco() + 1) % 10;

        boolean estaManilha =
                this.indiceTruco() == indiceManilha;

        boolean outraManilha =
                outra.indiceTruco() == indiceManilha;

        if (estaManilha && outraManilha) {
            return Integer.compare(
                    this.forcaManilha(),
                    outra.forcaManilha()
            );
        }

        if (estaManilha) {
            return 1;
        }

        if (outraManilha) {
            return -1;
        }

        return Integer.compare(
                this.indiceTruco(),
                outra.indiceTruco()
        );
    }
}
