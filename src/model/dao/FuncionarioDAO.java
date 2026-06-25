package model.dao;
import java.sql.*;
import model.Utilizador;
import model.Funcionario;
import model.db.DBConnection;

public class FuncionarioDAO {
    /**
     * Metodo para gravar o registo de um novo funcionário na base de dados
     * <p>
     * O metodo recebe um objeto do tipo {@link Funcionario} e executa uma instrução SQL {@code INSERT}
     * para gravar os dados (NIF, telemóvel, morada, nível de escolaridade e
     * data de início de contrato) na tabela "funcionario". O registo é vinculado ao identificador único
     * do utilizador (ID). A data de início é convertida para {@code java.sql.Date} para garantir a
     * compatibilidade com a base de dados.
     * </p>
     * @param f O objeto {@link Funcionario} contendo as informações detalhadas a serem registadas
     * @return {@code true} se o funcionário for registado com sucesso (pelo menos uma linha afetada),
     * {@code false} caso contrário
     * @throws RuntimeException Se ocorrer um erro durante a execução da instrução SQL na base de dados,
     * encapsulando a {@link SQLException} original.
     */
    public boolean RegistarFuncionario(Funcionario f) {
        String sql = "Insert into funcionario (NIF,Telemovel,Morada,NivelE,DataI,id) values (?,?,?,?,?,?)";

        try (Connection conn = DBConnection.getconn();
        PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1,f.getNif());
            ps.setString(2,f.getTelemovel());
            ps.setString(3,f.getMorada());
            ps.setString(4,f.getNivelE());
            ps.setDate(5, new Date(f.getDataI().getTime()));
            ps.setInt(6,f.getIdUtilizador());

            int criarFuncionario = ps.executeUpdate();
            return criarFuncionario > 0;


        } catch (SQLException e) {
            throw new RuntimeException(e);

        }
    }

    /**
     * Metodo estático para verificar se um determinado utilizador possui um perfil de funcionário registado
     * <p>
     *      O metodo realiza uma consulta na tabela "funcionario" utilizando o identificador único (ID)
     *      fornecido. Se a consulta retornar um registo, confirma-se que o utilizador está devidamente
     *      associado ao papel de funcionário no sistema, permitindo-lhe o acesso a funcionalidades
     *      restritas a este grupo. Utiliza o padrão {@code try-with-resources} para garantir que os
     *      recursos da base de dados são libertados após a operação.
     * </p>
     * @param id O identificador único do utilizador a ser verificado na base de dados
     * @return {@code true} se for encontrado um registo na tabela de funcionários, {@code false} caso contrário
     * @throws RuntimeException Se ocorrer um erro durante a ligação ou a execução da consulta SQL
     * na base de dados, encapsulando a respetiva {@link SQLException}.
     */
    public static boolean verSeFuncionario(int id) {
        String sql = "select * from funcionario where id=?";
        boolean existeFuncionario = false;

        try (Connection conn = DBConnection.getconn();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                existeFuncionario = true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return existeFuncionario;
    }


    /**
     * Método estático para contabilizar o número de notificações de reparações atribuídas a um funcionário específico
     * <p>
     * O método executa uma consulta de contagem na tabela de reparações filtrando os registos onde o estado é
     * igual a 2 (representando reparações pendentes ou sob uma condição específica de fluxo) e o campo do
     * funcionário associado corresponde ao identificador numérico obtido a partir do objeto {@link Utilizador}.
     * </p>
     * @param userlogado O objeto {@link Utilizador} que representa a conta do funcionário para o qual as
     * notificações serão contabilizadas.
     * @return O número inteiro totalizador de registos de reparação que satisfazem as condições da pesquisa.
     * @throws RuntimeException Se ocorrer um erro na comunicação ou execução do comando SQL com a base de dados,
     * capturando a exceção original do tipo {@link SQLException}.
     */

    public static int notificacoes(Utilizador userlogado) {
        int counterFinal = 0;

        String sql = "Select count(*) from reparacao where estado = 2 and FuncionarioA = ?";

        try (Connection conn = DBConnection.getconn();
             PreparedStatement ps = conn.prepareStatement(sql))
        {
            ps.setInt(1, userlogado.getId());
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                counterFinal = rs.getInt(1);
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return counterFinal;
    }
}
