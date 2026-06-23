package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuGestor extends JFrame {

    private JPanel PaineldoMeio;

    public MenuGestor() {
        setTitle("Menu Gestor");
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

        JMenu menuNotificacoes = new JMenu("Notificacoes");
        JMenuItem ContasNovas = new JMenuItem("Pedidos aprovacao contas");
        JMenuItem NovasRepar = new JMenuItem("Reparações novas");
        JMenuItem AlertasRepar = new JMenuItem("Alertas reparacoes");
        JMenuItem NotificacoesStock = new JMenuItem("Notificacoes stock");
        JMenuItem ApagarContNot = new JMenuItem("Pedidos de apagamento de conta");

        JMenu Reparacoes = new JMenu("Reparacoes");



        menuConta.add(ConsultarMeusDados);
        menuConta.add(AlterarDados);
        menuConta.addSeparator();
        menuConta.add(Logout);
        menuConta.add(Sair);

        menuNotificacoes.add(ContasNovas);
        menuNotificacoes.add(NovasRepar);
        menuNotificacoes.add(AlertasRepar);
        menuNotificacoes.add(NotificacoesStock);
        menuNotificacoes.add(ApagarContNot);

        menuBar.add(menuConta);
        menuBar.add(menuNotificacoes);
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
                        MenuGestor.this,
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
        new MenuGestor().setVisible(true);
    }

}
