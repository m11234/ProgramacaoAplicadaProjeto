package model.dao;

import model.Peca;
import model.db.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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
}
