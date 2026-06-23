package view;

import model.Utilizador;
import controller.UtilizadorController;

import javax.swing.*;
import java.awt.*;

public class ConsultarDadosContaGeral extends JPanel {

    private Utilizador userLogado;
    private UtilizadorController controller = new UtilizadorController();

    public ConsultarDadosContaGeral(Utilizador u) {
        this.userLogado = u;

        Utilizador dados = controller.ConsultarDados(u);

        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        JLabel title = new JLabel("Consultar Dados da Conta", SwingConstants.CENTER);
        title.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(title, BorderLayout.NORTH);

        String caminho ;

        if (dados.getNome().isEmpty()) {
            caminho = "CoisasFeitas/meow_meow/IMG_0373.jpg";
            System.out.println("A foto por defeito existe? " + new java.io.File("CoisasFeitas/meow_meow/IMG_0373.jpg").exists());
        } else {
            caminho = dados.getFoto();
        }
        System.out.println(caminho);

        // solucao adaptada do powerpoint 6.3 pagina 33
        // solucao do Professor Marco Veloso
        //consultada no dia 23/06/2026

        Image image = Toolkit.getDefaultToolkit().getImage(caminho);

            String[] columnNames = {"Campo", "Informação Pessoal"};
            String[][] data = {
                    {"Nome", dados.getNome()},
                    {"Username", dados.getUsername()},
                    {"Email", dados.getEmail()},
            };

            JTable table = new JTable(data, columnNames);
            table.setRowHeight(30);
            table.setEnabled(false);

            JLabel myLabel = new JLabel(new ImageIcon(image));
            myLabel.setBorder(BorderFactory.createEmptyBorder(0, 30, 0, 0));


            JScrollPane scrollPane = new JScrollPane(table);
            scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 50, 50, 50));
            add(scrollPane, BorderLayout.CENTER);
            add(myLabel, BorderLayout.WEST);

        }
    }