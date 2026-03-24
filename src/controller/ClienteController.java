package controller;
import java.sql.SQLException;
import java.util.Date;
import java.util.Scanner;

import model.Cliente;
import model.Funcionario;
import model.Utilizador;
import model.dao.ClienteDAO;
import model.dao.ClienteDAO;

import java.util.Scanner;

public class ClienteController {
    private static ClienteDAO dao = new ClienteDAO();

    public static void criarCliente(Scanner sc, Utilizador logado) {
        System.out.println("\nRegistar Cliente");

        System.out.println("NIF");
        int nif = sc.nextInt();
        sc.nextLine();

        System.out.println("Telemovel:");
        int telemovel = sc.nextInt();
        sc.nextLine();

        System.out.println("Morada:");
        String morada = sc.nextLine();

        System.out.println("Sector:");
        String sector = sc.nextLine();
        sc.nextLine();

        System.out.println("Escalão:");
        String escalao = sc.nextLine();
        sc.nextLine();

        Cliente c = new Cliente(nif,telemovel,morada,sector,escalao);
        boolean sucesso = dao.RegistarCliente(c);
        if (sucesso) {
            System.out.println("Funcionario registado");
        } else {
            System.out.println("Err ");
        }
    }






}
