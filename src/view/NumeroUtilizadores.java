package view;

import controller.AdminController;
import controller.ReparacaoController;
import model.Utilizador;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class NumeroUtilizadores extends JPanel {
    private Utilizador userLogado;
    private ReparacaoController reparacaoController =  new ReparacaoController();
    private AdminController adminController =  new AdminController();

    public NumeroUtilizadores(Utilizador u) throws SQLException {
        this.userLogado = u;

        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        JLabel title = new JLabel("Numero de utilizadores");
        title.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(title, BorderLayout.NORTH);

        int numeroUtilizadores = adminController.NumeroDeUtilizadores(userLogado);

        if (numeroUtilizadores > 0) {
            JLabel numeroUtilizadore = new JLabel("Numero de utilizadores" + numeroUtilizadores);
            add(numeroUtilizadore, BorderLayout.WEST);
        } else  {
            JLabel numeroUtilizadore = new JLabel("Não existem utlizadores");
            add(numeroUtilizadore, BorderLayout.WEST);
        }
    }
}
