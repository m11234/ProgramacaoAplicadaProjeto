package view.Gestor;
//slwslw
import controller.AdminController;
import controller.EquipamentoController;
import model.Equipamento;
import model.Utilizador;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

public class EquipamentoPesquisa extends JPanel{
    private Utilizador userLogado;
    private EquipamentoController equipamentoController = new EquipamentoController();
    private AdminController adminController = new AdminController();
    private JTextField pesquisa = new JTextField(15);
    private JButton pesquisar = new JButton("Pesquisar");

    /**
     * Permite pesquisar por um equipamento pelo seu id
     * @param u
     */
    public EquipamentoPesquisa(Utilizador u) {
        this.userLogado = u;

        setLayout(new BorderLayout());
        setBackground(Color.white);

        JLabel title = new JLabel("Pesquisar por uma equipamento");
        title.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(title, BorderLayout.NORTH);

        JPanel Pesquisa = new JPanel();
        Pesquisa.setBackground(Color.white);

        Pesquisa.add(pesquisa);
        pesquisa.setToolTipText("Pesquisar por um equipamento");
        Pesquisa.add(pesquisar);

        pesquisar.setToolTipText("Pesquisar por um equipamento");
        pesquisar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        add(Pesquisa, BorderLayout.CENTER);

        pesquisar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String pesquisado = pesquisa.getText();

                if (pesquisado.isEmpty()) {
                    JOptionPane.showMessageDialog(
                            EquipamentoPesquisa.this,
                            "Por favor insira um id de um equipamento para pesquisar",
                            "Erro",
                            JOptionPane.ERROR_MESSAGE
                    );
                } else {
                    try {
                        int idPesquisado = Integer.parseInt(pesquisado);
                        List<Equipamento> porEncontrar = adminController.PesquisarEquipamento(userLogado,idPesquisado);

                        if (porEncontrar != null) {
                            String[] columnNames = {"idEquipamento","Marca","Modelo","SKU","DataSubmissao", "DataReparacao"};
                            String[][] data = new String[porEncontrar.size()][6];

                            for(int i = 0; i < porEncontrar.size(); i++) {
                                Equipamento PorEncontrar = porEncontrar.get(i);
                                data[i][0] = String.valueOf(PorEncontrar.getIdEquipamento());
                                data[i][1] = String.valueOf(PorEncontrar.getMarca());
                                data[i][2] = String.valueOf(PorEncontrar.getModelo());
                                data[i][3] = String.valueOf(PorEncontrar.getSKU());
                                data[i][4] = String.valueOf(PorEncontrar.getDataSubmissao());
                                data[i][5] = String.valueOf(PorEncontrar.getDataReparacao());
                            }
                            JTable table = new JTable(data, columnNames);
                            JScrollPane scrollPane = new JScrollPane(table);
                            scrollPane.setBorder(BorderFactory.createEmptyBorder());

                            removeAll();
                            add(scrollPane, BorderLayout.CENTER);
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
