package view.Cliente;

import controller.EquipamentoController;
import controller.UtilizadorController;
import model.Equipamento;
import model.Utilizador;
import view.Comuns.RegistarPagina;
import view.Comuns.RegistarPagina_parte2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.time.chrono.JapaneseDate;

public class CriarEquipamento extends JPanel {
    private JTextField marca = new JTextField(15);
    private JTextField modelo = new JTextField(15);
    private JTextField sku = new JTextField(15);
    private JTextField lote = new JTextField(15);
    private JTextField email = new JTextField(15);
    private EquipamentoController equipamentoController = new EquipamentoController();
    private JButton Prosseguir = new JButton("Prosseguir");

    public CriarEquipamento(Utilizador u) {
        this.userLogado = u;
        setTitle("Registar Equipamento: ");
        setSize(700,350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel painelMarca = new JPanel(new FlowLayout());
        painelMarca.add(new JLabel("Marca:", SwingConstants.RIGHT));
        painelMarca.setPreferredSize(new Dimension(100,20));
        painelMarca.add(marca);
        marca.setToolTipText("Insira aqui a marca do seu equipamento");

        JPanel painelModelo = new JPanel(new FlowLayout());
        painelModelo.add(new JLabel("Modelo:", SwingConstants.RIGHT));
        painelModelo.setPreferredSize(new Dimension(100,20));
        painelModelo.add(modelo);
        modelo.setToolTipText("Insira o modelo do seu equipamento:");

        JPanel painelSKU = new JPanel(new FlowLayout());
        painelSKU.add(new JLabel("SKU:", SwingConstants.RIGHT));
        painelSKU.setPreferredSize(new Dimension(100,20));
        painelSKU.add(sku);
        sku.setToolTipText("Insira o SKU do seu equipamento:");

        JPanel painelLote = new JPanel(new FlowLayout());
        painelLote.add(new JLabel("SKU:", SwingConstants.RIGHT));
        painelLote.setPreferredSize(new Dimension(100,20));
        painelLote.add(lote);
        lote.setToolTipText("Insira o lote do seu equipamento:");

        JPanel painelEmail = new JPanel(new FlowLayout());
        painelEmail.add(new JLabel("Email:", SwingConstants.RIGHT));
        painelEmail.add(email);
        email.setToolTipText("Insira aqui o seu email");





        JPanel painelRegiso = new JPanel(new GridLayout(7, 1));
        painelRegiso.add( new JLabel("Registar equipamento (dados)", SwingConstants.RIGHT) );
        painelRegiso.add(painelMarca);
        painelRegiso.add(painelModelo);
        painelRegiso.add(painelSKU);
        painelRegiso.add(painelLote);
        painelRegiso.add(painelEmail);

        JPanel painelBotoes = new JPanel(new FlowLayout());
        painelBotoes.add(Prosseguir);
        Prosseguir.setCursor(new Cursor(Cursor.HAND_CURSOR));

        Container contentor = getContentPane();
        contentor.setLayout(new BorderLayout());

        contentor.add(painelRegiso, BorderLayout.NORTH);
        contentor.add(painelBotoes, BorderLayout.CENTER);

        Prosseguir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String marcaText = marca.getText();
                String modeloText = modelo.getText();
                String skuText = sku.getText();
                String loteText = lote.getText();
                String emailText = email.getText();


                if (marcaText.isEmpty() || modeloText.isEmpty() || skuText.isEmpty() || loteText.isEmpty() || emailText.isEmpty()) {
                    JOptionPane.showMessageDialog(
                            CriarEquipamento.this ,
                            "Por favor preencha todos os campos.",
                            "Erro",
                            JOptionPane.ERROR_MESSAGE );
                } else {
                    try {
                        Equipamento equipamentoRegistado = EquipamentoController.criarEquipamento(marcaText, modeloText, skuText, loteText, emailText);
                        if (equipamentoRegistado != null) {
                            JOptionPane.showMessageDialog(
                                    CriarEquipamento.this,
                                    "Equipamento registado com sucesso!",
                                    "Sucesso",
                                    JOptionPane.INFORMATION_MESSAGE
                            );
                            dispose();
                        } else {
                            JOptionPane.showMessageDialog(
                                    CriarEquipamento.this,
                                    "Não foi possível registar o equipamento, dados incorretos.",
                                    "Erro",
                                    JOptionPane.ERROR_MESSAGE
                            );
                        }
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(
                                CriarEquipamento.this,
                                "Erro na base de dados: " + ex.getMessage(),
                                "Erro",
                                JOptionPane.ERROR_MESSAGE
                        );
                        throw new RuntimeException(ex);
                    }
                }
            }
        });
    }
}
