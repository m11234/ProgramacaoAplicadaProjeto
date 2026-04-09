package model.dao;

import controller.AdminController;
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

    public List<Reparacao> verReparacoesPorAprovar() {
        String sql = "Select * from reparacao where estado = 1";
        List<Reparacao> listaR = new ArrayList<>();
        try (Connection conn = DBConnection.getconn();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Reparacao r = new Reparacao();
                r.setIdEquip(rs.getInt("idEquip"));
                r.setObservacao(rs.getString("Observacao"));
                listaR.add(r);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Lista de reparacoes por aprovar: " + listaR);
        return listaR;
    }



}
