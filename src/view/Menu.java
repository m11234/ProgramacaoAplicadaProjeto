package view;

import java.sql.SQLException;
import java.util.Scanner;

import controller.ClienteController;
import controller.UtilizadorController;
import model.Utilizador;
import controller.FuncionarioController;
import  model.Funcionario;

public class Menu {

    private Scanner sc = new Scanner(System.in);
    private UtilizadorController controller = new UtilizadorController();
    private Utilizador userLogado = null;

    public void start() throws SQLException {

        int opcao;

        do {
            System.out.println("\n===== MENU =====");
            System.out.println("4- Alterar Dados");
            System.out.println("3 - Consultar dados");
            System.out.println("2 - Login");
            System.out.println("1 - Registar utilizador");
            System.out.println("0 - Sair");
            System.out.print("Opção: ");
            opcao = sc.nextInt();
            sc.nextLine();

            switch (opcao) {
                case 4:
                    if (userLogado != null){
                        controller.atualizarDados(sc,userLogado);
                    } else {
                        System.out.println("Fazer login primeiro!!!");
                    }break;

                case 3:
                    if (userLogado != null){
                        controller.ConsultarDados(userLogado);
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
            }

        } while (opcao != 0);
    }
}