package model.dao;

import controller.AdminController;
import model.Admin;
import model.Reparacao;
import model.Utilizador;
import model.db.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.dao.UtilizadoresDAO;
import java.sql.Date;
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
        String sql = "Select * from reparacao where estado = 1 order by idR";
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

    public List<Reparacao> verReparacoesPorAprovarF(Utilizador userlogado) throws SQLException {
        String sql = "Select * from reparacao where estado = 2 and FuncionarioA = ? order by idR";
        List<Reparacao> listaR = new ArrayList<>();
        try (Connection conn = DBConnection.getconn();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userlogado.getId());

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Reparacao r = new Reparacao();
                ps.setInt(1, (userlogado.getId()));
                r.setIdR(rs.getInt("idR"));
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

    public List<Reparacao> verReparacoesPorFinalizarF(Utilizador userlogado) throws SQLException {
        String sql = "Select * from reparacao where estado = 3 and FuncionarioA = ?";
        List<Reparacao> listaR = new ArrayList<>();
        try (Connection conn = DBConnection.getconn();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userlogado.getId());

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Reparacao r = new Reparacao();
                ps.setInt(1, (userlogado.getId()));
                r.setIdR(rs.getInt("idR"));
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

    public boolean AceitarReparacaoDAO(Reparacao r){
        String sql = "Update reparacao set Estado = 2 , FuncionarioA = ? where idR = ? ";
        try (Connection conn = DBConnection.getconn();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, r.getFuncionarioA());
            ps.setInt(2,r.getIdR());
            int ReparacaoAceite = ps.executeUpdate();
            return (ReparacaoAceite > 0);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public boolean AceitarReparacaoFDAO(Reparacao r , int id) {

        if (id != (r.getFuncionarioA())) {
            throw new SecurityException("Ação proibida: Só pode aceitar ou rejeitar a sua própria reparação!");
        }
        String sql = "Update reparacao set Estado = ? , DataInicio = current_date  where idR = ? and FuncionarioA = ? ";

        try (Connection conn = DBConnection.getconn();
        PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, r.getEstado());
            ps.setInt(2, r.getIdR());
            ps.setInt(3, r.getFuncionarioA());

            int ReparacaoAceiteF = ps.executeUpdate();
            return (ReparacaoAceiteF > 0);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public boolean FinalizarReparacaoFDAO(Reparacao r , int id) {

        if (id != (r.getFuncionarioA())) {
            throw new SecurityException("Ação proibida: Só pode finalizar a sua própria reparação!");
        }
        String sql = "Update reparacao set Estado = ? , DataFim = current_date  where idR = ? and FuncionarioA = ? ";

        try (Connection conn = DBConnection.getconn();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, r.getEstado());
            ps.setInt(2, r.getIdR());
            ps.setInt(3, r.getFuncionarioA());

            int ReparacaoAceiteF = ps.executeUpdate();
            return (ReparacaoAceiteF > 0);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }






}
