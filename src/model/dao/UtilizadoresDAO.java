package model.dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.Utilizador;
import model.db.DBConnection;


public class UtilizadoresDAO {
    public boolean RegistarUtilizador(Utilizador u ) {
        String sql = "Insert into utilizador (nome,username,password,estado,email) values (?,?,?,0,?)";

        // o que for feito dentro do try é fechado automaticamente ()
        // falta fazer um select para ver se o username e email ja existem e verificar o email
        try (Connection conn = DBConnection.getconn();
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
    public Utilizador Login(Utilizador u) throws SQLException {
        String sql = "SELECT id,nome, username, password, email, estado FROM utilizador WHERE username = ? AND password = ?";

        try (Connection conn = DBConnection.getconn();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, u.getUsername());
            ps.setString(2, u.getPassword()); // ideal: comparar hash

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Utilizador logado = new Utilizador();
                logado.setNome(rs.getString("nome"));
                logado.setUsername(rs.getString("username"));
                logado.setPassword(rs.getString("password"));
                logado.setEmail(rs.getString("email"));
                logado.setEstado(rs.getBoolean("estado"));
                logado.setId(rs.getInt("id"));
                return logado;
            } else {
                return null;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public Utilizador ConsultarDados(Utilizador userLogado) {
        String sql = "Select nome, username, password , email , estado from utilizador where username = ?";

        try (Connection conn = DBConnection.getconn();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1,userLogado.getUsername());


            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Utilizador u = new Utilizador();
                u.setNome(rs.getString("nome"));
                u.setUsername(rs.getString("username"));
                u.setPassword(rs.getString("password"));
                u.setEmail(rs.getString("email"));
                u.setEstado(rs.getBoolean("estado"));
                return u;

            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return userLogado;
    }

    public boolean AtualizarDados(Utilizador userLogado, Utilizador dadosNovos) {
        if (!userLogado.getUsername().equals(dadosNovos.getUsername())) {
            throw new SecurityException("Ação proibida!!");
        }
        String sql = "UPDATE utilizador SET password = ?, email = ?  WHERE username = ?";


        try (Connection conn = DBConnection.getconn();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1,dadosNovos.getPassword());
            ps.setString(2,dadosNovos.getEmail());
            ps.setString(3,dadosNovos.getUsername());
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return true;
    }

}




