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

    public void verReparacoesPorAprovar() {
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
    }

    public List<Reparacao> verReparacoesPorAprovarF(Utilizador userlogado) {
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

    public List<Reparacao> verReparacoesPorFinalizarF(Utilizador userlogado) {
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

            ps.setInt(1, Reparacao.getFuncionarioA());
            ps.setInt(2,r.getIdR());
            int ReparacaoAceite = ps.executeUpdate();
            return (ReparacaoAceite > 0);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public boolean AceitarReparacaoFDAO(Reparacao r , int id) {

        if (id != (Reparacao.getFuncionarioA())) {
            throw new SecurityException("Ação proibida: Só pode aceitar ou rejeitar a sua própria reparação!");
        }
        String sql = "Update reparacao set Estado = ? , DataInicio = current_date  where idR = ? and FuncionarioA = ? ";

        try (Connection conn = DBConnection.getconn();
        PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, r.getEstado());
            ps.setInt(2, r.getIdR());
            ps.setInt(3, Reparacao.getFuncionarioA());

            int ReparacaoAceiteF = ps.executeUpdate();
            return (ReparacaoAceiteF > 0);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public boolean FinalizarReparacaoFDAO(Reparacao r , int id) {

        if (id != (Reparacao.getFuncionarioA())) {
            throw new SecurityException("Ação proibida: Só pode finalizar a sua própria reparação!");
        }
        String sql = "Update reparacao set Estado = ? , DataFim = current_date  where idR = ? and FuncionarioA = ? ";

        try (Connection conn = DBConnection.getconn();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, r.getEstado());
            ps.setInt(2, r.getIdR());
            ps.setInt(3, Reparacao.getFuncionarioA());

            int ReparacaoAceiteF = ps.executeUpdate();
            return (ReparacaoAceiteF > 0);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void notificacaoDezDiasSemFinalizacao(){
        String sql = "Select * from reparacao where (CURRENT_DATE - DataInicio) > 10 and estado != 4";
        List<Reparacao> listaR = new ArrayList<>();
        try (Connection conn = DBConnection.getconn();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                Reparacao r = new Reparacao();
                r.setIdR(rs.getInt("idR"));
                r.setDataInicio(rs.getDate("DataInicio"));
                r.setIdEquip(rs.getInt("idEquip"));
                r.setObservacao(rs.getString("Observacao"));
                listaR.add(r);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Lista de reparacoes que passou 10 dias sem ser finalizado: " + listaR);
    }

    public static Reparacao PesquisarPedidosReparacao(int idR) {
        String sql = "SELECT * FROM reparacao WHERE idR=?";
        try (Connection conn = DBConnection.getconn();
             PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setInt(1, idR);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Reparacao re = new Reparacao();
                re.setIdR(rs.getInt("IdR"));
                re.setDataInicio(rs.getDate("DataInicio"));
                re.setDataFim(rs.getDate("DataFim"));
                re.setCusto(rs.getInt("Custo"));
                re.setObservacao(rs.getString("Observacao"));
                return re;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Pedido: ");
        return null;

    }
}
