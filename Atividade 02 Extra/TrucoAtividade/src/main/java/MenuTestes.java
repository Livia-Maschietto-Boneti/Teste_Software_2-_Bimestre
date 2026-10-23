
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;



public class MenuTestes {

    private static final Map<String, String> testes = new LinkedHashMap<>();

    static {
        testes.put("CT01", "deveRejeitarPartidaComUmJogador");
        testes.put("CT02", "devePermitirPartidaComDoisJogadores");
        testes.put("CT03", "devePermitirPartidaComDuasDuplas");
        testes.put("CT04", "deveAceitarDistribuicaoDeTresCartas");
        testes.put("CT05", "deveRejeitarDistribuicaoDeQuatroCartas");
        testes.put("CT06", "deveVencerComCartaDeMaiorForca");
        testes.put("CT07", "naoDeveDeclararVitoriaComApenasUmaVaza");
        testes.put("CT08", "deveIniciarMaoValendoUmPonto");
        testes.put("CT09", "deveRejeitarPontuacaoNegativa");
        testes.put("CT10", "devePermitirPedirTrucoEAumentarValorDaMao");
        testes.put("CT11", "deveEncerrarPartidaAoAlcancarDozePontos");
        testes.put("CT12", "naoDeveEncerrarPartidaComOnzePontos");
        testes.put("CT13", "deveIdentificarEmpateEntreCartasIguais");
        testes.put("CT14", "deveAumentarValorDaMaoParaTresPontos");
        testes.put("CT15", "deveRejeitarSegundoPedidoDeTruco");
        testes.put("CT16", "deveRejeitarPontuacaoZeroOuNegativa");
        testes.put("CT17", "deveRejeitarJogadorInexistente");
        testes.put("CT18", "naoDevePermitirPontuarDepoisDoFimDoJogo");
        testes.put("CT19", "naoDevePermitirPedirTrucoDepoisDoFimDoJogo");
        testes.put("CT20", "deveAtualizarPontuacaoDoJogadorUm");
    }

    public void iniciar() {
        main(new String[0]);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int opcao = 0;

        do {
            System.out.println("\n===== TESTADOR TRUCO PAULISTA =====");
            System.out.println("1 - Executar todos os testes");
            System.out.println("2 - Executar um teste específico");
            System.out.println("3 - Listar os casos de teste");
            System.out.println("0 - Sair");
            System.out.print("Escolha uma opção: ");

            if (!scanner.hasNextInt()) {
                System.out.println("Opção inválida.");
                scanner.nextLine();
                continue;
            }

            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1 -> executarComando("mvn test");
                case 2 -> executarTesteEspecifico(scanner);
                case 3 -> listarTestes();
                case 0 -> System.out.println("Encerrando o menu.");
                default -> System.out.println("Opção inválida.");
            }
        } while (opcao != 0);

        scanner.close();
    }

    private static void listarTestes() {
        System.out.println("\n===== CASOS DE TESTE =====");

        testes.forEach((id, nome) ->
                System.out.println(id + " - " + nome)
        );
    }

    private static void executarTesteEspecifico(Scanner scanner) {
        listarTestes();

        System.out.print("\nDigite o código do teste (ex.: CT01): ");
        String codigo = scanner.nextLine().trim().toUpperCase();

        String metodo = testes.get(codigo);

        if (metodo == null) {
            System.out.println("Código de teste inexistente.");
            return;
        }

        executarComando(
                "mvn \"-Dtest=TrucoTest#" + metodo + "\" test"
        );
    }

    private static void executarComando(String comando) {
        try {
            ProcessBuilder builder = new ProcessBuilder(
                    "cmd.exe", "/c", comando
            );

            builder.inheritIO();

            Process processo = builder.start();
            int resultado = processo.waitFor();

            if (resultado == 0) {
                System.out.println("\nExecução concluída com sucesso.");
            } else {
                System.out.println("\nA execução terminou com falha.");
            }

        } catch (Exception e) {
            System.out.println("Erro ao executar os testes: " + e.getMessage());
        }
    }
}
