package view;
import controller.AdminController;
import model.Utilizador;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.awt.*;
import java.sql.SQLException;

public class ApagarContas extends JPanel {

    private Utilizador userLogado;
    private AdminController adminController =  new AdminController();

    public ApagarContas(Utilizador u) throws SQLException {
        this.userLogado = u;
        //mesma coisa que no de ativar contas o controller so precisava de receber o id do user atual
        //para confirmar que ele é admin

        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        JLabel title = new JLabel("Contas por Apagar", SwingConstants.CENTER);
        title.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(title, BorderLayout.NORTH);

        List<Utilizador> porApagar = adminController.verContasPorApagar(userLogado);

        if (porApagar != null) {
            String[] columnNames = {"id", "nome"};
            String[][] data = new String[porApagar.size()][2];

            for (int i = 0; i < porApagar.size(); i++) {
                Utilizador Apagar = porApagar.get(i);
                data[i][0] = String.valueOf(Apagar.getId());
                data[i][1] = Apagar.getUsername();
            }

            JTable table = new JTable(data, columnNames);
            JScrollPane scrollPane = new JScrollPane(table);
            scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 50, 50, 50));

            JPanel Apagar =  new JPanel();
            Apagar.setBackground(Color.WHITE);

            JButton ButaoApagar = new JButton("Apagar");
            Apagar.add(ButaoApagar, BorderLayout.SOUTH);

            add(scrollPane, BorderLayout.CENTER);
            add(Apagar, BorderLayout.NORTH);

            ButaoApagar.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {

                    String input = JOptionPane.showInputDialog(
                            "Introduza o id da conta a apagar",
                            "Apagar Conta," +
                                    JOptionPane.QUESTION_MESSAGE
                    );
                    if (input != null) {
                        int idApagar = Integer.parseInt(input);

                        try {
                            boolean sucesso = adminController.apagarContas(userLogado,idApagar);
                            if (sucesso) {
                                JOptionPane.showMessageDialog(ApagarContas.this,
                                        "Conta ID " + idApagar + " apagada com sucesso!)",
                                        "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                            } else {
                                JOptionPane.showMessageDialog(ApagarContas.this,
                                        "Erro: O ID não existe",
                                        "Erro", JOptionPane.ERROR_MESSAGE);
                            }
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                }
            });
        }
    }
}
