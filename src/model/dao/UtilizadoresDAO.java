package model.dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import model.Utilizador;
import model.db.DBConnection;

public class UtilizadoresDAO {
    public boolean RegistarUtilizador(Utilizador u ) {
        String sql = "Insert into utilizador (nome,username,password,estado,email) values (?,?,?,0,?)";

        // o que for feito dentro do try é fechado automaticamente ()
        // falta fazer um select para ver se o username e email ja existem e verificar o email
        try (Connection conn = DBConnection.getConn();
             PreparedStatement ps = conn.prepareStatement(sql))  {

            ps.setString(1, u.getNome());
            ps.setString(2, u.getUsername());
            ps.setString(3, u.getPassword());
            ps.setString(4, u.getEmail());

            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println("Dados inseridos invalidos ou já existe um utilizador com os dados inseridos");
            throw new RuntimeException(e);
        }
    }
}

