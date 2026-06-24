package view;

import controller.ClienteController;
import model.Cliente;
import model.Utilizador;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

/**
 * Classe responsável por criar a interface gráfica para a conclusão do registo com perfil de Cliente
 * <p>
 * Esta classe estende {@link JFrame} e disponibiliza um formulário detalhado que recolhe dados específicos
 * do cliente, tais como NIF, número de telemóvel, morada, setor de atividade e escalão, associando-os
 * à conta de utilizador criada previamente.
 * </p>
 */
public class RegistarClientePagina extends JFrame {

    private JTextField nif = new JTextField(15);
    private JTextField telemovel = new JTextField(15);
    private JTextField morada = new JTextField(15);
    private JTextField sector = new JTextField(15);
    private JTextField escalao = new JTextField(15);
    private Utilizador userLogado;
    private JButton botaoVoltar = new JButton("Voltar");
    private JButton botaoRegistar = new JButton("Registar");
    private ClienteController clienteController = new ClienteController();
    private Cliente clienteCriado = null;


    /**
     * Construtor da classe que inicializa, configura e monta o formulário de registo do cliente
     * <p>
     * O construtor cria a disposição dos painéis e campos de texto com recurso ao {@link GridLayout} e
     * associa os respetivos ouvintes de eventos ({@link ActionListener}) aos botões. O botão ({@code botaoVoltar})
     * permite cancelar a operação e regressar ao ecrã anterior de registo, enquanto o botão ({@code botaoRegistar})
     * valida a presença de campos vazios e invoca o método ({@code clienteController.criarCliente}) para submeter
     * a informação de perfil para a base de dados.
     * </p>
     * @param u O objeto {@link Utilizador} que representa a conta de utilizador genérica acabada de criar, utilizada
     * como chave de associação para a criação do registo específico na tabela de clientes.
     */
    public RegistarClientePagina(Utilizador u) {
        this.userLogado = u;
        System.out.println(userLogado);
        setTitle("Complete aqui o seu registo como cliente");
        setSize(700,350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel painelNif = new JPanel(new FlowLayout());
        painelNif.add(new JLabel("NIF:", SwingConstants.RIGHT));
        painelNif.setPreferredSize(new Dimension(100,20));
        painelNif.add(nif);
        nif.setToolTipText("Insira aqui o seu nif");

        JPanel painelTele = new JPanel(new FlowLayout());
        painelTele.add(new JLabel("Telemovel:", SwingConstants.RIGHT));
        painelTele.setPreferredSize(new Dimension(100,20));
        painelTele.add(telemovel);
        telemovel.setToolTipText("Insira aqui o seu numero de telemovel");

        JPanel painelMorada = new JPanel(new FlowLayout());
        painelMorada.add(new JLabel("Morada:", SwingConstants.RIGHT));
        painelMorada.setPreferredSize(new Dimension(100,20));
        painelMorada.add(morada);
        morada.setToolTipText("Insira aqui a sua morada");

        JPanel painelSector = new JPanel(new FlowLayout());
        painelSector.add(new JLabel("Sector:", SwingConstants.RIGHT));
        painelSector.setPreferredSize(new Dimension(100,20));
        painelSector.add(sector);
        sector.setToolTipText("Insira aqui o seu sector");

        JPanel painelEscalao = new JPanel(new FlowLayout());
        painelEscalao.add(new JLabel("Escalao:", SwingConstants.RIGHT));
        painelEscalao.setPreferredSize(new Dimension(100,20));
        painelEscalao.add(escalao);
        escalao.setToolTipText("Insira aqui o seu escalao");

        JPanel PainelPrincipal = new JPanel(new GridLayout(5, 1));
        PainelPrincipal.add(painelNif);
        PainelPrincipal.add(painelTele);
        PainelPrincipal.add(painelMorada);
        PainelPrincipal.add(painelSector);
        PainelPrincipal.add(painelEscalao);

        JPanel painelBotoes = new JPanel(new FlowLayout());
        painelBotoes.add(botaoVoltar);
        botaoVoltar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        painelBotoes.add(botaoRegistar);
        botaoRegistar.setCursor(new Cursor(Cursor.HAND_CURSOR));

        Container contentor = getContentPane();
        contentor.setLayout(new BorderLayout());

        contentor.add(PainelPrincipal, BorderLayout.NORTH);
        contentor.add(new JPanel(), BorderLayout.CENTER);
        contentor.add(painelBotoes, BorderLayout.SOUTH);


        botaoVoltar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int valor = JOptionPane.showConfirmDialog(
                        RegistarClientePagina.this,
                        "Deseja voltar atras todos os dados inseridos serao perdidos",
                        "Voltar atras",
                        JOptionPane.OK_CANCEL_OPTION
                );

                if (valor == JOptionPane.OK_OPTION) {
                    dispose();

                    RegistarPagina paginaRegisto = new RegistarPagina();
                    paginaRegisto.setVisible(true);
                }

            }
        });
        botaoRegistar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String userNif = nif.getText();
                String userTelemovel = telemovel.getText();
                String userMorada = morada.getText();
                String userSector = sector.getText();
                String userEscalao = escalao.getText();

                if (userNif.isEmpty() || userTelemovel.isEmpty() || userMorada.isEmpty() || userSector.isEmpty() || userEscalao.isEmpty()) {
                    JOptionPane.showMessageDialog(
                            RegistarClientePagina.this,
                            "Por favor preencha todos os campos",
                            "Erro",
                            JOptionPane.ERROR_MESSAGE
                    );
                } else  {
                    try {
                        clienteCriado = clienteController.criarCliente(userNif,userTelemovel,userMorada,userSector,userEscalao,userLogado);
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    } if (clienteCriado != null) {
                        dispose();
                        JOptionPane.showMessageDialog(RegistarClientePagina.this, "Cliente criado com sucesso podera fazer login quando a sua conta for ativada");
                    } else {
                        JOptionPane.showMessageDialog(
                                RegistarClientePagina.this,
                                "Dados incorretos",
                                "Erro",
                                JOptionPane.ERROR_MESSAGE
                        );
                    }
                }
            }
        });


    }





}
