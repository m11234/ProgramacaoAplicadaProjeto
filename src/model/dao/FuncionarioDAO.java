package model.dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Date;

import model.Funcionario;
import model.db.DBConnection;

public class FuncionarioDAO {
    public boolean RegistarFuncionario(Funcionario f) {
        String sql = "Insert into funcionario (NIF,Telemovel,Morada,NivelE,DataI,id) values (?,?,?,?,?,?)";

        try (Connection conn = DBConnection.getconn();
        PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1,f.getNif());
            ps.setInt(2,f.getTelemovel());
            ps.setString(3,f.getMorada());
            ps.setInt(4,f.getNivelE());
            ps.setDate(5, new Date(f.getDataI().getTime()));
            ps.setInt(6,f.getIdUtilizador());

            int criarFuncionario = ps.executeUpdate();
            return criarFuncionario > 0;


        } catch (SQLException e) {
            throw new RuntimeException(e);

        }
    }
}
