package controller;

import model.Teste;
import model.Utilizador;
import model.dao.*;

import java.util.Scanner;


public class TestesController {
    /**
     * Metodo para um funcionário submeter o registo de um teste efetuado numa reparação
     * <p>
     *     O metodo valida primeiro se o utilizador atual tem sessão iniciada e se tem permissões (é um funcionário).
     *     De seguida, solicita os dados relativos ao teste, nomeadamente o ID da reparação associada, a designação,
     *     a descrição detalhada e o preço do serviço. Após recolher estas informações, cria um objeto {@link Teste}
     *     e submete-o para a base de dados através do identificador (ID) do funcionário responsável. O metodo trata
     *     exceções de segurança e erros genéricos, informando o utilizador sobre o resultado da operação.
     * </p>
     * @param sc O objeto {@link Scanner} é responsavel por capturar a informação introduzida na consola e passar
     * para a criação do objeto {@link Teste} e posteriormente para o metodo ({@code TestesDAO.submeterTeste})
     * @param userLogado O objeto {@link Utilizador} representa a conta com sessao iniciada no momento que é utilizado
     * para verificar permissões de acesso através de ({@code FuncionarioDAO.verSeFuncionario}) e para associar
     * o ID do funcionário ao registo do teste
     */
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
