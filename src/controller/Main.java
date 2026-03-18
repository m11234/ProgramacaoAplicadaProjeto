package controller;

import model.db.DBConnection;
import view.Menu;

public class Main {
  public static void main(String[] args) {

    DBConnection.connection();

    if (DBConnection.getConn() == null) {
      System.out.println("Erro na ligação à BD.");
      return;
    }

    Menu menu = new Menu();
    menu.start();
  }
}