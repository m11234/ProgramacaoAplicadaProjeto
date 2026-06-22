package view;

import controller.FuncionarioController;
import model.Funcionario;
import model.Utilizador;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegistarFuncionarioPagina extends JFrame {

    //String nif,String telemovel, String morada,String nivelE, Utilizador logado
    private JTextField nif = new JTextField(15);
    private JTextField telemovel = new JTextField(15);
    private JTextField morada = new JTextField(15);
    private JTextField nivelE = new JTextField(15);
    private JButton registar = new JButton("Registar");
    private JButton voltar = new JButton("Voltar");
    private Utilizador userLogado;
    private FuncionarioController funcionarioController = new FuncionarioController();
    private Funcionario funcionarioCriado = null;

    public RegistarFuncionarioPagina(Utilizador u) {
        this.userLogado = u;
        System.out.println(userLogado);
        setTitle("Registar Funcionario");
        setSize(700,300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel painelNif = new JPanel(new FlowLayout());
        painelNif.add(new JLabel("NIF:", SwingConstants.RIGHT));
        painelNif.setPreferredSize(new Dimension(100,20));
        painelNif.add(nif);
        nif.setToolTipText("Insira aqui o seu nif");

        JPanel painelTele = new JPanel(new FlowLayout());
        painelTele.add(new JLabel("Telemovel:", SwingConstants.RIGHT));
        painelTele.setPreferredSize(new Dimension(100,20));
        painelTele.add(telemovel);
        telemovel.setToolTipText("Insira aqui o seu numero de telemovel");

        JPanel painelMorada = new JPanel(new FlowLayout());
        painelMorada.add(new JLabel("Morada:", SwingConstants.RIGHT));
        painelMorada.setPreferredSize(new Dimension(100,20));
        painelMorada.add(morada);
        morada.setToolTipText("Insira aqui a sua morada");

        JPanel painelNivelE = new JPanel(new FlowLayout());
        painelNivelE.add(new JLabel("Nivel:", SwingConstants.RIGHT));
        painelNivelE.setPreferredSize(new Dimension(100,20));
        painelNivelE.add(nivelE);
        nivelE.setToolTipText("Insira aqui o seu nivel");

        JPanel PainelPrincipal = new JPanel(new GridLayout(4,1));
        PainelPrincipal.add(painelNif);
        PainelPrincipal.add(painelTele);
        PainelPrincipal.add(painelMorada);
        PainelPrincipal.add(painelNivelE);

        JPanel painelBotoes = new JPanel(new FlowLayout());
        painelBotoes.add(voltar);
        voltar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        painelBotoes.add(registar);
        registar.setCursor(new Cursor(Cursor.HAND_CURSOR));

        Container contentor = getContentPane();
        contentor.setLayout(new BorderLayout());

        contentor.add(PainelPrincipal, BorderLayout.NORTH);
        contentor.add(new JPanel(), BorderLayout.CENTER);
        contentor.add(painelBotoes, BorderLayout.SOUTH);

        voltar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int valor = JOptionPane.showConfirmDialog(
                        RegistarFuncionarioPagina.this,
                        "Deseja voltar atras todos os dados inseridos serao perdidos",
                        "Voltar atras",
                        JOptionPane.OK_CANCEL_OPTION
                );
                if (valor == JOptionPane.OK_OPTION) {
                    dispose();

                    RegistarPagina pagina = new RegistarPagina();
                    pagina.setVisible(true);
                }
            }
        });

        registar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String userNif = nif.getText();
                String userTelemovel = telemovel.getText();
                String userMorada = morada.getText();
                String userNivel = nivelE.getText();

                if (userNif.isEmpty() || userTelemovel.isEmpty() || userMorada.isEmpty() || userNivel.isEmpty() ) {
                    JOptionPane.showMessageDialog(
                            RegistarFuncionarioPagina.this,
                            "Por favor preencha todos os campos",
                            "Erro",
                            JOptionPane.ERROR_MESSAGE
                    );
                } else {
                    try {
                        funcionarioCriado = funcionarioController.criarFuncionario(userNif,userTelemovel,userMorada,userNivel,userLogado);
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    } if (funcionarioCriado != null) {
                        dispose();
                        JOptionPane.showMessageDialog(RegistarFuncionarioPagina.this, "Funcionario criado com sucesso podera fazer login quando a sua conta for ativada");
                    } else {
                        JOptionPane.showMessageDialog(
                                RegistarFuncionarioPagina.this,
                                "Dados Incorretos",
                                "Erro",
                                JOptionPane.ERROR_MESSAGE
                        );
                    }
                }

            }
        });





    }
}
