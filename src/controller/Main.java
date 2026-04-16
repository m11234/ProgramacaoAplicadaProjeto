package controller;

import model.db.DBConnection;
import view.Menu;

import java.sql.SQLException;

public class Main {
  static void main() throws SQLException {

    if (DBConnection.getconn() == null) {
      System.out.println("Erro na ligação à BD.");
      return;
    }

    Menu menu = new Menu();
    menu.start();
  }
}