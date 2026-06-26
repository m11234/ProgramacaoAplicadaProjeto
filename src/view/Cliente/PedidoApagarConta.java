package view.Cliente;

import controller.ClienteController;
import model.Utilizador;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

/**
 * View para apagar um conta recebe o utilizador "logado" para depois verificar permissões
 */
public class PedidoApagarConta extends JPanel {
    private Utilizador userLogado;
    private ClienteController clienteController = new ClienteController();
    private JTextField ApagarContaConfirmacao = new JTextField(15);
    private JButton ApagarConta = new JButton("Apagar");

    public PedidoApagarConta(Utilizador u) {
        this.userLogado = u;

        setLayout(new BorderLayout());
        setBackground(Color.white);

        JLabel title = new JLabel("Pedido para apagar conta");
        title.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(title, BorderLayout.NORTH);

        JPanel Apagar = new JPanel();
        Apagar.setBackground(Color.white);

        Apagar.add(ApagarContaConfirmacao);
        ApagarContaConfirmacao.setToolTipText("Insira o id da sua conta para confirmar que deseja apagar");

        Apagar.add(ApagarConta);
        ApagarConta.setToolTipText("Clique aqui para apagar a sua conta");
        ApagarConta.setCursor(new Cursor(Cursor.HAND_CURSOR));

        add(Apagar, BorderLayout.CENTER);

        ApagarConta.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String apagarConta = ApagarContaConfirmacao.getText();

                if (apagarConta.isEmpty()) {
                    JOptionPane.showMessageDialog(
                            PedidoApagarConta.this,
                            "Nada",
                            "Erro",
                            JOptionPane.ERROR_MESSAGE
                    );
                } else {
                    try {
                        int contaApagar = Integer.parseInt(apagarConta);
                        int valor = JOptionPane.showConfirmDialog(
                                PedidoApagarConta.this,
                                "Deseja mesmo apagar a sua conta",
                                "Confirme",
                                JOptionPane.OK_CANCEL_OPTION
                        );
                        if (valor == JOptionPane.OK_OPTION) {
                            clienteController.ApagarContaPedido(userLogado,contaApagar);

                        }
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });

    }

}
