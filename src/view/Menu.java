package view;

import java.sql.SQLException;
import java.util.Scanner;

import controller.*;
import model.Admin;
import model.Utilizador;
import  model.Funcionario;
import model.dao.AdminDao;
import model.dao.ClienteDAO;
import model.dao.FuncionarioDAO;
import model.dao.ReparacaoDAO;

public class Menu {

    private Scanner sc = new Scanner(System.in);
    private UtilizadorController controller = new UtilizadorController();
    private AdminController controllerAdmin = new AdminController();
    private EquipamentoController controllerEquipamento = new EquipamentoController();
    private Utilizador userLogado = null;
    private ReparacaoController controllerReparacao = new ReparacaoController();
    private ClienteDAO clienteDAO = new ClienteDAO();
    private Utilizador utilizador = new Utilizador();
    private FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
    private Funcionario funcionario;
    private AdminDao adminDAO = new AdminDao();
    private ReparacaoDAO reparacaoDAO = new ReparacaoDAO();
    private TestesController controllerTestes = new TestesController();
    private PecaController controllerPeca = new PecaController();

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
            //tiramos daqui a solucao para voltar atras https://stackoverflow.com/questions/60023456/how-to-go-back-to-previous-switch
            theLabel: while (true){
            switch (opcao) {
                case 3: //cliente
                    if (userLogado != null){
                        if (clienteDAO.VerSeCliente(userLogado.getId())){
                            do {
                                System.out.println("\nMenu: Cliente");
                                System.out.println("4- Consultar Dados");
                                System.out.println("3 - Atualizar dados");
                                System.out.println("2 - Registar Equipamento");
                                System.out.println("1 - Inserir reparação");
                                System.out.println("0 - Sair");
                                System.out.print("Opção: ");
                            opcaoCliente = sc.nextInt();
                            switch (opcaoCliente) {
                                case 0:
                                    userLogado = controller.Logout(userLogado);
                                    break;
                                case 1:
                                    controllerReparacao.criarReparacao(sc, userLogado);
                                    break;
                                case 2:
                                    controllerEquipamento.criarEquipamento(sc, userLogado);
                                    break;
                                case 3:
                                    controller.atualizarDados(sc, userLogado);
                                    break;
                                case 4:
                                    controller.ConsultarDados(userLogado);
                                    break;
                                case 5:
                                    controllerReparacao.criarReparacao(sc, userLogado);
                                    break;

                            } }while (opcaoCliente != 0);
                            break theLabel;
                        }
                        if (adminDAO.VerSeGestor(userLogado.getId())){
                            do{
                                System.out.println("\nMenu: Admin");
                                System.out.println("14- Pesquisar utilizador por email");
                                System.out.println("13- Pesquisar utilizador por username");
                                System.out.println("12- Pesquisar utilizador por nome");
                                System.out.println("11- Ver lista de utilizadores");
                                System.out.println("10- Inserir peca");
                                System.out.println("9- Aprovar reparacoes");
                                System.out.println("8- Reparacoes por Aprovar");
                                System.out.println("7- Atualizar dados de outra conta ");
                                System.out.println("6- Consultar dados de outra conta");
                                System.out.println("5- Criar outro gestor");
                                System.out.println("4- Ativar conta");
                                System.out.println("3- Ver contas por ativar");
                                System.out.println("2- Alterar Dados");
                                System.out.println("1- Consultar Dados");
                                System.out.println("0 - Sair");
                                opcaoAdmin = sc.nextInt();
                                switch (opcaoAdmin) {
                                    case 0:
                                        userLogado = controller.Logout(userLogado);
                                        break;
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
                                        controllerAdmin.CriarOutroGestor(userLogado,sc);
                                        break;
                                    case 6:
                                        controllerAdmin.ConsultarDadosGestor(userLogado,sc);
                                        break;
                                    case 7:
                                        controllerAdmin.atualizarDadosGestor(sc,userLogado);
                                        break;
                                    case 8:
                                        controllerReparacao.verReparacoesPorAprovar(userLogado);
                                        break;
                                    case 9:
                                        controllerReparacao.aceitarReparacao(sc, userLogado);
                                        break;
                                    case 10:
                                        controllerPeca.inserirPecaController(sc,userLogado);
                                        break;
                                    case 11:
                                        controllerAdmin.verUtilizador(userLogado);
                                        break;
                                    case 12:
                                        controllerAdmin.ConsultarNome(userLogado, sc);
                                        break;
                                    case 13:
                                        controllerAdmin.ConsultarUsername(userLogado, sc);
                                        break;
                                    case 14:
                                        controllerAdmin.ConsultarEmail(userLogado, sc);
                                        break;
                                }
                            } while (opcaoAdmin != 0);
                            break theLabel;
                        }
                        if (FuncionarioDAO.verSeFuncionario(userLogado.getId())){
                            do {
                                System.out.println("\nMenu: Funcionario");
                                System.out.println("1 - Ver reparacoes por aprovar ");
                                System.out.println("2 - Aprovar ou rejeitar reparacoes ");
                                System.out.println("3 - Submeter testes");
                                System.out.println("4 - Finalizar reparacao");
                                opcaoFuncionario = sc.nextInt();
                                switch (opcaoFuncionario) {
                                    case 0:
                                        userLogado = controller.Logout(userLogado);
                                        break;
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

                                }
                            } while (opcaoFuncionario != 0);
                            break theLabel;
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
                    break;

                default:
                    System.out.println("Opção inválida!");
            }}
        } while (opcao != 0);
    }
}
