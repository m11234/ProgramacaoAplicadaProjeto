package view;

import java.util.Scanner;
import controller.UtilizadorController;

public class Menu {

    private Scanner sc = new Scanner(System.in);
    private UtilizadorController controller = new UtilizadorController();

    public void start() {

        int opcao;

        do {
            System.out.println("\n===== MENU =====");
            System.out.println("1 - Registar utilizador");
            System.out.println("0 - Sair");
            System.out.print("Opção: ");
            opcao = sc.nextInt();
            sc.nextLine();

            switch (opcao) {
                case 1:
                    controller.registar(sc);
                    break;

                case 0:
                    System.out.println("Adeus!");
                    break;

                default:
                    System.out.println("Opção inválida!");
            }

        } while (opcao != 0);
    }
}