package controller;

import model.Peca;
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
}
