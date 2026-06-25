package view.Gestor;

import controller.AdminController;
import controller.ReparacaoController;
import model.Reparacao;
import model.Utilizador;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

public class ReparacaoPesquisa extends JPanel {
    private Utilizador userLogado;
    private ReparacaoController reparacaoController = new ReparacaoController();
    private AdminController adminController = new AdminController();
    private JTextField pesquisa = new JTextField(15);
    private JButton pesquisar = new JButton("Pesquisar");
    
    public ReparacaoPesquisa(Utilizador u) {
        this.userLogado = u;
        
        setLayout(new BorderLayout());
        setBackground(Color.white);
        
        JLabel title = new JLabel("Pesquisar por uma reparacao");
        title.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(title, BorderLayout.NORTH);
        
        JPanel Pesquisa = new JPanel();
        Pesquisa.setBackground(Color.white);
        
        Pesquisa.add(pesquisa);
        Pesquisa.add(pesquisar);

        add(Pesquisa, BorderLayout.CENTER);
        
        pesquisar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String pesquisado = pesquisa.getText();
                
                if (pesquisado.isEmpty()) {
                    JOptionPane.showMessageDialog(
                            ReparacaoPesquisa.this,
                            "Por favor insira um id de uma reparacao para pesquisar",
                            "Erro",
                            JOptionPane.ERROR_MESSAGE
                    );
                } else {
                    try {
                        int idPesquisado = Integer.parseInt(pesquisado);
                        List<Reparacao> porEncontrar = adminController.ConsultarReparacao(userLogado,idPesquisado);
                        
                        if (porEncontrar != null) {
                            String[] columnNames = {"idReparacai","data inicio","ide","obs"};
                            String[][] data = new String[porEncontrar.size()][4];

                            for(int i = 0; i < porEncontrar.size(); i++) {
                                Reparacao PorEncontrar = porEncontrar.get(i);
                                data[i][0] = String.valueOf(PorEncontrar.getIdR());
                                data[i][1] = String.valueOf(PorEncontrar.getDataInicio());
                                data[i][2] = String.valueOf(PorEncontrar.getIdEquip());
                                data[i][3] = String.valueOf(PorEncontrar.getObservacao());
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
