package view;

import model.Utilizador;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;



/**
 * Classe responsável por criar a interface do menu principal para os utilizadores com conta de Cliente
 * <p>
 * Esta classe estende {@link JFrame}
 * </p>
 */

public class MenuCliente extends JFrame {

    private JPanel PaineldoMeio;
    private Utilizador userLogado;


    /**
     * Construtor da classe que inicializa, configura e monta a interface do menu do cliente
     * <p>
     * O construtor define as dimensões e propriedades da janela, e constrói
     * a barra de menus ({@link JMenuBar}) com as respetivas opções ({@link JMenuItem}). Adicionalmente,
     * configura os listeners de ({@link ActionListener}) para permitir a navegação entre ecrãs (como {@link ConsultarDadosContaGeral} ou {@link AlterarDadosContaGeral}) no contentor central.
     * </p>
     * @param u O objeto {@link Utilizador} que representa o cliente com sessão iniciada no momento, utilizado para
     * propagar as informações da conta atualmente "logada" autenficada
     */

    public MenuCliente(Utilizador u) {

        this.userLogado = u;
        setTitle("Menu Cliente");
        setSize(900,600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); //centrar acho eu?
        setLayout(new BorderLayout());

        JMenuBar menuBar = new JMenuBar();

        JMenu menuConta = new JMenu("Conta");
        JMenuItem ConsultarMeusDados = new JMenuItem("Consultar Dados Conta");
        JMenuItem AlterarDados = new JMenuItem("Alterar Dados Conta");
        JMenuItem Logout = new JMenuItem("Logout");
        JMenuItem Sair = new JMenuItem("Sair");

        JMenu NoticacoesPedidos = new JMenu("Notificações");
        JMenuItem ConsultarPedidos = new JMenuItem("Notificações pedidos de reparação");

        JMenu Pedidos = new JMenu("Pedidos");
        JMenuItem PedidoApagar = new JMenuItem("Iniciar pedido para apagar conta");
        JMenuItem NovasRepar = new JMenuItem("Criar pedido reparacao");
        JMenuItem CriarPedido = new JMenuItem("Alterar pedido reparacao");
        JMenuItem CriarEquipamento = new JMenuItem("Criar equipamento");


        menuConta.add(ConsultarMeusDados);
        menuConta.add(AlterarDados);
        menuConta.addSeparator();
        menuConta.add(Logout);
        menuConta.add(Sair);

        NoticacoesPedidos.add(ConsultarPedidos);

        Pedidos.add(PedidoApagar);
        Pedidos.add(NovasRepar);
        Pedidos.add(CriarPedido);
        Pedidos.add(CriarEquipamento);


        menuBar.add(menuConta);
        menuBar.add(NoticacoesPedidos);
        menuBar.add(Pedidos);
        setJMenuBar(menuBar);

        PaineldoMeio = new JPanel();
        PaineldoMeio.setBackground(Color.white);
        PaineldoMeio.setLayout(new BorderLayout());

        JLabel labelSemOpcaoEscolhida = new JLabel("Escolha uma opcao:");
        labelSemOpcaoEscolhida.setForeground(Color.gray);
        menuBar.add(labelSemOpcaoEscolhida);

        add(PaineldoMeio, BorderLayout.CENTER);

        Logout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new LoginPagina().setVisible(true);
            }
        });

        ConsultarMeusDados.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                PaineldoMeio.removeAll(); //apaga se tiver la alguma coisa

                ConsultarDadosContaGeral dados = new ConsultarDadosContaGeral(userLogado);

                PaineldoMeio.add(dados, BorderLayout.CENTER);
                PaineldoMeio.revalidate();
                PaineldoMeio.repaint();
            }
        });

        AlterarDados.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                PaineldoMeio.removeAll();

                AlterarDadosContaGeral alterar = new AlterarDadosContaGeral(userLogado);

                PaineldoMeio.add(alterar, BorderLayout.CENTER);
                PaineldoMeio.revalidate();
                PaineldoMeio.repaint();
            }
        });

        Sair.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int valor = JOptionPane.showConfirmDialog(
                        MenuCliente.this,
                        "Deseja sair da aplicacao?",
                        "Fechar programa",
                        JOptionPane.OK_CANCEL_OPTION
                );
                if (valor == JOptionPane.OK_OPTION) {
                    dispose();
                }
            }
        });



    }

}
