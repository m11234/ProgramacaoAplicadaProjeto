package controller;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Scanner;

import model.Cliente;
import model.Funcionario;
import model.Utilizador;
import model.dao.ClienteDAO;
import model.dao.ClienteDAO;
import model.db.DBConnection;

import java.util.Scanner;

public class ClienteController {
    private static ClienteDAO dao = new ClienteDAO();

    public static void criarCliente(Scanner sc, Utilizador logado) {
        System.out.println("\nRegistar Cliente");

        System.out.println("NIF");
        int nif = sc.nextInt();
        sc.nextLine();
/*
        System.out.println("Telemovel:");
        int telemovel = sc.nextInt();
        sc.nextLine();
*/
//----------------------------------------------------------
        int telemovel = 0;
        boolean contactoValido = false;

        while (!contactoValido) {
            System.out.println("Telemovel:");
            String entrada = sc.nextLine();

            if (entrada.matches("[923][0-9]{8}")) {
                telemovel = Integer.parseInt(entrada); //serve para retornar um número inteiro
                contactoValido = true;
            } else {
                System.out.println("Erro: O número de telemovel deve ter 9 dígitos e começar por 9, 2 ou 3.");
            }
        }
//----------------------------------------------------------

        System.out.println("Morada:");
        String morada = sc.nextLine();

        System.out.println("Sector:");
        String sector = sc.nextLine();

        System.out.println("Escalão:");
        String escalao = sc.nextLine();

        int idUtilizador = logado.getId();

        Cliente c = new Cliente(nif,telemovel,morada,sector,escalao, idUtilizador);
        boolean sucesso = dao.RegistarCliente(c);
        if (sucesso) {
            System.out.println("Funcionario registado");
        } else {
            System.out.println("Err ");
        }
    }

}
