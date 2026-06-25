package view.Funcionario;

import controller.FuncionarioController;
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
 * Classe responsável por criar a interface do menu principal para os utilizadores com conta de Funcionário
 * <p>
 * Esta classe estende {@link JFrame}
 * </p>
 */
public class MenuFuncionario extends JFrame {

    private Utilizador userLogado;
    private JPanel PaineldoMeio;

    /**
     * Construtor da classe que inicializa, configura e monta a interface do menu do funcionário
     * <p>
     * O construtor define os parâmetros visuais da janela, centraliza a interface no ecrã e monta a barra de menus
     * ({@link JMenuBar}). Configura ainda os ouvintes de eventos ({@link ActionListener}) para os itens do menu,
     * permitindo que o funcionário navegue entre as opções ao limpar o contentor central ({@code PaineldoMeio}) e
     * injetar dinamicamente os novos painéis, como o ecrã de aprovação de reparações ({@link ReparacoesAprovarF}).
     * </p>
     * @param u O objeto {@link Utilizador} que representa o funcionário com sessão iniciada no momento, utilizado para
     * validar permissões e filtrar as reparações que lhe estão diretamente atribuídas.
     */
    public MenuFuncionario(Utilizador u) throws SQLException {
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

        if (FuncionarioController.contadorNotificacoes(userLogado)) {
            NoticacoesPedidos.setForeground(Color.red);
            NoticacoesPedidos.setEnabled(true);
        } else {
            NoticacoesPedidos.setForeground(Color.gray);
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

        PedidoApagar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                PaineldoMeio.removeAll();
                try {
                    ReparacoesAprovarF porAtivar = new ReparacoesAprovarF(userLogado);
                    PaineldoMeio.add(porAtivar, BorderLayout.CENTER);
                    PaineldoMeio.revalidate();
                    PaineldoMeio.repaint();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        //n é repetido pois o menu das notificacoes so funciona quando
        //existe uma e pode ser necessario entrar no menu de aceitar reparacoes
        //mesmo sem qualquer uma
        ConsultarPedidos.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                PaineldoMeio.removeAll();
                try {
                    ReparacoesAprovarF porAtivar = new ReparacoesAprovarF(userLogado);
                    PaineldoMeio.add(porAtivar, BorderLayout.CENTER);
                    PaineldoMeio.revalidate();
                    PaineldoMeio.repaint();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

    }

}
