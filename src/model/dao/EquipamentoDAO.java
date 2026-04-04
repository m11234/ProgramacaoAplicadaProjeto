package model.dao;

import model.Equipamento;
import model.db.DBConnection;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class EquipamentoDAO {

    public boolean RegistarEquipamento (Equipamento e){
        String sql = "Insert into equipamento (Marca, Modelo, SKU, Lote, DataSubmissao, idC) value (?,?,?,?,?,?)";

        try (Connection conn = DBConnection.getconn();
        PreparedStatement ps = conn.prepareStatement(sql)){

            ps.setString(1, e.getMarca());
            ps.setString(2, e.getModelo());
            ps.setString(3, e.getSKU());
            ps.setString(4, e.getLote());
            ps.setDate(5, (Date) e.getDataSubmissao());
            ps.setInt(6, e.getIdC());

            int criarEquipamento = ps.executeUpdate();
            return (criarEquipamento > 0);

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
}
