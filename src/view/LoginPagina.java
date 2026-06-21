import controller.UtilizadorController;
import model.Utilizador;
import view.RegistarPagina;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class LoginPagina extends JFrame {

        private JTextField username = new JTextField(15);
        private JPasswordField pass = new JPasswordField(15);
        private JButton loginButao = new JButton("Login");
        private JButton IrRegistoButao = new JButton("Registar Conta");

        private final UtilizadorController controller = new UtilizadorController();
        private Utilizador userLogado = null;

        public LoginPagina()  {

                setTitle("Login");
                setSize(400, 300);
                setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                JPanel painelUsername = new JPanel(new FlowLayout());
                painelUsername.add(new JLabel("Username:", SwingConstants.RIGHT));
                painelUsername.add(username);
                username.setToolTipText("Insira aqui o seu username");

                JPanel painelPassword = new JPanel(new FlowLayout());
                painelPassword.add(new JLabel("Password:", SwingConstants.RIGHT));
                painelPassword.add(pass);
                pass.setToolTipText("Insira aqui a sua password");

                JPanel PainelIrRegistoButao = new JPanel(new FlowLayout());
                PainelIrRegistoButao.add(IrRegistoButao);
                IrRegistoButao.setCursor(new Cursor(Cursor.HAND_CURSOR));

                JPanel painelLoginPrincipal = new JPanel(new GridLayout(4, 1));
                painelLoginPrincipal.add(new JLabel("Login", SwingConstants.CENTER));
                painelLoginPrincipal.add(painelUsername);
                painelLoginPrincipal.add(painelPassword);
                painelLoginPrincipal.add(PainelIrRegistoButao);

                JPanel painelBotoes = new JPanel(new FlowLayout());
                painelBotoes.add(loginButao);
                loginButao.setCursor(new Cursor(Cursor.HAND_CURSOR));

                Container contentor = getContentPane();
                contentor.setLayout(new BorderLayout());

                contentor.add(painelLoginPrincipal, BorderLayout.NORTH);
                contentor.add(new JPanel(), BorderLayout.CENTER);
                contentor.add(painelBotoes, BorderLayout.SOUTH);

                loginButao.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                                String userText = username.getText();
                                String passText = pass.getText();

                                if (userText.isEmpty() || passText.isEmpty()) {
                                        JOptionPane.showMessageDialog(
                                                LoginPagina.this ,
                                                "Por favor preencha todos os campos.",
                                                "Erro",
                                                JOptionPane.ERROR_MESSAGE );
                                } else {
                                        try {
                                                userLogado = controller.Login(userText,passText);
                                        } catch (SQLException ex) {
                                                throw new RuntimeException(ex);
                                        }

                                        if (userLogado != null) {
                                                dispose();
                                        } else {
                                                JOptionPane.showMessageDialog(
                                                        LoginPagina.this,
                                                        "Username ou password incorretos",
                                                        "Erro",
                                                        JOptionPane.ERROR_MESSAGE
                                                );
                                        }
                                }
                        }
                });

                IrRegistoButao.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                                int valor = JOptionPane.showConfirmDialog(
                                        LoginPagina.this,
                                        "Deseja ir para o menu para registar uma conta?",
                                        "Confirmacao de Registo",
                                        JOptionPane.OK_CANCEL_OPTION
                                );

                                if (valor == JOptionPane.OK_OPTION) {
                                        dispose();

                                        RegistarPagina paginaRegisto = new RegistarPagina();
                                        paginaRegisto.setVisible(true);
                                }
                        }
                });
        }

        public static void main(String[] args) {
                SwingUtilities.invokeLater(new Runnable() {
                        public void run() {
                                new LoginPagina().setVisible(true);
                        }
                });
        }
}