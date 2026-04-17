package model.dao;

import model.Teste;
import model.db.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TestesDAO {
    /**
     * Submete o registo de um novo teste técnico associado a uma reparação.
     * <p>
     *      O processo começa por executar uma instrução de inserção na base de dados suportada por
     *      uma consulta de validação. Os dados técnicos do teste (designação, descrição e preço) e
     *      a data atual só são gravados na tabela "testes" se a reparação indicada existir e se
     *      encontrar explicitamente atribuída ao funcionário que está a tentar efetuar o registo.
     *      Caso a inserção não afete nenhuma linha, a operação é bloqueada e é imediatamente lançada
     *      uma exceção de segurança, impedindo que um funcionário manipule reparações que não lhe pertencem.
     * </p>
     * @param t O objeto {@link Teste} contendo as informações a registar (designação, descrição, preço e identificador da reparação).
     * @param idFuncionario O identificador único do funcionário que está a submeter o teste.
     * @return {@code true} se o teste for inserido com sucesso na base de dados.
     * @throws SecurityException Se a reparação não existir ou se o funcionário tentar registar um teste numa reparação que não lhe está atribuída.
     * @throws RuntimeException Se ocorrer algum erro na manipulação da base de dados envia {@link SQLException}.
     */
    public static boolean submeterTeste(Teste t, int idFuncionario) {

        String sql = "INSERT INTO testes (idReparacao, designacao, descricao, dataTeste, preco) " +
                "SELECT idR, ?, ?, current_date, ? FROM reparacao WHERE idR = ? AND FuncionarioA = ?";

        try (Connection conn = DBConnection.getconn();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, t.getDesignacao());
            ps.setString(2, t.getDescricao());
            ps.setFloat(3, t.getPreco());
            ps.setInt(4, t.getIdReparacao());
            ps.setInt(5, idFuncionario);

            int linhasInseridas = ps.executeUpdate();

            if (linhasInseridas == 0) {
                throw new SecurityException("A reparação não existe ou não pertence a este utilizador!");
            }
            return true;

        } catch (SQLException e) {
            System.out.println("Erro SQL: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}