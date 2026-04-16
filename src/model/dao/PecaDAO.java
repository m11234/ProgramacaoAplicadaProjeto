package model.dao;

import model.Peca;
import model.db.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PecaDAO {

    public boolean InserirPeca(Peca p){
        String sql = "Insert into peca (designacao,fabricante,quantidade,preco) values (?,?,?,?)";

        ry (Connection conn = DBConnection.getconn();
        PreparedStatement ps = conn.prepareStatement(sql)) {

        }
    }
}
