package view;

import java.sql.SQLException;
import java.util.Scanner;

import controller.*;
import model.Utilizador;
import model.dao.AdminDao;
import model.dao.ClienteDAO;
import model.dao.FuncionarioDAO;

public class Menu {

    private final Scanner sc = new Scanner(System.in);
    private final UtilizadorController controller = new UtilizadorController();
    private final AdminController controllerAdmin = new AdminController();
    private final EquipamentoController controllerEquipamento = new EquipamentoController();
    private Utilizador userLogado = null;
    private final ReparacaoController controllerReparacao = new ReparacaoController();
    private final AdminDao adminDAO = new AdminDao();
    private final TestesController controllerTestes = new TestesController();
    private final PecaController controllerPeca = new PecaController();

    public void start() throws SQLException {
        controllerAdmin.verAdmins(sc);

        int opcao;
        int opcaoCliente;
        int opcaoAdmin;
        int opcaoFuncionario;

        do {
            System.out.println("3 - Mais opções");
            System.out.println("2 - Login");
            System.out.println("1 - Registar utilizador");
            System.out.println("0 - Sair");
            System.out.print("Opção: ");
            opcao = sc.nextInt();
            sc.nextLine();

            /*
              Logica de navegacao de menus (voltar ao menu anterior).
              Solucao adaptada de: Andy Turner
              Fonte: https://stackoverflow.com/questions/60023456/how-to-go-back-to-previous-switch
              Acedido em: 8 de Abril de 2026.
             */
            theLabel: while (true){
                switch (opcao) {
                    case 3:
                        if (userLogado != null){
                            if (ClienteDAO.VerSeCliente(userLogado.getId())){
                                do {
                                    System.out.println("\nMenu: Cliente");
                                    System.out.println("6 - Consultar pedidos de reparacao: ");
                                    System.out.println("5 - Iniciar pedido para apagar conta");
                                    System.out.println("4- Criar pedido reparacao");
                                    System.out.println("3 - Criar equipamento");
                                    System.out.println("2 - Consultar dados");
                                    System.out.println("1 - Atualizar Dados");
                                    System.out.println("0 - Sair");
                                    System.out.print("Opção: ");
                                    opcaoCliente = sc.nextInt();
                                    switch (opcaoCliente) {
                                        case 0:
                                            userLogado = controller.Logout(userLogado);
                                            break theLabel;
                                        case 1:
                                            controller.atualizarDados(sc, userLogado);
                                            break;
                                        case 2:
                                            controller.ConsultarDados(userLogado);
                                            break;
                                        case 3:
                                            controllerEquipamento.criarEquipamento(sc, userLogado);
                                            break;
                                        case 4:
                                            controllerReparacao.criarReparacao(sc, userLogado);
                                            break;
                                        case 5:
                                            ClienteController.ApagarContaPedido(userLogado,sc);
                                            break;
                                        case 6:
                                            ClienteController.pesquisarPedidosReparacao(userLogado,sc);
                                            break;
                                    } }while (true);
                            }
                            if (adminDAO.VerSeGestor(userLogado.getId())){
                                do{
                                    System.out.println("\nMenu: Admin");
                                    System.out.println("20- Consultar equipamentos por codigo: ");
                                    System.out.println("19- Consultar reparação por ID: ");
                                    System.out.println("18- Ver notificações de reparações a ocorrer á mais de 10 dias");
                                    System.out.println("17- Ver notificações do stock");
                                    System.out.println("16- Pesquisar utilizador por email");
                                    System.out.println("15- Pesquisar utilizador por username");
                                    System.out.println("14- Pesquisar utilizador por nome");
                                    System.out.println("13- Ver lista de utilizadores");
                                    System.out.println("12- Inserir peca");
                                    System.out.println("11- Aprovar reparacoes");
                                    System.out.println("10- Ver notificacoes novas reparacoes");
                                    System.out.println("9- Atualizar dados de outra conta ");
                                    System.out.println("8- Consultar dados de outra conta");
                                    System.out.println("7- Criar outro gestor");
                                    System.out.println("6- Apagar conta de outro utilizador");
                                    System.out.println("5- Notificacoes pedidos para apagar conta");
                                    System.out.println("4- Ativar conta");
                                    System.out.println("3- Ver notificacoes contas novas");
                                    System.out.println("2- Alterar Dados");
                                    System.out.println("1- Consultar Dados");
                                    System.out.println("0 - Sair");
                                    opcaoAdmin = sc.nextInt();
                                    switch (opcaoAdmin) {
                                        case 0:
                                            userLogado = controller.Logout(userLogado);
                                            break theLabel;
                                        case 1:
                                            userLogado = controller.ConsultarDados(userLogado);
                                            break;
                                        case 2:
                                            controller.atualizarDados(sc,userLogado);
                                            break;
                                        case 3:
                                            controllerAdmin.verContasPorAtivar(userLogado);
                                            break;
                                        case 4:
                                            controllerAdmin.ativarConta(userLogado,sc);
                                            break;
                                        case 5:
                                            controllerAdmin.verContasPorApagar(userLogado);
                                            break;
                                        case 6:
                                            controllerAdmin.apagarContas(userLogado,sc);
                                            break;
                                        case 7:
                                            controllerAdmin.CriarOutroGestor(userLogado,sc);
                                            break;
                                        case 8:
                                            controllerAdmin.ConsultarDadosGestor(userLogado,sc);
                                            break;
                                        case 9:
                                            controllerAdmin.atualizarDadosGestor(sc,userLogado);
                                            break;
                                        case 10:
                                            controllerReparacao.verReparacoesPorAprovar(userLogado);
                                            break;
                                        case 11:
                                            controllerReparacao.aceitarReparacao(sc, userLogado);
                                            break;
                                        case 12:
                                            controllerPeca.inserirPecaController(sc,userLogado);
                                            break;
                                        case 13:
                                            controllerAdmin.verUtilizador(userLogado);
                                            break;
                                        case 14:
                                            controllerAdmin.ConsultarNome(userLogado, sc);
                                            break;
                                        case 15:
                                            controllerAdmin.ConsultarUsername(userLogado, sc);
                                            break;
                                        case 16:
                                            controllerAdmin.ConsultarEmail(userLogado, sc);
                                            break;
                                        case 17:
                                            controllerPeca.pecaInferior(userLogado);
                                            break;
                                        case 18:
                                            controllerReparacao.notificacaoDezDiasSemFinalizacao(userLogado);
                                            break;
                                        case 19:
                                            controllerAdmin.ConsultarReparacao(userLogado,sc);
                                            break;
                                        case 20:
                                            controllerAdmin.PesquisarEquipamento(userLogado,sc);
                                            break;
                                    }
                                } while (true);
                            }
                            if (FuncionarioDAO.verSeFuncionario(userLogado.getId())){
                                do {
                                    System.out.println("\nMenu: Funcionario");
                                    System.out.println("0 - Sair");
                                    System.out.println("1 - Ver reparacoes por aprovar ");
                                    System.out.println("2 - Aprovar ou rejeitar reparacoes ");
                                    System.out.println("3 - Submeter testes");
                                    System.out.println("4 - Finalizar reparacao");
                                    System.out.println("5 - Associar peca utilizada com reparacao");
                                    opcaoFuncionario = sc.nextInt();
                                    switch (opcaoFuncionario) {
                                        case 0:
                                            userLogado = controller.Logout(userLogado);
                                            break theLabel;
                                        case 1:
                                            controllerReparacao.verReparacoesPorAprovarF(userLogado);
                                            break;
                                        case 2:
                                            controllerReparacao.aceitarReparacaoF(sc,userLogado);
                                            break;
                                        case 3:
                                            controllerTestes.submeterTesteF(sc,userLogado);
                                            break;
                                        case 4:
                                            controllerReparacao.FinalizarReparacaoF(sc,userLogado);
                                            break;
                                        case 5:
                                            controllerPeca.inserirPecaUsada(sc,userLogado);
                                            break;

                                    }
                                } while (true);
                            }
                            //Funcionario
                            // controller.ConsultarDados(userLogado);
                        } else {
                            System.out.println("Fazer login primeiro!!!");
                        }break theLabel;
                    case 2:
                        userLogado = controller.Login(sc);
                        break theLabel;
                    case 1:
                        controller.registar(sc);
                        break;

                    case 0:
                        //System.out.println("Adeus!");
                        userLogado = controller.Logout(userLogado);
                        break theLabel;

                    default:
                        System.out.println("Opção inválida!");
                        break theLabel;


                }
            }
        } while (opcao != 0);
    }
}
