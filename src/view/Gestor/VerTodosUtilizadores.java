package view.Gestor;

import controller.AdminController;
import controller.UtilizadorController;
import model.Reparacao;
import model.Utilizador;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class VerTodosUtilizadores extends JPanel {
    private Utilizador userLogado;
    private UtilizadorController utilizadorController = new UtilizadorController();
    private AdminController adminController = new AdminController();

    /**
     * Devolve ao gestor uma tabela com os dados de todos os utlizadores
     * @param u
     * @throws SQLException
     */
    public VerTodosUtilizadores(Utilizador u) throws SQLException {
        this.userLogado = u;

        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        JLabel title = new JLabel("Todos os utilizadores");
        title.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(title, BorderLayout.NORTH);

        List<Utilizador> todos = adminController.verUtilizador(userLogado);

        if (todos != null) {
            String[] columnNames = {"id","username","nome"};
            String[][] data = new String[todos.size()][3];

            for(int i = 0; i < todos.size(); i++) {
                Utilizador elesTodos = todos.get(i);
                data[i][0] = String.valueOf(elesTodos.getId());
                data[i][1] = String.valueOf(elesTodos.getUsername());
                data[i][2] = String.valueOf(elesTodos.getNome());
            }
            JTable table = new JTable(data, columnNames);
            JScrollPane scrollPane = new JScrollPane(table);
            scrollPane.setBorder(BorderFactory.createEmptyBorder());

            add(scrollPane, BorderLayout.CENTER);
        }

    }

}
