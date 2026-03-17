package model.db;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.Properties;
import java.io.FileInputStream;
import java.io.IOException;

public class DBConnection {

    private static  Connection conn;
    private static Statement st;

    public static void connection() {
        try {
            Properties props = new Properties();
            props.load(new FileInputStream("config/properties"));

            String url = props.getProperty("db.url");
            String user = props.getProperty("db.user");
            String pass = props.getProperty("db.password");
            String driver = props.getProperty("db.driver");

            Class.forName(driver);
            conn = DriverManager.getConnection(url,user,pass);
            st = conn.createStatement();

            System.out.println("Ligacao a base de dados executada com sucesso");


        } catch (FileNotFoundException e) {
            System.out.println("Ficheiro nao encontrado");
            throw new RuntimeException(e);
        } catch (IOException e) {
            System.out.println("Erros a carregar os dados do ficheiro");
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            System.out.println("Ficheiro do driver nao encontrado");
            throw new RuntimeException(e);
        } catch (SQLException e) {
            System.out.println("Erro a conectar");
            throw new RuntimeException(e);
        }
    }
    public static Connection getConn() {
        return conn;
    }
    public static Statement getSt() {
        return st;
    }

}
