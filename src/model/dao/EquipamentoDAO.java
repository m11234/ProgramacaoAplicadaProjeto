package model.dao;

import model.Equipamento;
import model.db.DBConnection;

import java.sql.*;

public class EquipamentoDAO {

    public boolean RegistarEquipamento (Equipamento e){
        String sql = "Insert into equipamento (Marca, Modelo, SKU, Lote, DataSubmissao, id) values (?,?,?,?,?,?)";

        try (Connection conn = DBConnection.getconn();
        PreparedStatement ps = conn.prepareStatement(sql)){

            ps.setString(1, e.getMarca());
            ps.setString(2, e.getModelo());
            ps.setString(3, e.getSKU());
            ps.setString(4, e.getLote());
            ps.setDate(5, new java.sql.Date(e.getDataSubmissao().getTime()));
            ps.setInt(6, e.getid());

            int criarEquipamento = ps.executeUpdate();
            return (criarEquipamento > 0);

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
    public boolean verSeEquipPertence(int idEquip,int id) {
        String sql = "Select 1 from equipamento where idEquip = ? and id = ?";
        boolean existeAndPertence = false;
        try (Connection conn = DBConnection.getconn();
             PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setInt(1, idEquip);
            ps.setInt(2, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                System.out.println("Equipamento encontrado...");
                existeAndPertence = true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return existeAndPertence;
    }

}
