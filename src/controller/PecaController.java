package controller;

import model.Peca;
import model.PecaUsada;
import model.Utilizador;

import java.sql.SQLException;
import java.util.Scanner;
import model.dao.*;

public class PecaController {
    private final AdminDao adminDao = new AdminDao();

    /**
     * Método para inserir uma nova peça no sistema
     * <p>
     *      O método valida primeiro se o utilizador atual tem sessão iniciada e se tem permissões (é um gestor).
     *      A seguir, pede ao utilizador as informações necessárias da peça (designação, fabricante, stock e preço).
     *      Após recolher os dados, cria o objeto representativo da peça e efetua a sua inserção na base de dados,
     *      informando sobre o sucesso ou falha da operação.
     * </p>
     * @param sc O objeto {@link Scanner} é responsável por capturar a informação introduzida na consola e passar
     * para a criação do objeto {@link Peca} e posteriormente para o método ({@code PecaDAO.InserirPeca})
     * @param userLogado O objeto {@link Utilizador} representa a conta com sessão iniciada no momento utilizado
     * para verificar permissões de acesso
     * @throws RuntimeException Se existir algum erro na comunicação com a base de dados durante a verificação de
     * privilégios ({@code adminDao.VerSeGestor}), encapsulando a respetiva {@link SQLException}.
     */
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

    /**
     * Método para registar a utilização de uma peça numa reparação
     * <p>
     *      O método valida primeiro se o utilizador atual tem sessão iniciada e se tem permissões (é um funcionário).
     *      De seguida, pede ao utilizador o ID da reparação e o ID da peça a ser utilizada. Após recolher os dados,
     *      cria o objeto representativo da peça usada e efetua o seu registo na base de dados, associando ao identificador (ID)
     *      do funcionário responsável. O processo lida ainda internamente com possíveis exceções de segurança ou erros
     *      gerais na submissão, informando o utilizador sobre o sucesso ou falha da operação.
     * </p>
     * @param sc O objeto {@link Scanner} é responsável por capturar a informação introduzida na consola e passar
     * para a criação do objeto {@link PecaUsada} e posteriormente para o método ({@code PecaDAO.PecasUsadas})
     * @param userLogado O objeto {@link Utilizador} representa a conta com sessão iniciada no momento utilizado
     * para verificar permissões de acesso através de ({@code FuncionarioDAO.verSeFuncionario}) e obter o
     * identificador (ID) do funcionário para o registo na base de dados
     */
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

    /**
     * Método para listar as peças com stock inferior a 10 unidades
     * <p>
     *      O método valida primeiro se o utilizador atual tem sessão iniciada e se tem permissões (é um gestor).
     *      Após a validação de segurança, invoca a base de dados para procurar e apresentar na consola a lista
     *      de todas as peças cujo nível de stock atual se encontre abaixo das 10 unidades.
     * </p>
     * @param userLogado O objeto {@link Utilizador} representa a conta com sessão iniciada no momento utilizado
     * para verificar permissões de acesso através do método ({@code adminDao.VerSeGestor})
     * @throws RuntimeException Se existir algum erro na comunicação com a base de dados durante a verificação de
     * privilégios ({@code adminDao.VerSeGestor}), encapsulando a respetiva {@link SQLException}.
     */
    public void pecaInferior (Utilizador userLogado) {
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

        PecaDAO.pecaInferior();
    }
}
