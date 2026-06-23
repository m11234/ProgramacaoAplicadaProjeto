package view;

import model.Utilizador;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuFuncionario extends JFrame {

    private Utilizador userLogado;
    private JPanel PaineldoMeio;

    public MenuFuncionario(Utilizador u) {
        this.userLogado = u;
        setTitle("Menu Funcionário");
        setSize(900, 600);
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
        JMenuItem ConsultarPedidos = new JMenuItem("Notificações de reparações por aprovar");

        JMenu Reparacoes = new JMenu("Reparações");
        JMenuItem PedidoApagar = new JMenuItem("Aprovar ou rejeitar reparações");
        JMenuItem CriarPedido = new JMenuItem("Finalizar reparações");


        menuConta.add(ConsultarMeusDados);
        menuConta.add(AlterarDados);
        menuConta.addSeparator();
        menuConta.add(Logout);
        menuConta.add(Sair);

        NoticacoesPedidos.add(ConsultarPedidos);

        Reparacoes.add(PedidoApagar);
        Reparacoes.add(PedidoApagar);
        Reparacoes.add(CriarPedido);

        menuBar.add(menuConta);
        menuBar.add(NoticacoesPedidos);
        menuBar.add(Reparacoes);
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
                        MenuFuncionario.this,
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
        new MenuFuncionario().setVisible(true);
    }
}
