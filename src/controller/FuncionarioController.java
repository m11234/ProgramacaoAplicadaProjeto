package controller;
import java.sql.SQLException;
import java.util.Date;
import java.util.Scanner;

import model.Funcionario;
import model.Utilizador;
import model.dao.FuncionarioDAO;
import model.dao.UtilizadoresDAO;

public class FuncionarioController {
    private static FuncionarioDAO dao = new FuncionarioDAO();

    public static void criarFuncionario(Scanner sc, Utilizador logado) {
        System.out.println("\n Registar Funcionario");

        System.out.println("NIF");
        int nif = sc.nextInt();
        sc.nextLine();


/* Antes
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
        //---------------------------------------------------


        System.out.println("Morada:");
        String morada = sc.nextLine();

        System.out.println("Nivel E:");
        int nivelE = sc.nextInt();
        sc.nextLine();

        Date dataI = new Date();
        int idUtilizador = logado.getId();

        Funcionario f = new Funcionario(nif,telemovel,morada,nivelE,dataI,idUtilizador);
        boolean sucesso = dao.RegistarFuncionario(f);
        if (sucesso) {
            System.out.println("Funcionario registado");
        } else {
            System.out.println("Err ");
        }
    }
}
