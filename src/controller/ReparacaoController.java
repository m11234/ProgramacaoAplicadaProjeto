package controller;

import model.Reparacao;
import model.Utilizador;
import model.dao.*;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class ReparacaoController {
    private UtilizadoresDAO dao = new UtilizadoresDAO();
    private EquipamentoController equipamentoController = new EquipamentoController();
    private ReparacaoDAO reparacaoDAO = new ReparacaoDAO();
    private EquipamentoDAO equipamentoDAO = new EquipamentoDAO();
    private AdminDao adminDao = new AdminDao();

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

    public void verReparacoesPorAprovarF(Utilizador userLogado) throws SQLException {

        if (userLogado == null) {
            System.out.println("Fazer login!!!");
            return;
        }
        if (!FuncionarioDAO.verSeFuncionario(userLogado.getId())) {
            System.out.println("So gestores podem fazer isto!!!!");
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

    public void aceitarReparacaoF(Scanner sc, Utilizador userLogado) throws SQLException {

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
            System.out.println("Não tem reparações pendentes para aprovar neste momento.");
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

    }
