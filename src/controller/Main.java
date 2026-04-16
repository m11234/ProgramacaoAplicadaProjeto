package controller;

import model.db.DBConnection;
import view.Menu;

import java.sql.SQLException;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
public class Main {
  static void main() throws SQLException {
    /**
     * Solução adaptada de: Stackify
     * Fonte: <a href="https://stackify.com/heres-how-to-calculate-elapsed-time-in-java/">Here's How to Calculate Elapsed Time in Java</a>
     * Acedido em: 17 de Abril de 2026.
     */
    Instant start = Instant.now();
    LocalDateTime startDateAndTime = LocalDateTime.now();

    if (DBConnection.getconn() == null) {
      System.out.println("Erro na ligação à BD.");
      return;
    }

    Menu menu = new Menu();
    menu.start();
    // time passes
    Instant end = Instant.now();
    LocalDateTime endDateAndTime = LocalDateTime.now();
    Duration timeElapsed = Duration.between(start, end);
    System.out.println("Início do processo:" + startDateAndTime);
    System.out.println("Tempo total: " + timeElapsed.toSeconds() + " segundos.");
    System.out.println("Fim do processo:" + endDateAndTime);
  }
}