package view.Funcionario;

import controller.ReparacaoController;
import model.Reparacao;
import model.Utilizador;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

public class ReparacaoFinalizar extends JPanel {
    private Utilizador userLogado;
    private ReparacaoController reparacaoController =  new ReparacaoController();
    int Estado;


    public ReparacaoFinalizar(Utilizador u) throws SQLException {
        this.userLogado = u;

        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        JLabel title = new JLabel("Reparações por Finalizar", SwingConstants.CENTER);
        title.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(title, BorderLayout.NORTH);

        List<Reparacao> porFinalizar = ReparacaoController.verReparacoesPorFinalizarF(userLogado);

        if (porFinalizar != null) {
            String[] columnNames = {"ID Reparação", "ID Equipamento","Observações"};
            String[][] data = new String[porFinalizar.size()][3];

            for (int i = 0; i < porFinalizar.size(); i++) {
                Reparacao Finalizar = porFinalizar.get(i);
                data[i][0] = String.valueOf(Finalizar.getIdR());
                data[i][1] = String.valueOf(Finalizar.getIdEquip());
                data[i][2] = String.valueOf(Finalizar.getObservacao());
            }

            JTable table = new JTable(data, columnNames);
            JScrollPane scrollPane = new JScrollPane(table);
            scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 50, 50, 50));

            JPanel Finalizar = new JPanel();
            Finalizar.setBackground(Color.WHITE);

            JButton ButaoFinalizar = new JButton("Finalizar Reparação");
            Finalizar.add(ButaoFinalizar, BorderLayout.SOUTH);

            add(scrollPane, BorderLayout.CENTER);
            add(Finalizar, BorderLayout.NORTH);



            ButaoFinalizar.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    JTextField myText1 = new JTextField(10);
                    JLabel myLabel1 = new JLabel("Insira o ID da reparacao que pretender finalizar");
                    JComponent[] inputs = new JComponent[]
                            {
                                    myLabel1,
                                    myText1,};

                    UIManager.put("OptionPane.cancelButtonText", "Rejeitar");
                    UIManager.put("OptionPane.okButtonText", "Aceitar");

                    int valor = JOptionPane.showConfirmDialog(
                            ReparacaoFinalizar.this,
                            inputs,
                            "Finalizar reparacao",
                            JOptionPane.OK_CANCEL_OPTION);

                    String text = myText1.getText();

                    if (text != null) {
                        int idR = Integer.parseInt(myText1.getText());
                        if(valor == JOptionPane.OK_OPTION){
                            Estado = 3;
                            System.out.println("Reparacao finalizada");

                        } else if (valor == JOptionPane.CANCEL_OPTION) {
                            Estado = 1;
                            System.out.println("Reparacao não finalizada");
                        }

                        boolean sucesso = false;
                        try {
                            sucesso = ReparacaoController.FinalizarReparacaoF(idR, userLogado);
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
                        if (sucesso) {
                            JOptionPane.showMessageDialog(ReparacaoFinalizar.this,
                                    "Reparação ID " + idR + " finalizada com sucesso!)",
                                    "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            JOptionPane.showMessageDialog(ReparacaoFinalizar.this,
                                    "Erro: O ID não existe.",
                                    "Erro", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
            });

        }
}}
