package view.Gestor;

import controller.AdminController;
import controller.UtilizadorController;
import model.Utilizador;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

public class PesquisarUtilizadores extends JPanel {
    private Utilizador userLogado;
    private UtilizadorController utilizadorController = new UtilizadorController();
    private AdminController adminController = new AdminController();
    private JTextField pesquisa = new JTextField(15);
    private JButton pesquisar = new JButton("Pesquisar");
    private ButtonGroup button = new ButtonGroup();
    private JRadioButton nome = new JRadioButton("Nome");
    private JRadioButton email = new JRadioButton("Email");
    private JRadioButton username = new JRadioButton("Username");
    public PesquisarUtilizadores(Utilizador u) {
        this.userLogado = u;

        setLayout(new BorderLayout());
        setBackground(Color.white);

        JLabel title = new JLabel("Pesquisar utilizadores");

        title.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(title, BorderLayout.NORTH);

        JPanel Pesquisa = new JPanel();
        Pesquisa.setBackground(Color.white);

        Pesquisa.add(pesquisa);
        Pesquisa.add(pesquisar);
        Pesquisa.add(nome);
        Pesquisa.add(email);
        Pesquisa.add(username);

        button.add(nome);
        button.add(email);
        button.add(username);
        // Obter o butao selecionado e o seu valor solucao do Majdi e do AlexW no:
        //https://stackoverflow.com/questions/201287/how-do-i-get-which-jradiobutton-is-selected-from-a-buttongroup
        //acedido no dia 25/06/2026
        nome.setActionCommand("nome");
        email.setActionCommand("email");
        username.setActionCommand("username");

        add(Pesquisa, BorderLayout.CENTER);

        pesquisar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String pesquisado = pesquisa.getText();
                // Obter o butao selecionado e o seu valor solucao do Majdi e do AlexW no:
                //https://stackoverflow.com/questions/201287/how-do-i-get-which-jradiobutton-is-selected-from-a-buttongroup
                //acedido no dia 25/06/2026
                String escolha = button.getSelection().getActionCommand();
                if (pesquisado.isEmpty()) {
                    JOptionPane.showMessageDialog(
                            PesquisarUtilizadores.this,
                            "Por favor insira um id de um equipamento para pesquisar",
                            "Erro",
                            JOptionPane.ERROR_MESSAGE
                    );
                } else {
                    try {
                        String coisaPesquisada = pesquisa.getText();
                        List<Utilizador> pesquisa = adminController.ConsultaUtilizadores(userLogado,escolha,coisaPesquisada);

                        if ( pesquisa != null) {
                            String[] columnNames = {"id","nome","username","email"};
                            String[][] data = new String[pesquisa.size()][4];

                            for (int i = 0; i < pesquisa.size(); i++) {
                                Utilizador utilizador = pesquisa.get(i);
                                data[i][0] = String.valueOf(utilizador.getId());
                                data[i][1] = utilizador.getNome();
                                data[i][2] = utilizador.getUsername();
                                data[i][3] = utilizador.getEmail();
                            }
                            JTable table = new JTable(data,columnNames);
                            JScrollPane scrollPane = new JScrollPane(table);
                            scrollPane.setBorder(BorderFactory.createEmptyBorder());

                            removeAll();
                            add(scrollPane,BorderLayout.CENTER);
                            revalidate();
                            repaint();
                        }
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }

                }
            }
        });


    }


}
