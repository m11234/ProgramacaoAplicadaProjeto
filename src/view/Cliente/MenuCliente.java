package view.Cliente;

import controller.ReparacaoController;
import model.Utilizador;
import view.Comuns.AlterarDadosContaGeral;
import view.Comuns.ConsultarDadosContaGeral;
import view.Comuns.LoginPagina;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;


/**
 * Classe responsável por criar a interface do menu principal para os utilizadores com conta de Cliente
 * <p>
 * Esta classe estende {@link JFrame}
 * </p>
 */

public class MenuCliente extends JFrame {

    private JPanel PaineldoMeio;
    private Utilizador userLogado;
    private ReparacaoController reparacaoController = new ReparacaoController();

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

    public MenuCliente(Utilizador u) throws SQLException {

        this.userLogado = u;
        setTitle("Menu Cliente");
        setSize(900,600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); //centrar acho eu?
        setLayout(new BorderLayout());

        JMenuBar menuBar = new JMenuBar();

        JMenu menuConta = new JMenu("Conta");
        menuConta.setCursor(new Cursor(Cursor.HAND_CURSOR));
        menuConta.setToolTipText("Menu Conta");

        JMenuItem ConsultarMeusDados = new JMenuItem("Consultar Dados Conta");
        ConsultarMeusDados.setToolTipText("Consultar Dados Conta");
        ConsultarMeusDados.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JMenuItem AlterarDados = new JMenuItem("Alterar Dados Conta");
        AlterarDados.setToolTipText("Alterar Dados Conta");
        AlterarDados.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JMenuItem Logout = new JMenuItem("Logout");
        Logout.setToolTipText("Terminar sessão");
        Logout.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JMenuItem Sair = new JMenuItem("Sair");
        Sair.setToolTipText("Sair do programa");
        Sair.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JMenu NoticacoesPedidos = new JMenu("Notificações");
        NoticacoesPedidos.setToolTipText("Ver notificações");
        NoticacoesPedidos.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JMenuItem ConsultarPedidos = new JMenuItem("Reparações ativas");
        ConsultarPedidos.setToolTipText("Ver estado das reparações ativas dos seus equipamentnos");
        ConsultarPedidos.setCursor(new Cursor(Cursor.HAND_CURSOR));


        JMenu Pedidos = new JMenu("Pedidos");
        JMenuItem PedidoApagar = new JMenuItem("Iniciar pedido para apagar conta");
        PedidoApagar.setToolTipText("Iniciar pedido para apagar a sua conta");
        PedidoApagar.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JMenu Equipamentos = new JMenu("Equipamentos");
        Equipamentos.setToolTipText("Gerir os seus equipamentos e iniciar reparações");
        Equipamentos.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JMenuItem CriarEquipamento = new JMenuItem("Consultar e adicionar equipamentos");
        CriarEquipamento.setToolTipText("Gerir os seus equipamentos e iniciar reparações");
        CriarEquipamento.setCursor(new Cursor(Cursor.HAND_CURSOR));


        menuConta.add(ConsultarMeusDados);
        menuConta.add(AlterarDados);
        menuConta.addSeparator();
        menuConta.add(Logout);
        menuConta.add(Sair);

        NoticacoesPedidos.add(ConsultarPedidos);


        Pedidos.add(PedidoApagar);

        Equipamentos.add(CriarEquipamento);


        menuBar.add(menuConta);
        menuBar.add(NoticacoesPedidos);
        menuBar.add(Pedidos);
        menuBar.add(Equipamentos);
        setJMenuBar(menuBar);

        PaineldoMeio = new JPanel();
        PaineldoMeio.setBackground(Color.white);
        PaineldoMeio.setLayout(new BorderLayout());

        JLabel labelSemOpcaoEscolhida = new JLabel("Escolha uma opcao:");
        labelSemOpcaoEscolhida.setForeground(Color.gray);
        menuBar.add(labelSemOpcaoEscolhida);

        add(PaineldoMeio, BorderLayout.CENTER);

        if (reparacaoController.ReparAtivasContar(userLogado)) {
            NoticacoesPedidos.setForeground(Color.red);
            NoticacoesPedidos.setEnabled(true);
        } else {
            NoticacoesPedidos.setForeground(Color.black);
            NoticacoesPedidos.setEnabled(false);
        }

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

        PedidoApagar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PaineldoMeio.removeAll();

                PedidoApagarConta apagar = new PedidoApagarConta(userLogado);

                PaineldoMeio.add(apagar, BorderLayout.CENTER);
                PaineldoMeio.revalidate();
                PaineldoMeio.repaint();
            }
        });

        ConsultarPedidos.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                PaineldoMeio.removeAll();
                try {
                    ReparAtivas repars = new ReparAtivas(userLogado);
                    PaineldoMeio.add(repars, BorderLayout.CENTER);
                    PaineldoMeio.revalidate();
                    PaineldoMeio.repaint();
                } catch (SQLException ex) {
                }
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

        CriarEquipamento.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("carregado");
                PaineldoMeio.removeAll();
                try {
                    EquipamentosCliente equipamento = new EquipamentosCliente(userLogado);
                    PaineldoMeio.add(equipamento, BorderLayout.CENTER);
                    PaineldoMeio.revalidate();
                    PaineldoMeio.repaint();
                }  catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
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
