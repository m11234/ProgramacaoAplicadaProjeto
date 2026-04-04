package model.dao;
import java.sql.*;

import model.Cliente;
import model.db.DBConnection;


public class ClienteDAO {
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
    public boolean VerSeCliente(int Id) throws SQLException {
        String sql = "SELECT * FROM clientes WHERE id=?";
        boolean ClienteSer = false;
        try (Connection conn = DBConnection.getconn();
             PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setInt(1, Id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                System.out.println("O utilizador é um cliente proseguir...");
                ClienteSer = true;
            }
        }
        return ClienteSer;
    }
}
