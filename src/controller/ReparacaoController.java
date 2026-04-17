package controller;

import model.Reparacao;
import model.Utilizador;
import model.dao.*;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class ReparacaoController {
    private final ReparacaoDAO reparacaoDAO = new ReparacaoDAO();
    private final EquipamentoDAO equipamentoDAO = new EquipamentoDAO();
    private final AdminDao adminDao = new AdminDao();

    /**
     * Método para criar e submeter um novo pedido de reparação
     * <p>
     *      O método valida primeiro se o utilizador atual tem sessão iniciada e se tem permissões (é um cliente).
     *      De seguida, pede ao cliente o ID do equipamento a reparar e verifica se o mesmo pertence-lhe efetivamente.
     *      Após esta validação, solicita uma breve descrição do problema, define o estado inicial da
     *      reparação e submete o pedido para a base de dados, informando o utilizador sobre o sucesso ou falha da operação.
     * </p>
     * @param sc O objeto {@link Scanner} é responsável por capturar a informação introduzida na consola e passar
     * para a criação do objeto {@link Reparacao} e posteriormente para o método ({@code reparacaoDAO.SubmeterREparacao})
     * @param userLogado O objeto {@link Utilizador} representa a conta com sessão iniciada no momento utilizado
     * para verificar permissões de acesso através de ({@code ClienteDAO.VerSeCliente}) e para validar a pertença do
     * equipamento através do método ({@code equipamentoDAO.verSeEquipPertence})
     * @throws SQLException Se existir algum erro na comunicação com a base de dados seja durante o query de pesquisa ou submissão
     * ({@code ClienteDAO.VerSeCliente}), ({@code equipamentoDAO.verSeEquipPertence}) e ({@code reparacaoDAO.SubmeterREparacao}).
     */
    public void criarReparacao(Scanner sc, Utilizador userLogado) throws SQLException {
        if (userLogado == null) {
            System.out.println("Fazer login!!!");
            return;
        }
        if (!ClienteDAO.VerSeCliente(userLogado.getId())) {
            System.out.println("So clientes podem fazer isto!!!!");
            return;
        }
        System.out.print("Inserir id do equipamento a reparar:");
        int idEquip = sc.nextInt();
        sc.nextLine();
        if (!equipamentoDAO.verSeEquipPertence(idEquip, userLogado.getId())) {
        System.out.println("O equipamento não pertence a este cliente");
        return;
        }

        System.out.print("\nBreve descricao do problema");
        String observacao  = sc.nextLine();

        int estado = 1;

        Reparacao r = new Reparacao(observacao,idEquip,estado);
        boolean sucesso = reparacaoDAO.SubmeterREparacao(r);
        if (sucesso) {
            System.out.println("Pedido submetido");
        }
        else {
            System.out.println("Erro");
    }

    }

    /**
     * Método para visualizar as reparações pendentes de aprovação
     * <p>
     * O método valida primeiro se o utilizador atual tem sessão iniciada e se tem permissões (é um gestor).
     * Após esta validação de segurança, invoca a base de dados para procurar e apresentar na consola a lista
     * de todas as reparações que se encontram atualmente a aguardar aprovação.
     * </p>
     * @param userLogado O objeto {@link Utilizador} representa a conta com sessão iniciada no momento utilizado
     * para verificar permissões de acesso através do método ({@code adminDao.VerSeGestor})
     * @throws SQLException Se existir algum erro na comunicação com a base de dados seja durante o query de pesquisa
     * ({@code adminDao.VerSeGestor}) e ({@code reparacaoDAO.verReparacoesPorAprovar}).
     */
    public void verReparacoesPorAprovar(Utilizador userLogado) throws SQLException {

        if (userLogado == null) {
            System.out.println("Fazer login!!!");
            return;
        }
        if (!adminDao.VerSeGestor(userLogado.getId())) {
            System.out.println("So gestores podem fazer isto!!!!");
            return;
        }

        reparacaoDAO.verReparacoesPorAprovar();
    }

    /**
     * Método para visualizar as reparações pendentes de aprovação associadas a um funcionário
     * <p>
     *     O método valida primeiro se o utilizador atual tem sessão iniciada e se tem permissões (é um funcionário).
     *     Após esta validação de segurança, invoca a base de dados para obter a lista de reparações pendentes
     *     atribuídas a esse funcionário específico. Se existirem reparações por aprovar, imprime na consola os
     *     respetivos detalhes (ID da reparação, ID do equipamento e observações); caso contrário, informa que
     *     não existem reparações pendentes no momento.
     * </p>
     * @param userLogado O objeto {@link Utilizador} representa a conta com sessão iniciada no momento utilizado
     * para verificar permissões de acesso através do método ({@code FuncionarioDAO.verSeFuncionario}) e para
     * obter a lista de reparações específicas desse funcionário através de ({@code reparacaoDAO.verReparacoesPorAprovarF})
     * @throws SQLException Se existir algum erro na comunicação com a base de dados seja durante o query de pesquisa
     * ({@code FuncionarioDAO.verSeFuncionario}) e ({@code reparacaoDAO.verReparacoesPorAprovarF}).
     */
    public void verReparacoesPorAprovarF(Utilizador userLogado) throws SQLException {

        if (userLogado == null) {
            System.out.println("Fazer login!!!");
            return;
        }
        if (!FuncionarioDAO.verSeFuncionario(userLogado.getId())) {
            System.out.println("So funcionários podem fazer isto!!!!");
            return;
        }

        List<Reparacao> listaPorAceitar = reparacaoDAO.verReparacoesPorAprovarF(userLogado);

        if (listaPorAceitar == null || listaPorAceitar.isEmpty()) {
            System.out.println("Não tem reparações pendentes para aprovar neste momento.");
            return;
        }

        for (Reparacao rep : listaPorAceitar) {
            System.out.println("ID Reparação: " + rep.getIdR() +
                    " | ID Equipamento: " + rep.getIdEquip() +
                    " | Observações: " + rep.getObservacao());
        }

    }

    /**
     * Metodo para aprovar uma reparação e associá-la a um funcionário
     * <p>
     *      O metodo valida primeiro se o utilizador atual tem sessão iniciada e se tem permissões (é um gestor).
     *      Após a validação, apresenta a lista de reparações pendentes de aprovação e solicita ao gestor o ID da
     *      reparação a aprovar, bem como o ID do funcionário ao qual a reparação será atribuída. Por fim, regista
     *      a aprovação e a associação do funcionário na base de dados, informando sobre o sucesso ou falha da operação.
     * </p>
     * @param sc O objeto {@link Scanner} é responsavel por capturar a informação introduzida na consola e passar
     * para a criação do objeto {@link Reparacao} e posteriormente para o metodo ({@code reparacaoDAO.AceitarReparacaoDAO})
     * @param userLogado O objeto {@link Utilizador} representa a conta com sessao iniciada no momento que é utilizado
     * para verificar permissões de acesso através do metodo ({@code adminDao.VerSeGestor})
     * @throws SQLException Se existir algum erro na comunicação com a base de dados seja durante o query de pesquisa ou atualização
     * ({@code adminDao.VerSeGestor}), ({@code reparacaoDAO.verReparacoesPorAprovar}) e ({@code reparacaoDAO.AceitarReparacaoDAO}).
     */
    public void aceitarReparacao(Scanner sc,Utilizador userLogado) throws SQLException {
        if (userLogado == null) {
            System.out.println("Fazer login!!!");
            return;
        }
        if (!adminDao.VerSeGestor(userLogado.getId())) {
            System.out.println("So gestores podem fazer isto!!!!");
            return;
        }

        reparacaoDAO.verReparacoesPorAprovar();
        System.out.print("Inserir id da reparacao a aprovar:");
        int idReparacao = sc.nextInt();
        sc.nextLine();
        System.out.print("Associar reparacao com funcionario:");
        // se der tempo imprimir lista de funcionarios
        int FuncionarioA = sc.nextInt();
        sc.nextLine();

        Reparacao r = new Reparacao(FuncionarioA, idReparacao);
        boolean sucesso = reparacaoDAO.AceitarReparacaoDAO(r);

        if  (sucesso) {
            System.out.println("Reparacao aprovado");

        }
        else {
            System.out.println("Erro a aprovar reparacao");
        }

    }

    /**
     * Metodo para um funcionário aceitar ou rejeitar uma reparação que lhe foi atribuída
     * <p>
     *     O metodo valida primeiro se o utilizador atual tem sessão iniciada e se tem permissões (é um funcionário).
     *     De seguida, apresenta a lista de reparações que lhe foram atribuídas e que se encontram pendentes de aprovação.
     *     Após a listagem, pede ao funcionário o ID da reparação que deseja processar e solicita a escolha do novo estado
     *     (3 para aceitar, 1 para rejeitar). Por fim, o estado da reparação é atualizado na base de dados e é apresentada
     *     uma mensagem de sucesso ou falha da operação.
     * </p>
     * @param sc O objeto {@link Scanner} é responsável por capturar a informação introduzida na consola e passar
     * para a criação do objeto {@link Reparacao} e posteriormente para o método ({@code reparacaoDAO.AceitarReparacaoFDAO})
     * @param userLogado O objeto {@link Utilizador} representa a conta com sessão iniciada no momento utilizado
     * para verificar permissões de acesso através do metodo ({@code FuncionarioDAO.verSeFuncionario}) e para obter
     * as reparações específicas a aprovar através de ({@code reparacaoDAO.verReparacoesPorAprovarF})
     */
    public void aceitarReparacaoF(Scanner sc, Utilizador userLogado) {

        if (userLogado == null) {
            System.out.println("Fazer login!!!");
            return;
        }
        if (!FuncionarioDAO.verSeFuncionario(userLogado.getId())) {
            System.out.println("So funcionarios podem fazer isto!!!!");
            return;
        }

        int idFuncionario = userLogado.getId();

        List<Reparacao> listaPorAceitar = reparacaoDAO.verReparacoesPorAprovarF(userLogado);

        if (listaPorAceitar == null || listaPorAceitar.isEmpty()) {
            System.out.println("Não tem reparações pendentes para aprovar neste momento.");
            return;
        }

        for (Reparacao rep : listaPorAceitar) {
            System.out.println("ID Reparação: " + rep.getIdR() +
                    " | ID Equipamento: " + rep.getIdEquip() +
                    " | Observações: " + rep.getObservacao());
        }

        System.out.println("Inserir reparacao a aprovar");
        int idReparacao = sc.nextInt();
        sc.nextLine();

        System.out.print("Para aceitar 3 para rejeitar 1");
        int Estado = sc.nextInt();
        sc.nextLine();

        Reparacao r = new Reparacao(Estado, idReparacao, idFuncionario);
        boolean sucesso = reparacaoDAO.AceitarReparacaoFDAO(r,idFuncionario);

        if (sucesso) {
            System.out.println("Reparacao aprovado");
        }
        else {
            System.out.println("Erro a aprovar reparacao");
        }
    }

    /**
     * Metodo para um funcionário finalizar uma reparação que lhe foi atribuída
     * <p>
     *      O método valida primeiro se o utilizador atual tem sessão iniciada e se tem permissões (é um funcionário).
     *      De seguida, apresenta a lista de reparações que lhe foram atribuídas e que se encontram pendentes de finalização.
     *      Após a listagem, pede ao funcionário o ID da reparação que deseja concluir. Por fim, o estado da reparação
     *      é atualizado na base de dados (passando para o estado 4 - finalizado) e é apresentada uma mensagem de sucesso
     *      ou falha da operação.
     * </p>
     * @param sc O objeto {@link Scanner} é responsável por capturar a informação introduzida na consola e passar
     * para a criação do objeto {@link Reparacao} e posteriormente para o método ({@code reparacaoDAO.FinalizarReparacaoFDAO})
     * @param userLogado O objeto {@link Utilizador} representa a conta com sessão iniciada no momento utilizado
     * para verificar permissões de acesso através do método ({@code FuncionarioDAO.verSeFuncionario}) e para obter
     * as reparações pendentes de finalização através de ({@code reparacaoDAO.verReparacoesPorFinalizarF})
     * @throws SQLException Se existir algum erro na comunicação com a base de dados seja durante o query de pesquisa ou atualização
     * ({@code FuncionarioDAO.verSeFuncionario}), ({@code reparacaoDAO.verReparacoesPorFinalizarF}) e ({@code reparacaoDAO.FinalizarReparacaoFDAO}).
     */
    public void FinalizarReparacaoF(Scanner sc, Utilizador userLogado) throws SQLException {

        if (userLogado == null) {
            System.out.println("Fazer login!!!");
            return;
        }
        if (!FuncionarioDAO.verSeFuncionario(userLogado.getId())) {
            System.out.println("So funcionarios podem fazer isto!!!!");
            return;
        }

        int idFuncionario = userLogado.getId();

        List<Reparacao> listaPorFinalizar = reparacaoDAO.verReparacoesPorFinalizarF(userLogado);

        if (listaPorFinalizar == null || listaPorFinalizar.isEmpty()) {
            System.out.println("Não tem reparações pendentes por finalizar neste momento.");
            return;
        }

        for (Reparacao rep : listaPorFinalizar) {
            System.out.println("ID Reparação: " + rep.getIdR() +
                    " | ID Equipamento: " + rep.getIdEquip() +
                    " | Observações: " + rep.getObservacao());
        }

        System.out.println("Inserir reparacao a aprovar");
        int idReparacao = sc.nextInt();
        sc.nextLine();


        Reparacao r = new Reparacao(4, idReparacao, idFuncionario);
        boolean sucesso = reparacaoDAO.FinalizarReparacaoFDAO(r,idFuncionario);

        if (sucesso) {
            System.out.println("Reparacao aprovado");
        }
        else {
            System.out.println("Erro a aprovar reparacao");
        }
    }

    /**
     * Metodo para listar as reparações que se encontram sem finalização há mais de dez dias
     * <p>
     *      O metodo valida primeiro se o utilizador atual tem sessão iniciada e se tem permissões (é um gestor).
     *      Após a validação de segurança, lista na consola todas as
     *      reparações que permanecem em aberto por um período superior a dez dias, servindo como um sistema
     *      de notificação ou alerta para atrasos
     * </p>
     * @param userLogado O objeto {@link Utilizador} representa a conta com sessao iniciada no momento que é utilizado
     * para verificar permissões de acesso através do metodo ({@code adminDao.VerSeGestor})
     * @throws SQLException Se existir algum erro na comunicação com a base de dados seja durante o query de pesquisa
     * ({@code adminDao.VerSeGestor}) e ({@code ReparacaoDAO.notificacaoDezDiasSemFinalizacao}).
     */
        public void notificacaoDezDiasSemFinalizacao(Utilizador userLogado) throws SQLException {
            if (userLogado == null) {
                System.out.println("Fazer login!!!");
                return;
            }
            if (!adminDao.VerSeGestor(userLogado.getId())) {
                System.out.println("So gestores podem fazer isto!!!!");
                return;
            }

            ReparacaoDAO.notificacaoDezDiasSemFinalizacao();
        }
}
