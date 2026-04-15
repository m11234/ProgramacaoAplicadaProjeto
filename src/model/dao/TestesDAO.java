package model.dao;

import model.Teste;
import model.db.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TestesDAO {
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