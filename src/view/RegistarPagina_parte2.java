package view;

import model.Utilizador;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 * Classe responsável por criar a segunda janela do processo de registo de conta
 * <p>
 * Esta classe estende {@link JFrame} e atua como um ecrã intermediário
 * permitindo ao utilizador escolher o tipo de conta(Cliente ou Funcionário)
 * </p>
 */
public class RegistarPagina_parte2 extends JFrame {
    private JButton btnCliente = new JButton(" Registar como Cliente");
    private JButton btnFuncionario = new JButton("Registar como Funcionario");

    private Utilizador userlogado;

    /**
     * Continua o processo de registo funciona com uma ponte entre os diferentes tipos de registo
     * possiveis apenas mantem o objeto do utilizador criado e "envia" para o menu de registo correto
     * para continuar.
     * @param u
     */
    public RegistarPagina_parte2(Utilizador u) {
        this.userlogado = u;
        System.out.println(userlogado);
        setTitle("Escolher tipo de conta:");
        setSize(300,150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel ClienteButao = new JPanel(new FlowLayout());
        setTitle("Escolher tipo de conta:");
        ClienteButao.add(btnCliente);
        btnCliente.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JPanel FuncionarioButao = new JPanel(new FlowLayout());
        FuncionarioButao.add(btnFuncionario);
        btnFuncionario.setCursor(new Cursor(Cursor.HAND_CURSOR));

        Container contentor = getContentPane();
        contentor.setLayout(new BorderLayout());

        contentor.add(ClienteButao, BorderLayout.NORTH);
        contentor.add(FuncionarioButao, BorderLayout.CENTER);

        btnCliente.addActionListener(new  ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();

                new RegistarClientePagina(userlogado).setVisible(true);
            }
        });

        btnFuncionario.addActionListener(new  ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();

                new RegistarFuncionarioPagina(userlogado).setVisible(true);
            }
        });

    }

}
