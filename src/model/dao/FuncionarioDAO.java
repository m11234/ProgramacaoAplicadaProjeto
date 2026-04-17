package model.dao;
import java.sql.*;

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
            ps.setInt(1,f.getNif());
            ps.setInt(2,f.getTelemovel());
            ps.setString(3,f.getMorada());
            ps.setInt(4,f.getNivelE());
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
}
