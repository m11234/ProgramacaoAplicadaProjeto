package view;

import controller.AdminController;
import model.Utilizador;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

/**
 * Classe responsável por criar a interface do menu principal para os utilizadores com perfil de Gestor
 * <p>
 * Esta classe estende {@link JFrame} e centraliza todas as operações administrativas avançadas do sistema.
 * Disponibiliza uma barra de menus complexa estruturada para a gestão global de contas de utilizadores,
 * consulta de notificações de stock, monitorização de alertas e administração de reparações e equipamentos.
 * </p>
 */
public class MenuGestor extends JFrame {

    private JPanel PaineldoMeio;
    private Utilizador userLogado;
    private AdminController adminController = new AdminController();

    /**
     * Construtor da classe que inicializa, configura e monta a interface do menu do funcionário
     * <p>
     * O construtor define os parâmetros visuais da janela, centraliza a interface no ecrã e monta a barra de menus
     * ({@link JMenuBar}). Configura ainda os ouvintes de eventos ({@link ActionListener}) para os itens do menu,
     * permitindo que o funcionário navegue entre as opções ao limpar o contentor central ({@code PaineldoMeio}) e
     * injetar dinamicamente os novos painéis, como o ecrã de aprovação de reparações ({@link ReparacoesAprovarA}).
     * </p>
     * @param u O objeto {@link Utilizador} que representa o funcionário com sessão iniciada no momento, utilizado para
     * validar permissões e filtrar as reparações que lhe estão diretamente atribuídas.
     */
    public MenuGestor(Utilizador u) throws SQLException {
        this.userLogado = u;
        setTitle("Menu Gestor");
        setSize(900,600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); //centrar acho eu?
        setLayout(new BorderLayout());

        JMenuBar menuBar = new JMenuBar();

        //feito a parte geral dos menus
        JMenu menuConta = new JMenu("Conta");
        JMenuItem ConsultarMeusDados = new JMenuItem("Consultar Dados Conta");
        JMenuItem AlterarDados = new JMenuItem("Alterar Dados Conta");
        JMenuItem Logout = new JMenuItem("Logout");
        JMenuItem Sair = new JMenuItem("Sair");


        JMenu menuNotificacoes = new JMenu("Notificacoes");
        JMenuItem ContasNovas = new JMenuItem("Pedidos aprovacao contas");
        JMenuItem NovasRepar = new JMenuItem("Reparações novas");
        JMenuItem NotificacoesStock = new JMenuItem("Notificacoes stock");
        JMenuItem ApagarContNot = new JMenuItem("Pedidos de apagamento de conta");

        JMenu Reparacoes = new JMenu("Reparacoes");
        JMenuItem AprovRepar = new JMenuItem("Aprovar reparacoes");
        JMenuItem AlertasRepar = new JMenuItem("Alertas reparacoes");
        JMenuItem PesquisarReparID = new JMenuItem("Pesquisar reparacao pelo ID");

        JMenu CoisasGestor = new JMenu("Coisas de Gestor");
        JMenuItem UtilizadoresNum = new JMenuItem("Ver numero de utilizadores");
        JMenuItem UtilizadoresLs = new JMenuItem("Ver lista de utilizadores");
        JMenuItem UtilizadoresPesq = new JMenuItem("Pesquisar por um utilizador");
        JMenuItem ConsultarDadosU = new JMenuItem("Consultar dados de outra conta");
        JMenuItem AlterarDadosU = new JMenuItem("Atualizar dados de outra conta");
        JMenuItem AtivarContaU = new JMenuItem("Ativar contas");
        JMenuItem ApagarContaU = new JMenuItem("Apagar contas");

        JMenu Equipamentos = new JMenu("Equipamentos");
        JMenuItem PesquisarEquipamento = new JMenuItem("Pesquisar");


        menuConta.add(ConsultarMeusDados);
        menuConta.add(AlterarDados);
        menuConta.addSeparator();
        menuConta.add(Logout);
        menuConta.add(Sair);

        menuNotificacoes.add(ContasNovas);
        menuNotificacoes.add(NovasRepar);
        menuNotificacoes.add(ApagarContNot);

        Reparacoes.add(AprovRepar);
        Reparacoes.add(AlertasRepar);
        Reparacoes.add(PesquisarReparID);

        CoisasGestor.add(UtilizadoresNum);
        CoisasGestor.add(UtilizadoresLs);
        CoisasGestor.add(UtilizadoresPesq);
        CoisasGestor.add(ConsultarDadosU);
        CoisasGestor.add(AlterarDadosU);
        CoisasGestor.add(AtivarContaU);
        CoisasGestor.add(ApagarContaU);

        Equipamentos.add(PesquisarEquipamento);

        menuBar.add(menuConta);
        menuBar.add(menuNotificacoes);
        menuBar.add(Reparacoes);
        menuBar.add(CoisasGestor);
        menuBar.add(Equipamentos);
        setJMenuBar(menuBar);


        PaineldoMeio = new JPanel();
        PaineldoMeio.setBackground(Color.white);
        PaineldoMeio.setLayout(new BorderLayout());

        JLabel labelSemOpcaoEscolhida = new JLabel("Escolha uma opcao:");
        labelSemOpcaoEscolhida.setForeground(Color.gray);
        menuBar.add(labelSemOpcaoEscolhida);

        add(PaineldoMeio, BorderLayout.CENTER);

        if (adminController.contadorNotificacoes(userLogado)) {
            menuNotificacoes.setForeground(Color.red);
            menuNotificacoes.setEnabled(true);
        } else {
            menuNotificacoes.setForeground(Color.black);
            menuNotificacoes.setEnabled(false);
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

        AlterarDados.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                PaineldoMeio.removeAll();

                AlterarDadosContaGeral alterar = new AlterarDadosContaGeral(userLogado);

                PaineldoMeio.add(alterar, BorderLayout.CENTER);
                PaineldoMeio.revalidate();
                PaineldoMeio.repaint();
            }
        });

        ContasNovas.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                PaineldoMeio.removeAll();
                try {
                    AtivarContas porAtivar = new AtivarContas(userLogado);
                    PaineldoMeio.add(porAtivar, BorderLayout.CENTER);
                    PaineldoMeio.revalidate();
                    PaineldoMeio.repaint();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        ApagarContNot.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                PaineldoMeio.removeAll();
                try {
                    ApagarContas porApagar = new ApagarContas(userLogado);
                    PaineldoMeio.add(porApagar, BorderLayout.CENTER);
                    PaineldoMeio.revalidate();
                    PaineldoMeio.repaint();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        NovasRepar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                PaineldoMeio.removeAll();
                try {
                    ReparacoesAprovarA porAceitar = new ReparacoesAprovarA(userLogado);
                    PaineldoMeio.add(porAceitar, BorderLayout.CENTER);
                    PaineldoMeio.revalidate();
                    PaineldoMeio.repaint();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        AprovRepar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                PaineldoMeio.removeAll();
                try {
                    ReparacoesAprovarA porAceitar = new ReparacoesAprovarA(userLogado);
                    PaineldoMeio.add(porAceitar, BorderLayout.CENTER);
                    PaineldoMeio.revalidate();
                    PaineldoMeio.repaint();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        AlertasRepar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                PaineldoMeio.removeAll();
                try {
                    AlertasReparacoes m10Dias = new AlertasReparacoes(userLogado);
                    PaineldoMeio.add(m10Dias, BorderLayout.CENTER);
                    PaineldoMeio.revalidate();
                    PaineldoMeio.repaint();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        PesquisarReparID.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                PaineldoMeio.removeAll();
                ReparacaoPesquisa pq = new ReparacaoPesquisa(userLogado);
                PaineldoMeio.add(pq, BorderLayout.CENTER);
                PaineldoMeio.revalidate();
                PaineldoMeio.repaint();
            }
        });

        UtilizadoresNum.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                PaineldoMeio.removeAll();
                try {
                    NumeroUtilizadores num = new NumeroUtilizadores(userLogado);
                    PaineldoMeio.add(num, BorderLayout.CENTER);
                    PaineldoMeio.revalidate();
                    PaineldoMeio.repaint();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        PesquisarEquipamento.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                PaineldoMeio.removeAll();
                EquipamentoPesquisa pq =  new EquipamentoPesquisa(userLogado);
                PaineldoMeio.add(pq, BorderLayout.CENTER);
                PaineldoMeio.revalidate();
                PaineldoMeio.repaint();
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
}
