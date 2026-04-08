package model.dao;

import model.Reparacao;
import model.Utilizador;
import model.db.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReparacaoDAO {
    public boolean SubmeterREparacao(Reparacao r){
        String sql = "Insert into reparacao (observacao, idEquip,Estado) values (?, ?,?)";

        try (Connection conn = DBConnection.getconn();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, r.getObservacao());
            ps.setInt(2, r.getIdEquip());
            ps.setInt(3, r.getEstado());

            int criarReparacao = ps.executeUpdate();
            return (criarReparacao > 0);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
