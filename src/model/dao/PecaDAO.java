package model.dao;

import jdk.jshell.execution.Util;
import model.Peca;
import model.PecaUsada;
import model.Utilizador;
import model.db.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PecaDAO {

    public static boolean InserirPeca(Peca p){
        String sql = "Insert into peca (designacao,fabricante,quantidade,preco) values (?,?,?,?)";

        try (Connection conn = DBConnection.getconn();
        PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1,p.getDesignacao());
            ps.setString(2, p.getFabricante());
            ps.setInt(3,p.getQuantidade());
            ps.setFloat(4,p.getPreco());

            int pecaInserida =  ps.executeUpdate();
            return pecaInserida > 0;


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean PecasUsadas(PecaUsada up, int idFuncionario) {

        // assim so insere se a reparacao pertencer aquele funcionario e a peca existir
        String sql = "INSERT INTO peca_usada (idPeca, idReparacao)" +
                "SELECT ?, idR FROM reparacao WHERE idR = ? AND FuncionarioA = ? and Estado = 3";


        try (Connection conn = DBConnection.getconn();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1,up.getIdPeca());
            ps.setInt(2,up.getIdReparacao());
            ps.setInt(3,idFuncionario);

            int pecaUsada = ps.executeUpdate();

            if (pecaUsada == 0) {
                throw new SecurityException("A peca/reparacao não existe ou não pertence a este funcionario!");
            }
            return true;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<Peca>  pecaInferior () {
        String sql = "SELECT * FROM peca WHERE quantidade < 10";
        List<Peca> listaPecas = new ArrayList<>();
        try (Connection conn = DBConnection.getconn();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Peca pu = new Peca();
                pu.setDesignacao(rs.getString("designacao"));
                pu.setFabricante(rs.getString("fabricante"));
                pu.setQuantidade(rs.getInt("quantidade"));
                listaPecas.add(pu);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
            System.out.println("Peças com stock inferior a 10 unidades: " + listaPecas.toString());
            return listaPecas;
        }
    }
