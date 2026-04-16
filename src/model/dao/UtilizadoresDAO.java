package model.dao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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
        String sql = "SELECT id,nome, username, password, email, estado FROM utilizador WHERE username = ? AND password = ? ";

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
    public Utilizador ConsultarDadosGestor(int id) {
        String sql = "Select nome, username, password , email , estado from utilizador where id = ?";

        try (Connection conn = DBConnection.getconn();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Utilizador Dados = new Utilizador();
                Dados.setNome(rs.getString("nome"));
                Dados.setUsername(rs.getString("username"));
                Dados.setPassword(rs.getString("password"));
                Dados.setEmail(rs.getString("email"));
                Dados.setEstado(rs.getBoolean("estado"));
                return Dados;

            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null ;
    }


    public Utilizador ConsultarNomeUtilizador(String nome) {
        String sql = "Select nome, username, email from utilizador where nome = ?";

        try (Connection conn = DBConnection.getconn();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, nome);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Utilizador Dados = new Utilizador();
                Dados.setNome(rs.getString("nome"));
                Dados.setUsername(rs.getString("username"));
                Dados.setEmail(rs.getString("email"));
                return Dados;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null ;
    }

    public Utilizador ConsultarUsername(String username) {
        String sql = "Select nome, username, email from utilizador where username = ?";

        try (Connection conn = DBConnection.getconn();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Utilizador Dados = new Utilizador();
                Dados.setNome(rs.getString("nome"));
                Dados.setUsername(rs.getString("username"));
                Dados.setEmail(rs.getString("email"));
                return Dados;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null ;
    }

    public Utilizador ConsultarEmailUtilizador(String email) {
        String sql = "Select nome, username, email from utilizador where email = ?";

        try (Connection conn = DBConnection.getconn();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Utilizador Dados = new Utilizador();
                Dados.setNome(rs.getString("nome"));
                Dados.setUsername(rs.getString("username"));
                Dados.setEmail(rs.getString("email"));
                return Dados;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null ;
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


    public boolean atualizarEmailGestor(int id, Utilizador dadosNovos) {
        String sql = "UPDATE utilizador SET email = ? WHERE id = ?";

        try (Connection conn = DBConnection.getconn();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, dadosNovos.getEmail());

            ps.setInt(2, id);

            int linhasAfetadas = ps.executeUpdate();

            return linhasAfetadas > 0;

        } catch (SQLException e) {
            System.out.println("Erro ao tentar atualizar o email na base de dados.");
            throw new RuntimeException(e);
        }
    }

    public boolean mudarEstadoGestor(Utilizador userLogado) throws SQLException {
        String sql = "UPDATE utilizador SET estado = 1 WHERE username = ?";

        try (Connection conn = DBConnection.getconn();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setBoolean(1,userLogado.getEstado());
            ps.setString(1, userLogado.getUsername());
            ps.executeUpdate();
    }
        return true;
    }

    public boolean mudarEstado(int id) throws SQLException {
        String sql = "UPDATE utilizador SET estado = 1 WHERE id = ?";
        boolean contaAtivada = false;

        try (Connection conn = DBConnection.getconn();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            int atividado = ps.executeUpdate();
            contaAtivada = atividado == 1;

        }
        return contaAtivada;
    }
    public List<Utilizador> verContasPorAtivar() {
        String sql = "Select * from utilizador where estado = 0";
        List<Utilizador> lista = new ArrayList<>();
        try (Connection conn = DBConnection.getconn();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Utilizador u = new Utilizador();
                u.setUsername(rs.getString("username"));
                u.setId(rs.getInt("id"));
                lista.add(u);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Lista de utilizadores: " + lista);
        return lista;
    }
    public boolean verSeContaExiste(int id) throws SQLException {
        String sql = "Select * from utilizador WHERE id = ?";
        boolean contaExiste = false;

        try (Connection conn = DBConnection.getconn();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                contaExiste = true;
            }

        }
        return contaExiste;
    }


}




