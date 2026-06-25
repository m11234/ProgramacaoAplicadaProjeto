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
    public Cliente criarCliente(String nif, String telemovel, String morada, String sector, String escalao, Utilizador logado) {
        System.out.println("\nRegistar Cliente");

        boolean NumeroValido = false;

        while (!NumeroValido) {
            System.out.println("Telemovel:");

            String entrada = telemovel;

            if (entrada.matches("[923][0-9]{8}")) {
                telemovel = String.valueOf(Integer.parseInt(entrada)); //serve para retornar um número inteiro
                NumeroValido = true;
            } else {
                System.out.println("Erro: O número de telemovel deve ter 9 dígitos e começar por 9, 2 ou 3.");
                break;
            }
        }

        int idUtilizador = logado.getId();

        Cliente c = new Cliente(nif,telemovel,morada,sector,escalao, idUtilizador);
        boolean sucesso = dao.RegistarCliente(c);
        if (sucesso) {
            System.out.println("Cliente registado");
            return c;
        } else {
            System.out.println("Erro");
        }
        return null;
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
    public boolean ApagarContaPedido(Utilizador userLogado, int id) throws SQLException {
        if (userLogado == null) {
            System.out.println("Fazer login!!");
        }

        if (!ClienteDAO.VerSeCliente(userLogado.getId())) {
            System.out.println("So clientes podem fazer isto!!!!");
            return false;
        }

        boolean sucesso = utilizadoresDAO.ApagarContaUtilizador(userLogado, id);
        if (sucesso) {
            System.out.println("Pedido para apagar conta iniciado com sucesso");
            return true;
        }
        return false;
    }

}






















