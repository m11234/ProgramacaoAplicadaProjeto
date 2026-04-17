package model.dao;
import java.sql.*;
import model.Cliente;
import model.Equipamento;
import model.Reparacao;
import model.db.DBConnection;


public class ClienteDAO {

    /**
     * Metodo para persistir o registo de um novo cliente na base de dados
     * <p>
     *      O metodo recebe um objeto do tipo {@link Cliente} e executa uma instrução SQL {@code INSERT}
     *      para gravar os dados (NIF, telemóvel, morada, sector e escalão) na tabela "clientes",
     *      associando-os ao identificador único do utilizador correspondente. Utiliza o padrão
     *      {@code try-with-resources} para garantir que a conexao  à base de dados é fechada.
     * </p>
     * @param c O objeto {@link Cliente} com as informações a serem registadas
     * @return {@code true} se o registo for inserido com sucesso (pelo menos uma linha afetada),
     * {@code false} caso contrário
     * @throws RuntimeException Se ocorrer um erro durante a execução da instrução SQL,
     * encapsulando a respetiva {@link SQLException}.
     */
    public boolean RegistarCliente(Cliente c) {
        String sql = "Insert into clientes (NIF,Telemovel,Morada,SectorA,Escalao,id) values (?,?,?,?,?,?)";

        try (Connection conn = DBConnection.getconn();
        PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setInt(1, c.getNif());
            ps.setInt(2, c.getTelemovel());
            ps.setString(3, c.getMorada());
            ps.setString(4, c.getSector());
            ps.setString(5, c.getEscalao());
            ps.setInt(6, c.getIdUtilizador());

            int criarCliente = ps.executeUpdate();
            return criarCliente > 0;


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Metodo estático para verificar se um determinado utilizador possui um perfil de cliente registado
     * <p>
     *      O metodo realiza uma consulta na tabela "clientes" utilizando o identificador único (ID)
     *      do utilizador fornecido como argumento. Se a consulta retornar um registo, confirma-se que
     *      o utilizador está associado ao papel de cliente no sistema, devolvendo o valor lógico verdadeiro.
     *      Caso contrário, o utilizador não é reconhecido como cliente.
     * </p>
     * @param Id O identificador único do utilizador a ser verificado na base de dados
     * @return {@code true} se for encontrado um registo na tabela de clientes, {@code false} caso contrário
     * @throws SQLException Se ocorrer um erro durante a ligação à base de dados ou na execução da
     * consulta SQL ({@code DBConnection.getconn} e {@code ps.executeQuery}).
     */
    public static boolean VerSeCliente(int Id) throws SQLException {
        String sql = "SELECT * FROM clientes WHERE id=?";
        boolean ClienteSer = false;
        try (Connection conn = DBConnection.getconn();
             PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setInt(1, Id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                //.out.println("O utilizador é um cliente proseguir...");
                ClienteSer = true;
            }
        }
        return ClienteSer;
    }


}
