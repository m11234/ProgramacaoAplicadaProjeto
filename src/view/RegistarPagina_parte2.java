package view;

import model.Utilizador;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 * Classe responsável por criar a segunda janela do processo de registo de conta
 * <p>
 * Esta classe estende {@link JFrame} e atua como um ecrã intermediário de tomada de decisão,
 * permitindo ao utilizador escolher o tipo de perfil específico (Cliente ou Funcionário)
 * que deseja associar aos dados comuns introduzidos na etapa anterior.
 * </p>
 */
public class RegistarPagina_parte2 extends JFrame {
    private JButton btnCliente = new JButton(" Registar como Cliente");
    private JButton btnFuncionario = new JButton("Registar como Funcionario");

    private Utilizador userlogado;

    /**
     * Construtor da classe que inicializa a interface de seleção de perfil e configura a navegação
     * <p>
     * O construtor define as propriedades da janela, organiza os botões de seleção de perfil e
     * regista os respetivos ouvintes de eventos ({@link ActionListener}). Ao selecionar uma das opções,
     * a janela atual é fechada através do método ({@code dispose()}) e o objeto {@link Utilizador} é
     * encaminhado para o formulário final correspondente ({@link RegistarClientePagina} ou {@link RegistarFuncionarioPagina}).
     * </p>
     * @param u O objeto {@link Utilizador} que contém os dados comuns da conta capturados no ecrã
     * anterior, essencial para propagar e vincular o utilizador ao seu novo perfil específico.
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
