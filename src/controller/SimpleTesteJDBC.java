package controller;
import java.sql.*;

public class SimpleTesteJDBC {

    public static void main (String [] args) {

        Connection conn = null;
        Statement st = null;
        ResultSet rs = null;

        try {
            //Class.forName("com.mysql.jdbc.Driver").newInstance();	// JDBC < 8.x
            Class.forName("com.mysql.cj.jdbc.Driver");				// JDBC > 8.x

            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/bd?useSSL=false&useTimezone=true&serverTimezone=UTC", "root", "mysql");
            st = conn.createStatement();

            String pesquisa = "miguel";

// pesquisar todos os nomes com caracter 'i'
            StringBuffer sqlQuery = new StringBuffer();
            sqlQuery.append(" SELECT * FROM java_test ");
            sqlQuery.append(" WHERE nomes LIKE ? ");

            PreparedStatement ps = conn.prepareStatement(sqlQuery.toString());
            ps.clearParameters();
            ps.setString(1, "%" + pesquisa + "%");

            rs = ps.executeQuery();

            //rs = st.executeQuery("SELECT * FROM java_test");

            if (rs == null ) {
                System.out.println("!! No Record on table !!");
            } else
                while (rs.next()) {
                    int 	column1 = rs.getInt("numeros");
                    String column2 = rs.getString("nomes");
                    System.out.println("Column1 = "+ column1 + " ; Column2 = "+column2);
                }

        } catch (SQLException e) {
            System.err.println("!! SQL Exception !!\n"+e);
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.err.println("!! Class Not Found. Unable to load Database Drive !!\n"+e);
			/*
			// JDBC < 8.x
			} catch (IllegalAccessException e) {
					System.err.println("!! Illegal Access !!\n"+e);
			} catch (InstantiationException e) {
					System.err.println("!! Class Not Instanciaded !!\n"+e);
			*/
        } finally {
            if (st != null) {
                try {
                    st.close();
                } catch (Exception e) {
                    System.err.println("!! Exception returning statement !!\n"+e);
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (Exception e) {
                    System.err.println("!! Exception closing DB connection !!\n"+e);
                }
            }
        } // end of finally

    }
}
