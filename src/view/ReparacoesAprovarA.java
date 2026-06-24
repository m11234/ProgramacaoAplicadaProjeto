package view;

import controller.ReparacaoController;
import model.Reparacao;
import model.Utilizador;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;


/**
 * Classe responsável por criar a interface gráfica para aprovação de reparações por parte dos gestores/admins
 * <p>
 * Esta classe estende {@link JPanel}
 * </p>
 */
public class ReparacoesAprovarA extends JPanel {
    private Utilizador userLogado;
    private ReparacaoController reparacaoController =  new ReparacaoController();


    /**
     * Aqui o metodo apresenta a tabela ao user ao chamar o metodo do controller das reparacoes para preencher a mesma
     * ({@code reparacaoController.verReparacoesPorAprovar}
     * @param u
     * @throws SQLException
     */
    public ReparacoesAprovarA(Utilizador u) throws SQLException {
        this.userLogado = u;

        setLayout(new BorderLayout());
        setBackground(Color.white);

        JLabel title = new JLabel("Reparacoes por Aceita no Sistema", SwingConstants.CENTER);
        title.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(title, BorderLayout.NORTH);

        List<Reparacao> porAceitar = reparacaoController.verReparacoesPorAprovar(userLogado);

        if (porAceitar != null) {
            String[] columnNames = {"idReparacao","idEquipamento","obs"};
            String[][] data = new String[porAceitar.size()][3];

            for(int i = 0; i < porAceitar.size(); i++) {
                Reparacao Aceitar = porAceitar.get(i);
                data[i][0] = String.valueOf(Aceitar.getIdR());
                data[i][1] = String.valueOf(Aceitar.getIdEquip());
                data[i][2] = String.valueOf(Aceitar.getObservacao());
            }
            JTable table = new JTable(data, columnNames);
            JScrollPane scrollPane = new JScrollPane(table);
            scrollPane.setBorder(BorderFactory.createEmptyBorder());

            JPanel Aceitar = new JPanel();
            Aceitar.setBackground(Color.white);

            JButton ButaoAceitar = new JButton("Aceitar");
            Aceitar.add(ButaoAceitar, BorderLayout.SOUTH);

            add(scrollPane, BorderLayout.CENTER);
            add(Aceitar, BorderLayout.NORTH);

            /*
              Nota: Aceitar ou rejeitar reparacoes.
              Solução adaptada dos slides 16 e 17 do powerpoint 6.3 do Professor Marco Veloso.
              Consulta relizada: 24 de Junho de 2026.
             */

            ButaoAceitar.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    //id da reparacao
                    JTextField idR = new JTextField(10);
                    JLabel idRLabel = new JLabel("Insira aqui o id da reparacao para aceitar/rejeitar");
                    //id do funcionario
                    JTextField idF = new JTextField(10);
                    JLabel idFLabel = new JLabel("Insira aqui o id do funcionaria a associar com a reparacao");

                    JComponent[] inputs = new JComponent[]
                            {
                                    idR,
                                    idRLabel,
                                    idF,
                                    idFLabel};

                    UIManager.put("OptionPane.cancelButtonText", "Rejeitar");
                    UIManager.put("OptionPane.okButtonText", "Aceitar");

                    int valor = JOptionPane.showConfirmDialog(
                            ReparacoesAprovarA.this,
                            inputs,
                            "Aceitar reparacao",
                            JOptionPane.OK_CANCEL_OPTION);

                    String text = idR.getText();
                    String text2 = idF.getText();

                    //o campo do id do funcionario poder ser null pois a reparacao pode
                    //n ser aceita logo n vai ter funcionario associado
                    if(text !=null) {
                        int idRAceite = Integer.parseInt(text);
                        int idFAcceite = Integer.parseInt(text2);
                        if (valor == JOptionPane.OK_OPTION){
                            System.out.println("R aceite");
                        } else if (valor == JOptionPane.CANCEL_OPTION){
                            idFAcceite = 0;
                            System.out.println("R rejeitda");
                        }
                        try {
                            boolean sucesso = ReparacaoController.aceitarReparacao(userLogado,idRAceite,idFAcceite);
                            if (sucesso) {
                                JOptionPane.showMessageDialog(ReparacoesAprovarA.this,
                                        "Reparação ID " + idRAceite + " ativada com sucesso!)",
                                        "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                            } else {
                                JOptionPane.showMessageDialog(ReparacoesAprovarA.this,
                                        "Erro: O ID não existe.",
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
