package view;

import java.sql.SQLException;
import java.util.Scanner;

import controller.*;
import model.Utilizador;
import  model.Funcionario;
import model.dao.ClienteDAO;
import model.dao.FuncionarioDAO;

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

    public void start() throws SQLException {
        controllerAdmin.verAdmins(sc);

        int opcao;
        int opcaoCliente;

        do {
            /*System.out.println("\n===== MENU =====");
            System.out.println("11- Iniciar reparacao");
            System.out.println("10-Criar equipamento");
            System.out.println("9- Atualizar dados de outra conta ");
            System.out.println("8- Consultar dados de outra conta");
            System.out.println("7- Criar outro gestor");
            System.out.println("6- Ativar conta");
            System.out.println("5- Ver contas por ativar");
            System.out.println("4- Alterar Dados"); */
            System.out.println("3 - Mais opções");
            System.out.println("2 - Login");
            System.out.println("1 - Registar utilizador");
            System.out.println("0 - Sair");
            System.out.print("Opção: ");
            opcao = sc.nextInt();
            sc.nextLine();
            theLabel: while (true) {
            switch (opcao) {
            /*    case 11:
                    controllerReparacao.criarReparacao(sc, userLogado);
                    break;
                case 10:
                    controllerEquipamento.criarEquipamento(sc,userLogado);
                    break;
                case 9:
                    controllerAdmin.atualizarDadosGestor(sc, userLogado);
                    break;
                case 8:
                    controllerAdmin.ConsultarDadosGestor(userLogado,sc);
                    break;
                case 7:
                    controllerAdmin.CriarOutroGestor(userLogado,sc);
                    break;
                case 6:
                    controllerAdmin.ativarConta(userLogado,sc);
                    break;
                case 5:
                    controllerAdmin.verContasPorAtivar(userLogado);
                    break;
                case 4:
                    if (userLogado != null){
                        controller.atualizarDados(sc,userLogado);
                    } else {
                        System.out.println("Fazer login primeiro!!!");
                    }break; */

                case 3: //cliente
                    if (userLogado != null){

                        if (clienteDAO.VerSeCliente(userLogado.getId())){
                            do {
                                System.out.println("Menu: Cliente");
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
                                    continue theLabel;
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
                            } }while (opcaoCliente != 0);
                            break;
                        }


                        //Funcionario






                       // controller.ConsultarDados(userLogado);
                    } else {
                        System.out.println("Fazer login primeiro!!!");
                    }break;
                case 2:
                    userLogado = controller.Login(sc);
                    break;
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
