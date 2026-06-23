package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuCliente extends JFrame {

    private JPanel PaineldoMeio;

    public MenuCliente() {

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
    public static void main(String[] args) {
        // Testar a interface
        new MenuCliente().setVisible(true);
    }

}
