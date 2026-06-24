package view;
import controller.AdminController;
import model.Utilizador;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.awt.*;
import java.sql.SQLException;


/**
 * Classe responsável por criar a interface gráfica para a eliminação de contas de utilizadores
 * <p>
 * Esta classe estende {@link JPanel} e disponibiliza ao administrador uma tabela que contém as contas
 * elegíveis para eliminação, permitindo a seleção e remoção definitiva através de uma caixa de diálogo.
 * </p>
 */
public class ApagarContas extends JPanel {

    private Utilizador userLogado;
    private AdminController adminController =  new AdminController();



    /**
     * Construtor da classe que inicializa a interface gráfica e carrega a listagem de contas a apagar
     * <p>
     * O construtor configura o aspeto visual do painel e invoca o método ({@code adminController.verContasPorApagar})
     * para obter as contas da base de dados. Se a lista for válida, preenche uma {@link JTable} com o ID e o
     * nome de utilizador de cada conta. Adicionalmente, configura o ouvinte de eventos ({@link ActionListener})
     * para o botão de eliminação, que solicita ao administrador o ID do utilizador a remover, dessa forma validando a
     * operação na base de dados através do método ({@code adminController.apagarContas}).
     * </p>
     * @param u O objeto {@link Utilizador} que representa o administrador com sessão iniciada, utilizado para
     * validar as permissões de acesso às operações de listagem e eliminação de contas.
     * @throws SQLException Se existir algum erro na comunicação com a base de dados ao tentar ler a lista de
     * contas por apagar através do método ({@code adminController.verContasPorApagar}).
     */

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
