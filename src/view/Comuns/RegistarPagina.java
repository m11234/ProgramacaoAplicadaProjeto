package view.Comuns;

import controller.UtilizadorController;
import model.Utilizador;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

/**
 * Classe responsável por criar a primeira interface do processo de registo de novas contas de utilizador
 * <p>
 * Esta classe estende {@link JFrame} e disponibiliza um formulário inicial para a recolha de dados comuns
 * a todos os tipos de conta, incluindo nome, username, email, palavra-passe e uma foto
 * de perfil
 * </p>
 */
public class RegistarPagina extends JFrame {

    private JTextField nome = new JTextField(15);
    private JTextField username = new JTextField(15);
    private JPasswordField pass = new JPasswordField(15);
    private JPasswordField confirmPass = new JPasswordField(15);
    private JTextField email = new JTextField(15);
    private JFileChooser foto = new JFileChooser();
    private JButton btnProcurarFoto = new JButton("Procurar Foto");
    private JButton Prosseguir = new JButton("Prosseguir");
    private  UtilizadorController controller = new UtilizadorController();
    private Utilizador registado = null;

    /**
     * Construtor da classe que inicializa, configura e monta o formulário inicial de registo de conta
     * <p>
     * O construtor define as propriedades geométricas da janela e estrutura os componentes utilizando layouts
     * combinados. Configura um ouvinte de eventos ({@link ActionListener}) no botão ({@code btnProcurarFoto})
     * para abrir a caixa de diálogo nativa do {@link JFileChooser}.
     * Configura também o botão ({@code Prosseguir}) para validar o preenchimento integral dos campos, verificar
     * a correspondência das palavras-passe e invocar o método ({@code controller.registar}), reencaminhando o
     * utilizador para o ecrã subsequente ({@code RegistarPagina_parte2}).
     * </p>
     */
    public RegistarPagina() {
        setTitle("Registar conta parte 1");
        setSize(700,350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel painelNome = new JPanel(new FlowLayout());
        painelNome.add(new JLabel("Nome:", SwingConstants.RIGHT));
        painelNome.setPreferredSize(new Dimension(100,20));
        painelNome.add(nome);
        nome.setToolTipText("Insira aqui o seu nome");

        JPanel painelUsername = new JPanel(new FlowLayout());
        painelUsername.add(new JLabel("Username:", SwingConstants.RIGHT));
        painelUsername.setPreferredSize(new Dimension(100,20));
        painelUsername.add(username);
        username.setToolTipText("Insira aqui o seu username");

        JPanel painelEmail = new JPanel(new FlowLayout());
        painelEmail.add(new JLabel("Email:", SwingConstants.RIGHT));
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
        painelRegiso.add( new JLabel("Registar conta dados comuns", SwingConstants.RIGHT) );
        painelRegiso.add(painelNome);
        painelRegiso.add(painelUsername);
        painelRegiso.add(painelEmail);
        painelRegiso.add(painelPassword);
        painelRegiso.add(painelConfirmarPassword);
        painelRegiso.add(painelFoto);

        JPanel painelBotoes = new JPanel(new FlowLayout());
        painelBotoes.add(Prosseguir);
        Prosseguir.setCursor(new Cursor(Cursor.HAND_CURSOR));
        Prosseguir.setToolTipText("Prosseguir para a proxima fase do registo");

        btnProcurarFoto.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnProcurarFoto.setToolTipText("Procurar foto");

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
                        RegistarPagina_parte2 parte2 = new RegistarPagina_parte2(registado);
                        parte2.setVisible(true);
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
}