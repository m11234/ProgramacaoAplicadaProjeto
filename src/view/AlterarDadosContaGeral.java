package view;

import controller.UtilizadorController;
import model.Utilizador;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Pattern;

import static controller.UtilizadorController.EMAIL_PATTERN;

/**
 * View do alterar dados da conta o geral em que so da para alterar os dados da propria conta
 * logo todos os utilizadores vão ter acesso e utilizar este view que é um dos poucos views
 * comuns a todos os views dos menus
 */

public class AlterarDadosContaGeral extends JPanel {

    private JTextField email = new JTextField(15);
    private JPasswordField password = new JPasswordField(15);
    private Utilizador userLogado;
    private JButton botaoGuardar = new JButton("Guardar Alterações");
    private UtilizadorController controller = new UtilizadorController();


    /**
     * metodo do view que recebe apenas o utilizador atual para passar ao controller
     * e assim confirmar permissoes
     * @param u
     */
    public AlterarDadosContaGeral(Utilizador u) {
        this.userLogado = u;

        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        JLabel title = new JLabel("Alterar Dados da Conta", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 22));
        title.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        email.setText(userLogado.getEmail());
        password.setText(userLogado.getPassword());

        JPanel painelEmail = new JPanel(new FlowLayout());
        painelEmail.setBackground(Color.WHITE);
        painelEmail.add(new JLabel("Novo Email:", SwingConstants.RIGHT));
        painelEmail.setPreferredSize(new Dimension(100,20));
        painelEmail.add(email);
        email.setToolTipText("Insira aqui o seu novo email");

        JPanel painelPass = new JPanel(new FlowLayout());
        painelPass.setBackground(Color.WHITE);
        painelPass.add(new JLabel("Nova Password:", SwingConstants.RIGHT));
        painelPass.setPreferredSize(new Dimension(100,20));
        painelPass.add(password);
        password.setToolTipText("Insira aqui a sua nova password");

        JPanel PainelPrincipal = new JPanel(new GridLayout(2, 1));
        PainelPrincipal.setBackground(Color.WHITE);
        PainelPrincipal.add(painelEmail);
        PainelPrincipal.add(painelPass);

        JPanel painelBotoes = new JPanel(new FlowLayout());
        painelBotoes.setBackground(Color.WHITE);
        painelBotoes.add(botaoGuardar);
        botaoGuardar.setCursor(new Cursor(Cursor.HAND_CURSOR));

        add(title, BorderLayout.NORTH);
        add(PainelPrincipal, BorderLayout.CENTER);
        add(painelBotoes, BorderLayout.SOUTH);

        botaoGuardar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String userEmail = email.getText();
                String userPass = new String(password.getPassword());

                if (userEmail.isEmpty() || userPass.isEmpty()) {
                    JOptionPane.showMessageDialog(
                            AlterarDadosContaGeral.this,
                            "Por favor preencha todos os campos",
                            "Erro",
                            JOptionPane.ERROR_MESSAGE
                    );
                } else if (!EMAIL_PATTERN.matcher(userEmail).matches()) {
                    JOptionPane.showMessageDialog(
                            AlterarDadosContaGeral.this,
                            "Erro: email inválido.",
                            "Erro",
                            JOptionPane.ERROR_MESSAGE
                    );
                } else {
                    Utilizador dadosNovos = new Utilizador(
                            userLogado.getNome(),
                            userLogado.getUsername(),
                            userPass,
                            userEmail
                    );
                    try {
                        boolean atualizadoComSucesso = controller.atualizarDados(userLogado,dadosNovos);

                        if (atualizadoComSucesso) {
                            JOptionPane.showMessageDialog(AlterarDadosContaGeral.this, "Dados atualizados com sucesso!");

                            userLogado.setEmail(userEmail);
                            userLogado.setPassword(userPass);
                        } else {
                            JOptionPane.showMessageDialog(
                                    AlterarDadosContaGeral.this,
                                    "Erro ao atualizar dados.",
                                    "Erro",
                                    JOptionPane.ERROR_MESSAGE
                            );
                        }
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });
    }
}