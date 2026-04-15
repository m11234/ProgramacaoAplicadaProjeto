package controller;

import model.Admin;
import model.Utilizador;
import model.dao.AdminDao;

import java.sql.SQLException;
import java.util.Scanner;
import java.util.regex.Pattern;

import model.dao.UtilizadoresDAO;

public class AdminController {
    private static AdminDao adminDao = new AdminDao();
    private UtilizadorController controller = new UtilizadorController();
    private UtilizadoresDAO dao = new UtilizadoresDAO();
    //email
    private static final String EMAIL_REGEX = "^[^@\\s]+@[^@\\s]+\\.[^@\\s]{2,}$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

    public void verAdmins(Scanner sc) {
        try {
            System.out.println("A verificar se existem gestores....");

            if (!adminDao.verificarGestores()) {
                System.out.println("Primeira execucao do programa");
                System.out.println("A criar gestor (Siga os passos de registo)...");

                controller.registarG(sc);
                sc.nextLine();

                System.out.println("\n--- Criar Gestor ---");
                System.out.println("Por favor, confirme as credenciais que acabou de criar:");
                System.out.print("Username: ");
                String username = sc.nextLine();
                System.out.print("Password: ");
                String password = sc.nextLine();

                Utilizador uPrimeiro = new Utilizador(username, password);
                Utilizador userCriado = dao.Login(uPrimeiro);
                dao.mudarEstadoGestor(userCriado);

                if (userCriado != null) {
                    Admin novoGestor = new Admin();
                    novoGestor.setId(userCriado.getId());

                    adminDao.criarGestor(novoGestor);
                    System.out.println("Conta de Gestor criada com sucesso para o ID: " + userCriado.getId());
                } else {
                    System.out.println("Erro: Não foi possível validar a conta acabada de criar.");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void verContasPorAtivar(Utilizador userLogado) throws SQLException {

        if (userLogado == null) {
            System.out.println("Fazer login!!!");
            return;
        }
        if (!adminDao.VerSeGestor(userLogado.getId())) {
            System.out.println("So gestores podem fazer isto!!!!");
            return;
        }

        dao.verContasPorAtivar();
    }

    public void ativarConta(Utilizador userLogado, Scanner sc) throws SQLException {
        if (userLogado == null) {
            System.out.println("Fazer login!!!");
            return;
        }
        if (!adminDao.VerSeGestor(userLogado.getId())) {
            System.out.println("So gestores podem fazer isto!!!!");
            return;
        }
        System.out.println("Inserir id da conta a ativar");
        int id = sc.nextInt();
        if (!dao.verSeContaExiste(id)) {
            System.out.println("Essa conta nao existe!!");
            return;
        }
        dao.mudarEstado(id);
        System.out.println("Conta atualizada com sucesso para o ID: " + id);
    }

    public void CriarOutroGestor(Utilizador userLogado, Scanner sc) throws SQLException {
        if (userLogado == null) {
            System.out.println("Fazer login!!!");
            return;
        }
        if (!adminDao.VerSeGestor(userLogado.getId())) {
            System.out.println("So gestores podem fazer isto!!!!");
            return;
        }
        System.out.println("A proceder a criacao de um gestor");
        controller.registarG(sc);
        sc.nextLine();

        System.out.println("\n--- Criar Gestor ---");
        System.out.println("Por favor, confirme as credenciais que acabou de criar:");
        System.out.print("Username: ");
        String username = sc.nextLine();
        System.out.print("Password: ");
        String password = sc.nextLine();

        Utilizador uPrimeiro = new Utilizador(username, password);
        Utilizador userCriado = dao.Login(uPrimeiro);
        dao.mudarEstadoGestor(userCriado);

        if (userCriado != null) {
            Admin novoGestor = new Admin();
            novoGestor.setId(userCriado.getId());

            adminDao.criarGestor(novoGestor);
            System.out.println("Conta de Gestor criada com sucesso para o ID: " + userCriado.getId());
        } else {
            System.out.println("Erro: Não foi possível validar a conta acabada de criar.");
        }
    }

    public Utilizador ConsultarDadosGestor(Utilizador userLogado, Scanner sc) throws SQLException {
        if (userLogado == null) {
            System.out.println("Fazer login!!!");
            return userLogado;
        }
        if (!adminDao.VerSeGestor(userLogado.getId())) {
            System.out.println("So gestores podem fazer isto!!!!");
            return userLogado;
        }
        System.out.println("Id do utilizador a consultar dados");
        int id = sc.nextInt();
        Utilizador Ver = dao.ConsultarDadosGestor(id);
        if (Ver != null) {
            System.out.println("\n Dados Utilizador");
            System.out.println("Nome:" + Ver.getNome());
            System.out.println("Email:" + Ver.getEmail());
            System.out.println("username:" + Ver.getUsername());
        } else {
            System.out.println("Nao foi posssivel encontrar os dados");
        }

        return Ver;
    }

    public void atualizarDadosGestor(Scanner sc, Utilizador userLogado) throws SQLException {

        if (userLogado == null || !adminDao.VerSeGestor(userLogado.getId())) {
            System.out.println("\nApenas Gestores podem fazer isto.");
            return;
        }

        System.out.println("\n--- Atualizar Dados de Outra Conta ---");
        System.out.print("Introduza o ID do utilizador que deseja alterar: ");
        int idAlvo = sc.nextInt();
        sc.nextLine();

        if (!dao.verSeContaExiste(idAlvo)) {
            System.out.println("\nErro: Não existe nenhum utilizador com o ID " + idAlvo);
            return;
        }

        Utilizador utilizadorAlvo = dao.ConsultarDadosGestor(idAlvo);
        System.out.println("\n[ A alterar dados de: " + utilizadorAlvo.getUsername() + " ]");
        System.out.println("1 - Atualizar password (Ainda nao funcional)");
        System.out.println("2 - Atualizar email(Funcional)");
        System.out.print("Escolha: ");
        int atualizar = sc.nextInt();
        sc.nextLine();

        switch (atualizar) {
            case 1:
                System.out.println("Funcionalidade ainda não implementada.");
                break;
            case 2:
                boolean emailValido = false;
                String novoEmail = "";

                while (!emailValido) {
                    System.out.print("Inserir novo email: ");
                    novoEmail = sc.nextLine();

                    if (EMAIL_PATTERN.matcher(novoEmail).matches()) {
                        emailValido = true;
                    } else {
                        System.out.println("Erro: formato de email inválido.");
                    }
                }

                Utilizador dadosNovos = new Utilizador();
                dadosNovos.setEmail(novoEmail);

                boolean atualizadoComSucesso = dao.atualizarEmailGestor(idAlvo, dadosNovos);

                if (atualizadoComSucesso) {
                    System.out.println("\n[SUCESSO] Dados atualizados com sucesso!");
                    Utilizador atualizado = dao.ConsultarDadosGestor(idAlvo);
                    System.out.println("Nome: " + atualizado.getNome());
                    System.out.println("Username: " + atualizado.getUsername());
                    System.out.println("Novo Email: " + atualizado.getEmail());
                } else {
                    System.out.println("\n[ERRO] Ocorreu um problema ao atualizar o email na base de dados.");
                }
                break;

            default:
                System.out.println("Opção inválida!");
                break;
        }
    }
}