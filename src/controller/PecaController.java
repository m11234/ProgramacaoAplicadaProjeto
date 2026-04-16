package controller;

import model.Peca;
import model.PecaUsada;
import model.Utilizador;

import java.sql.SQLException;
import java.util.Scanner;
import model.dao.*;

public class PecaController {
    private AdminDao adminDao = new AdminDao();

    public void inserirPecaController(Scanner sc, Utilizador userLogado) {
        if (userLogado == null) {
            System.out.println("Fazer login!!!");
            return;
        }

        try {
            if (!adminDao.VerSeGestor(userLogado.getId()))  {
                System.out.println("So gestores podem fazer isto!!!!");
                return;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        sc.nextLine();
        System.out.println("Digite o nome/designacao do peca: ");
        String designacao = sc.nextLine();

        System.out.println("Fabricante do peca: ");
        String fabricante = sc.nextLine();
        sc.nextLine();

        System.out.println("Stock da peca");
        int stock = sc.nextInt();

        System.out.println("Preco da peca");
        float preco =  sc.nextFloat();

        Peca p  = new Peca(designacao, fabricante, stock, preco);
        boolean sucesso = PecaDAO.InserirPeca(p);

        if (sucesso) {
            System.out.println("Peca Inserido com sucesso!!!");
        }
        else {
            System.out.println("Erro!!!");
        }
    }

    public void inserirPecaUsada(Scanner sc, Utilizador userLogado) {
        if (userLogado == null) {
            System.out.println("Fazer login!!!");
            return;
        }

        if (!FuncionarioDAO.verSeFuncionario(userLogado.getId())) {
            System.out.println("So funcionarios podem fazer isto!!!!");
            return;
        }

        System.out.print("Inserir ID da reparacao associada a essa peca: ");
        int idReparacao = sc.nextInt();
        sc.nextLine();

        System.out.print("Inserir ID da peca a utilizar: ");
        int idPeca = sc.nextInt();
        sc.nextLine();

        PecaUsada pu = new PecaUsada(idPeca, idReparacao);

        try {
            boolean sucesso = PecaDAO.PecasUsadas(pu, userLogado.getId());

            if (sucesso) {
                System.out.println("Peca inserida na reparacao com sucesso!");
            }
        } catch (SecurityException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println("Erro ao registar a peca usada.");
        }
    }
}
