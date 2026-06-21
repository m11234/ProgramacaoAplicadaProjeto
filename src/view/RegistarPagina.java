package view;

import controller.UtilizadorController;
import model.Utilizador;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class RegistarPagina extends JFrame {

    private JTextField nome = new JTextField(15);
    private JTextField username = new JTextField(15);
    private JPasswordField pass = new JPasswordField(15);
    private JPasswordField confirmPass = new JPasswordField(15);
    private JTextField email = new JTextField(15);
    private JFileChooser foto = new JFileChooser();
    private JButton btnProcurarFoto = new JButton("Procurar Foto");
    private JButton Prosseguir = new JButton("Prosseguir");
    private final UtilizadorController controller = new UtilizadorController();
    private Utilizador registado = null;

    public RegistarPagina() {
        setTitle("Registar conta parte 1");
        setSize(400,350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel painelNome = new JPanel(new FlowLayout());
        painelNome.add(new JLabel("Nome:", SwingConstants.CENTER));
        painelNome.add(nome);
        nome.setToolTipText("Insira aqui o seu nome");

        JPanel painelUsername = new JPanel(new FlowLayout());
        painelUsername.add(new JLabel("Username:", SwingConstants.CENTER));
        painelUsername.add(username);
        username.setToolTipText("Insira aqui o seu username");

        JPanel painelEmail = new JPanel(new FlowLayout());
        painelEmail.add(new JLabel("Email:", SwingConstants.CENTER));
        painelEmail.add(email);
        email.setToolTipText("Insira aqui o seu email");

        JPanel painelPassword = new JPanel(new FlowLayout());
        painelPassword.add(new JLabel("Password:", SwingConstants.RIGHT));
        painelPassword.add(pass);
        pass.setToolTipText("Insira aqui a sua password");

        JPanel painelConfirmarPassword = new JPanel(new FlowLayout());
        painelConfirmarPassword.add(new JLabel("Password:", SwingConstants.RIGHT));
        painelConfirmarPassword.add(confirmPass);
        confirmPass.setToolTipText("Insira de novo a sua password");

        JPanel painelFoto = new JPanel(new FlowLayout());
        painelFoto.add(new JLabel("Foto:", SwingConstants.RIGHT));
        painelFoto.add(btnProcurarFoto);
        foto.setDialogTitle("Insira aqui a sua foto");
        foto.setFileSelectionMode(JFileChooser.FILES_ONLY);
        foto.setAcceptAllFileFilterUsed(false);

        btnProcurarFoto.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (foto.showOpenDialog(RegistarPagina.this) == JFileChooser.APPROVE_OPTION) {
                    System.out.println("Current Dir.: " + foto.getCurrentDirectory());
                    System.out.println("Selected File:" + foto.getSelectedFile());
                } else {
                    System.out.println("No selection");
                }
            }
        });

        JPanel painelRegiso = new JPanel(new GridLayout(7, 1));
        painelRegiso.add( new JLabel("Registar conta dados comuns", SwingConstants.CENTER) );
        painelRegiso.add(painelNome);
        painelRegiso.add(painelUsername);
        painelRegiso.add(painelEmail);
        painelRegiso.add(painelPassword);
        painelRegiso.add(painelConfirmarPassword);
        painelRegiso.add(painelFoto);

        JPanel painelBotoes = new JPanel(new FlowLayout());
        painelBotoes.add(Prosseguir);
        Prosseguir.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnProcurarFoto.setCursor(new Cursor(Cursor.HAND_CURSOR));

        Container contentor = getContentPane();
        contentor.setLayout(new BorderLayout());

        contentor.add(painelRegiso, BorderLayout.NORTH);
        contentor.add(painelBotoes, BorderLayout.CENTER);

        Prosseguir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String nomeText = nome.getText();
                String usernameText = username.getText();
                String emailText = email.getText();
                String passText = pass.getText();
                String confirmPassText = confirmPass.getText();

                String fotoText = "";
                if (foto.getSelectedFile() != null) {
                    fotoText = foto.getSelectedFile().getAbsolutePath();
                }

                if (nomeText.isEmpty() || usernameText.isEmpty() || emailText.isEmpty() || passText.isEmpty() || confirmPassText.isEmpty() || fotoText.isEmpty()) {
                    JOptionPane.showMessageDialog(
                            RegistarPagina.this ,
                            "Por favor preencha todos os campos.",
                            "Erro",
                            JOptionPane.ERROR_MESSAGE );
                } else if (!passText.equals(confirmPassText)) {
                    JOptionPane.showMessageDialog(
                            RegistarPagina.this ,
                            "As password nao coincidem!.",
                            "Erro",
                            JOptionPane.ERROR_MESSAGE );
                } else {
                    try {
                        registado = controller.registar(nomeText, usernameText, passText, emailText, fotoText);
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    } if (registado != null) {
                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(
                                RegistarPagina.this,
                                "Dados incorretos",
                                "Erro",
                                JOptionPane.ERROR_MESSAGE
                        );
                    }
                }
            }
        });
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new RegistarPagina().setVisible(true);
            }
        });
    }
}