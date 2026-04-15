package controller;

import model.Teste;
import model.Utilizador;
import model.dao.*;

import java.util.Scanner;


public class TestesController {
    private ReparacaoDAO reparacaoDAO = new ReparacaoDAO();
    private TestesDAO testesDAO = new TestesDAO();
    public void submeterTesteF(Scanner sc, Utilizador userLogado) {
        if (userLogado == null) {
            System.out.println("Fazer login!!!");
            return;
        }
        if (!FuncionarioDAO.verSeFuncionario(userLogado.getId())) {
            System.out.println("So funcionarios podem fazer isto!!!!");
            return;
        }

        System.out.print("Inserir ID da reparacao para teste: ");
        int idReparacao = sc.nextInt();
        sc.nextLine();

        System.out.print("Inserir designacao do teste: ");
        String designacao = sc.nextLine();

        System.out.print("Inserir descricao do teste: ");
        String descricao = sc.nextLine();

        System.out.print("Inserir preco do teste: ");
        float preco = sc.nextFloat();
        sc.nextLine();

        Teste t = new Teste(idReparacao, designacao, descricao, preco);

        try {
            boolean sucesso = TestesDAO.submeterTeste(t,userLogado.getId());
            if (sucesso) {
                System.out.println("Teste submetido com sucesso");
            }
        } catch (SecurityException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println("Erro ao submeter teste");
        }
    }
}
