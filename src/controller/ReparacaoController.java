package controller;

import model.Reparacao;
import model.Utilizador;
import model.Utilizador;
import model.dao.*;
import model.Reparacao;

import java.sql.SQLException;
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
    }
