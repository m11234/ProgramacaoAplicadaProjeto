package view;

import model.Utilizador;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegistarPagina_parte2 extends JFrame {
    private JButton btnCliente = new JButton(" Registar como Cliente");
    private JButton btnFuncionario = new JButton("Registar como Funcionario");

    private Utilizador userlogado;

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

                //new RegistarClientePagina(userLoado).setVisible(true);
            }
        });

        btnFuncionario.addActionListener(new  ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();

                //new RegistarFuncionarioPagina(userLoado).setVisible(true);
            }
        });

    }

}
