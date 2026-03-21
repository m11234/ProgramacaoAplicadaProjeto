package controller;


import java.sql.SQLException;
import java.util.Scanner;
import java.util.regex.Pattern; //importação para a validação do email

import model.Utilizador;
import model.dao.UtilizadoresDAO;
public class UtilizadorController {

    private UtilizadoresDAO dao = new UtilizadoresDAO();


    //email
    private static final String EMAIL_REGEX = "^[^@\\s]+@[^@\\s]+\\.[^@\\s]{2,}$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);



    public void registar(Scanner sc) {

        System.out.println("\n--- Registo de Utilizador ---");

        System.out.print("Nome: ");
        String nome = sc.nextLine();

        System.out.print("Username: ");
        String username = sc.nextLine();

        System.out.print("Password: ");
        String password = sc.nextLine();

        String email = "";
        boolean emailValido = false;

        //BASEADO NESTA IMPLENMENTAÇÃO: https://www.abstractapi.com/guides/api-functions/email-validation-in-java
        while (!emailValido) {
            System.out.print("Email: ");
            email = sc.nextLine();

            if (EMAIL_PATTERN.matcher(email).matches()) {
                emailValido = true;
            } else {
                System.out.println("Erro: O email deve apresentar um formato válido e obrigatório ([designação] @ [entidade] . [domínio]).");
            }
        }

        String estado = "ativo";

        Utilizador u = new Utilizador(nome, username, password, email);

        boolean sucesso = dao.RegistarUtilizador(u);

        if (sucesso) {
            System.out.println("Utilizador registado!");
        } else {
            System.out.println("Erro no registo.");
        }
    }

    public void Login(Scanner sc) throws SQLException {
        boolean sucesso = false;

        while (!sucesso) {
        System.out.println("\n--- Login  ---");

        System.out.println("\n--- Inserir username  ---");
        String username = sc.nextLine();

        System.out.println("\n--- Inserir password  ---");
        String password = sc.nextLine();

        Utilizador u = new Utilizador(username,password);

        sucesso = dao.Login(u);

        if (sucesso) {
            System.out.println("Login com sucesso");
        } else {
            System.out.println("Erro");
        }

    }
} }