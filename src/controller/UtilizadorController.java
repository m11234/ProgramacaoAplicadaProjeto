package controller;


import java.sql.SQLException;
import java.util.Scanner;
import java.util.regex.Pattern; //importação para a validação do email

import model.Utilizador;
import model.dao.UtilizadoresDAO;

public class UtilizadorController {

    private final UtilizadoresDAO dao = new UtilizadoresDAO();



    //email
    private static final String EMAIL_REGEX = "^[^@\\s]+@[^@\\s]+\\.[^@\\s]{2,}$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);


    public void registar(Scanner sc) throws SQLException {

        System.out.println("\n--- Registo de Utilizador ---");

        System.out.print("Nome: ");
        String nome = sc.nextLine();

        System.out.print("Username: ");
        String username = sc.nextLine();

        System.out.print("Password: ");
        String password = sc.nextLine();

        String email = "";
        boolean emailValido = false;

        /*
          Logica de validacao de enderecos de email em Java.
          Solucao adaptada de: Nicolas Rio
          Fonte: https://www.abstractapi.com/guides/api-functions/email-validation-in-java
          Acedido em: Dezasseis de Abril de dois mil e vinte e seis.
         */
        while (!emailValido) {
            System.out.print("Email: ");
            email = sc.nextLine();

            if (EMAIL_PATTERN.matcher(email).matches()) {
                emailValido = true;
            } else {
                System.out.println("Erro: O email deve apresentar um formato válido e obrigatório ([designação] @ [entidade] . [domínio]).");
            }
        }

        Utilizador u = new Utilizador(nome, username, password, email);

        boolean sucesso = dao.RegistarUtilizador(u);

        if (sucesso) {
            System.out.println("Dados inseridos com sucesso");
            System.out.println("\nPASSO 2 - Registo ");
            System.out.println("\n 2 Associar utilizador como cliente");
            System.out.println("\n 1 Associar utilizador como funcionario");
            int tipoDeConta;
            Utilizador userLogado;
            tipoDeConta = sc.nextInt();
            switch (tipoDeConta) {
                case 1:
                    Utilizador l = new Utilizador(username, password);
                    userLogado = dao.Login(l);
                    FuncionarioController.criarFuncionario(sc,userLogado);
                    break;
                case 2:
                    Utilizador x = new Utilizador(username, password);
                    userLogado = dao.Login(x);
                    ClienteController.criarCliente(sc,userLogado);
                    break;
            }
        } else {
            System.out.println("Erro no registo.");
        }
    }
    public void registarG(Scanner sc) throws SQLException {

        System.out.println("\n--- Registo de Utilizador ---");

        System.out.print("Nome: ");
        sc.nextLine();
        String nome = sc.nextLine();

        System.out.print("Username: ");
        String username = sc.nextLine();

        System.out.print("Password: ");
        String password = sc.nextLine();

        String email = "";
        boolean emailValido = false;

        //BASEADO NESTA IMPLEMENTAÇÃO: https://www.abstractapi.com/guides/api-functions/email-validation-in-java
        while (!emailValido) {
            System.out.print("Email: ");
            email = sc.nextLine();

            if (EMAIL_PATTERN.matcher(email).matches()) {
                emailValido = true;
            } else {
                System.out.println("Erro: O email deve apresentar um formato válido e obrigatório ([designação] @ [entidade] . [domínio]).");
            }
        }

        Utilizador u = new Utilizador(nome, username, password, email);

        boolean sucesso = dao.RegistarUtilizador(u);

        if (sucesso) {
            System.out.println("Dados inseridos com sucesso");
        } else {
            System.out.println("Erro no registo.");
        }
    }


    public Utilizador Login(Scanner sc) throws SQLException {
        Utilizador logado = null;

        while (logado == null) {
            System.out.println("\n--- Login ---");
            System.out.print("Username: ");
            String username = sc.nextLine();
            System.out.print("Password: ");
            String password = sc.nextLine();

            Utilizador u = new Utilizador(username, password);
            logado = dao.Login(u);

            if (logado != null) {
                System.out.println("\nBem-vindo " + logado.getUsername() + ".\n");
                if (logado.getEstado() != 1) {
                    System.out.println("Erro: Conta ainda nao ativada pelo gestor contacte um gestor");
                } }
            else {
                System.out.println("Erro dados invalidos ou conta foi apagada contacte um gestor");

            }
        }

        return logado; // agora tens o currentUser
    }

    public Utilizador Logout(Utilizador logado) {
        if (logado != null){
            System.out.println("\nAdeus " + logado.getUsername() + ".\n");
        } else {
            System.out.println("\nErro: Não existe nenhuma sessão para encerrar.");
        }
        return null;
    }


    public Utilizador ConsultarDados(Utilizador logado) {
        Utilizador u = dao.ConsultarDados(logado);

        assert u != null;
        if (u.getEstado() == 1){
            System.out.println("\n Dados Utilizador");
            System.out.println("Nome:" + u.getNome());
            System.out.println("Email:" + u.getEmail());
            System.out.println("username:" + u.getUsername());
        } else {
            System.out.println("Nao foi posssivel encontrar os dados");
        }


        return u;
    }


    public void atualizarDados(Scanner sc, Utilizador logado) {
        int atualizar;
        boolean atualizadoComSucesso;
        System.out.println("\n--- Atualizar Dados ---");
        System.out.println("1 - Atualizar password");
        System.out.println("2 - Atualizar email");
        System.out.print("Escolha: ");
        atualizar = sc.nextInt();
        sc.nextLine();

        String novaPassword = logado.getPassword();
        String novoEmail = logado.getEmail();

        switch (atualizar) {
            case 1:
                System.out.print("Inserir nova password: ");
                novaPassword = sc.nextLine();
                break;

            case 2:
                boolean emailValido = false;
                while (!emailValido) {
                    System.out.print("Inserir novo email: ");
                    novoEmail = sc.nextLine();

                    if (EMAIL_PATTERN.matcher(novoEmail).matches()) {
                        emailValido = true;
                    } else {
                        System.out.println("Erro: email inválido.");
                    }
                }
                break;

            default:
                System.out.println("Opção inválida!");
                return;
        }

        // Criar objeto com os novos dados
        Utilizador dadosNovos = new Utilizador(
                logado.getNome(),
                logado.getUsername(),
                novaPassword,
                novoEmail
        );

        atualizadoComSucesso = dao.AtualizarDados(logado, dadosNovos);

        if (atualizadoComSucesso) {
            System.out.println("Dados atualizados com sucesso!");
            // Consultar e mostrar os dados atualizados
            Utilizador atualizado = dao.ConsultarDados(logado);
            if (atualizado != null) {
                System.out.println("Nome: " + atualizado.getNome());
                System.out.println("Username: " + atualizado.getUsername());
                System.out.println("Email: " + atualizado.getEmail());
            }
        } else {
            System.out.println("Erro ao atualizar dados.");
        }
    }
    }



