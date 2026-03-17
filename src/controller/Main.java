package controller;

import model.db.DBConnection;

public class Main {
  public static void main(String[] args) {

    // Testar ligação à base de dados
    DBConnection.connection();

    // Verificar se a ligação foi criada
    if (DBConnection.getConn() != null) {
      System.out.println("Ligação estabelecida com sucesso!");
    } else {
      System.out.println("Falha na ligação.");
    }
  }
}