package model.dao;

import model.db.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PecaDAO {

    public boolean decrementarStock(int idPeca, int quantidadeUsada) {
        // SQL que subtrai a quantidade atual pela quantidade gasta
        String sql = "UPDATE peca SET quantidade = quantidade - ? WHERE idPeca = ? AND quantidade >= ?";

        try (Connection conn = DBConnection.getconn();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, quantidadeUsada);
            ps.setInt(2, idPeca);
            ps.setInt(3, quantidadeUsada); // Garante que o stock não fica negativo

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar stock: " + e.getMessage());
        }
    }
}
