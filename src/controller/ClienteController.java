package controller;

import java.sql.SQLException;
import java.util.Scanner;

import model.Cliente;
import model.Equipamento;
import model.Reparacao;
import model.Utilizador;
import model.dao.ClienteDAO;
import model.dao.ReparacaoDAO;
import model.dao.UtilizadoresDAO;



public class ClienteController {
    private static final ClienteDAO dao = new ClienteDAO();
    private static final UtilizadoresDAO utilizadoresDAO = new UtilizadoresDAO();

    public static void criarCliente(Scanner sc, Utilizador logado) {
        System.out.println("\nRegistar Cliente");

        System.out.println("NIF");
        int nif = sc.nextInt();
        sc.nextLine();
/*
        System.out.println("Telemovel:");
        int telemovel = sc.nextInt();
        sc.nextLine();
*/
//----------------------------------------------------------
        int telemovel = 0;
        boolean contactoValido = false;

        while (!contactoValido) {
            System.out.println("Telemovel:");
            String entrada = sc.nextLine();

            if (entrada.matches("[923][0-9]{8}")) {
                telemovel = Integer.parseInt(entrada); //serve para retornar um número inteiro
                contactoValido = true;
            } else {
                System.out.println("Erro: O número de telemovel deve ter 9 dígitos e começar por 9, 2 ou 3.");
            }
        }
//----------------------------------------------------------

        System.out.println("Morada:");
        String morada = sc.nextLine();

        System.out.println("Sector:");
        String sector = sc.nextLine();

        System.out.println("Escalão:");
        String escalao = sc.nextLine();

        int idUtilizador = logado.getId();

        Cliente c = new Cliente(nif,telemovel,morada,sector,escalao, idUtilizador);
        boolean sucesso = dao.RegistarCliente(c);
        if (sucesso) {
            System.out.println("Funcionario registado");
        } else {
            System.out.println("Erro");
        }
    }

    public static void ApagarContaPedido(Utilizador userLogado, Scanner sc) throws SQLException {
        if (userLogado == null) {
            System.out.println("Fazer login!!");
        }
        assert userLogado != null;
        if (!ClienteDAO.VerSeCliente(userLogado.getId())) {
            System.out.println("So clientes podem fazer isto!!!!");
            return;
        }
        System.out.println("A iniciar pedido para apagar conta para confirmar entrar id para desistir entrar 0");
        int id = sc.nextInt();
        if (id == 0) {
            System.out.println("A abortar pedido para apagar conta");
        }
        boolean sucesso = utilizadoresDAO.ApagarContaUtilizador(userLogado, id);
        if (sucesso) {
            System.out.println("Pedido para apagar conta iniciado com sucesso");
        }
        else
        {
            System.out.println("Erro");
        }
    }

    public static void pesquisarPedidosReparacao(Utilizador userLogado, Scanner sc) throws SQLException {
        if (userLogado == null) {
            System.out.println("Fazer login!!");
            return;
        }
        if (!ClienteDAO.VerSeCliente(userLogado.getId())) {
            System.out.println("So clientes podem fazer isto!!!!");
            return;
        }
        System.out.println("Insira o numero do pedido de reparação: ");
        sc.nextLine();
        int idR =  sc.nextInt();
        Reparacao Ver = ReparacaoDAO.PesquisarPedidosReparacao(idR);
        if (Ver != null) {
            System.out.println("\n Dados da Reparação");
            System.out.println("Id: " + Ver.getIdR());
            System.out.println("Data inicio: " + Ver.getDataInicio());
            System.out.println("Data fim: " + Ver.getDataFim());
            System.out.println("Observação: " + Ver.getObservacao());
        } else {
            System.out.println("Nao foi posssivel encontrar os dados");
        }
    }


}






















