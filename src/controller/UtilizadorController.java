package controller;

import java.util.Scanner;

import model.Utilizador;
import model.dao.UtilizadoresDAO;
public class UtilizadorController {

    private UtilizadoresDAO dao = new UtilizadoresDAO();

    public void registar(Scanner sc) {

        System.out.println("\n--- Registo de Utilizador ---");

        System.out.print("Nome: ");
        String nome = sc.nextLine();

        System.out.print("Username: ");
        String username = sc.nextLine();

        System.out.print("Password: ");
        String password = sc.nextLine();

        System.out.print("Email: ");
        String email = sc.nextLine();

        String estado = "ativo";

        Utilizador u = new Utilizador(nome, username, password, email);

        boolean sucesso = dao.RegistarUtilizador(u);

        if (sucesso) {
            System.out.println("Utilizador registado!");
        } else {
            System.out.println("Erro no registo.");
        }
    }
}