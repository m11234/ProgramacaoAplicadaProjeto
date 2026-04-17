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

    /**
     * Método para registar um novo cliente no sistema
     * <p>
     *     O método recolhe os dados necessários para o registo (NIF, telemóvel, morada, sector e escalão).
     *     Durante o processo, efetua a validação do número de telemóvel introduzido (garantindo que possui 9 dígitos
     *     e começa por 9, 2 ou 3). Após a validação e recolha da informação, associa os dados ao identificador (ID)
     *     do utilizador com sessão iniciada e regista o cliente na base de dados.
     * </p>
     * @param sc O objeto {@link Scanner} é responsável por capturar a informação introduzida na consola e passar
     * para a criação do objeto {@link Cliente} e posteriormente para o método ({@code dao.RegistarCliente})
     * @param logado O objeto {@link Utilizador} representa a conta com sessão iniciada no momento utilizado
     * para obter o identificador do utilizador e associar ao novo registo de cliente
     */
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
            System.out.println("Cliente registado");
        } else {
            System.out.println("Erro");
        }
    }

    /**
     * Método para iniciar o pedido para apagar a conta de utilizador
     * <p>
     *      O método valida primeiro se o utilizador atual tem sessão iniciada e se tem permissões (é um cliente),
     *      de seguida pede ao utilizador o seu id para confirmar o pedido de eliminação da conta, oferecendo a opção
     *      de introduzir '0' para abortar a operação. Após a introdução do id, regista o pedido na base de dados
     *      e informa o utilizador sobre o sucesso ou falha da operação.
     * </p>
     * @param userLogado O objeto {@link Utilizador} representa a conta com sessão iniciada no momento utilizado
     * para verificar permissões e submeter o pedido
     * @param sc O objeto {@link Scanner} é responsável por capturar a informação introduzida na consola e passar
     * para o método ({@code utilizadoresDAO.ApagarContaUtilizador})
     * @throws SQLException Se existir algum erro na comunicação com a base de dados seja durante o query de pesquisa ou submissão
     * ({@code ClienteDAO.VerSeCliente}) e ({@code utilizadoresDAO.ApagarContaUtilizador}).
     */
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

    /**
     * Metodo para pesquisar e consultar um pedido de reparação através do seu número
     * <p>
     *      O metodo valida primeiro se o utilizador atual tem sessão iniciada e se tem permissões (é um cliente),
     *      a seguir pede ao utilizador o número (ID) do pedido de reparação que pretende pesquisar, procura os seus dados e imprime
     *      as suas informações detalhadas (ID, data de início, data de fim e observação) na consola caso o pedido exista.
     * </p>
     * @param userLogado O objeto {@link Utilizador} representa a conta com sessao iniciada no momento utilizado
     * para verificar permissões
     * @param sc O objeto {@link Scanner} é responsavel por capturar a informação introduzida na consola e passar
     * para o método ({@code ReparacaoDAO.PesquisarPedidosReparacao})
     * @throws SQLException Se existir algum erro na comunicação com a base de dados seja durante o query de pesquisa
     * ({@code ClienteDAO.VerSeCliente}) e ({@code ReparacaoDAO.PesquisarPedidosReparacao}).
     */
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






















