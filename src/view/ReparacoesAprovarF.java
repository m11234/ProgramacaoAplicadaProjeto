package view;

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

public class ReparacoesAprovarF extends JPanel {
    private Utilizador userLogado;
    private ReparacaoController reparacaoController =  new ReparacaoController();

    public ReparacoesAprovarF(Utilizador u) throws SQLException {
        this.userLogado = u;

        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        JLabel title = new JLabel("Reparações por Aprovar", SwingConstants.CENTER);
        title.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(title, BorderLayout.NORTH);

        List<Reparacao> porAtivar = reparacaoController.verReparacoesPorAprovarF(userLogado);

        if (porAtivar != null) {
            String[] columnNames = {"id", "nome"};
            String[][] data = new String[porAtivar.size()][2];

            for (int i = 0; i < porAtivar.size(); i++) {
                Reparacao Ativar = porAtivar.get(i);
                data[i][0] = String.valueOf(Ativar.getIdR());
                data[i][1] = String.valueOf(Ativar.getIdEquip());
                data[i][2] = String.valueOf(Ativar.getObservacao());
            }

            JTable table = new JTable(data, columnNames);
            JScrollPane scrollPane = new JScrollPane(table);
            scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 50, 50, 50));

            JPanel Ativar = new JPanel();
            Ativar.setBackground(Color.WHITE);

            JButton ButaoAtivar = new JButton("Aceitar reparação");
            Ativar.add(ButaoAtivar, BorderLayout.SOUTH);

            add(scrollPane, BorderLayout.CENTER);
            add(Ativar, BorderLayout.NORTH);


            ButaoAtivar.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {

                    String input = JOptionPane.showInputDialog(
                            ReparacoesAprovarF.this,
                            "Introduza o ID da reparação",
                            "Aceitar reparação",
                            JOptionPane.QUESTION_MESSAGE
                    );

                    String input2 = JOptionPane.showInputDialog(
                            ReparacoesAprovarF.this,
                            "Introduza o ID da reparação",
                            "Aceitar reparação",
                            JOptionPane.QUESTION_MESSAGE
                    );

                    if (input != null) {
                        int idR = Integer.parseInt(input);
                        int Estado = Integer.parseInt(input2);

                        boolean sucesso = ReparacaoController.aceitarReparacaoF(idR, Estado, userLogado);
                        if (sucesso) {
                            JOptionPane.showMessageDialog(ReparacoesAprovarF.this,
                                    "Reparação ID " + idR + " ativada com sucesso!)",
                                    "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            JOptionPane.showMessageDialog(ReparacoesAprovarF.this,
                                    "Erro: O ID não existe.",
                                    "Erro", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
            });

    }


}}

