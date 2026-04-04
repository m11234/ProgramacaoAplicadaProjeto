package model.dao;

import model.Admin;
import model.db.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class AdminDao {
    public boolean verificarGestores() throws SQLException {
        String sql = "Select count(*) from administrador";
        boolean ExistemGestores = false;

        try (Connection conn = DBConnection.getconn();
             PreparedStatement ps = conn.prepareStatement(sql)){
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int count = rs.getInt(1);
                if (count > 0) {
                    ExistemGestores = true;
                }
                else  {
                    ExistemGestores = false;
                    System.out.println("Nao existem gestores");
                }
            }
        }
        return ExistemGestores;
    }

    public boolean criarGestor(Admin a) throws SQLException {
        String sql = "INSERT INTO administrador (id) VALUES (?)";

        try (Connection conn = DBConnection.getconn();
             PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setInt(1,a.getId());
            int criarGestor = ps.executeUpdate();
            return criarGestor > 0;

        }
    }

    public boolean VerSeGestor(int Id) throws SQLException {
        String sql = "SELECT * FROM administrador WHERE id=?";
        boolean GestorSer = false;
        try (Connection conn = DBConnection.getconn();
             PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setInt(1, Id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                System.out.println("O utilizador é um gestor proseguir...");
                GestorSer = true;
            }
        }
        return GestorSer;
    }
}
