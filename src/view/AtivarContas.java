package view;

import controller.AdminController;
import model.Utilizador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;


/**
 * Classe responsável por criar a interface gráfica para a ativação de contas de utilizadores
 * <p>
 * Esta classe estende {@link JPanel} e disponibiliza aos administradores uma tabela contendo os utilizadores
 * cujos registos se encontram pendentes de ativação e permite tambem ativar as mesmas
 * </p>
 */
public class AtivarContas extends JPanel {
    private Utilizador userLogado;
    private AdminController adminController =  new AdminController();


    /**
     * Construtor da classe que inicializa a interface gráfica e carrega a lista das contas pendentes de ativação
     * <p>
     * O construtor configura o layout do painel, exibe os dados numa {@link JTable} obtidos através do método
     * ({@code adminController.verContasPorAtivar}) e chama um listener para ({@link ActionListener})  botão.
     * Ao acionar o botão, é solicitada uma caixa de introdução de dados para capturar o ID do utilizador e,
     * posteriormente, invoca o método ({@code adminController.ativarConta}) para efetivar a ativação na base de dados.
     * </p>
     * @param u O objeto {@link Utilizador} que representa o administrador com sessão iniciada, utilizado para
     * verificar e confirmar os privilégios administrativos necessários para listar e ativar contas.
     * @throws SQLException Se ocorrer alguma falha de comunicação ou erro na consulta com a base de dados
     * ao invocar o método ({@code adminController.verContasPorAtivar}).
     */
    public AtivarContas(Utilizador u) throws SQLException {
        this.userLogado = u;
        //so precisa de ir o userLogado para o controller confirmar mesmo que é admin

        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        JLabel title = new JLabel("Contas por Ativar", SwingConstants.CENTER);
        title.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(title, BorderLayout.NORTH);

        List<Utilizador> porAtivar = adminController.verContasPorAtivar(userLogado);

        if (porAtivar != null) {
            String[] columnNames = {"id", "nome"};
            String[][] data = new String[porAtivar.size()][2];

            for (int i = 0; i < porAtivar.size(); i++) {
                Utilizador Ativar = porAtivar.get(i);
                data[i][0] = String.valueOf(Ativar.getId());
                data[i][1] = String.valueOf(Ativar.getUsername());
            }

            JTable table = new JTable(data, columnNames);
            JScrollPane scrollPane = new JScrollPane(table);
            scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 50, 50, 50));

            JPanel Ativar = new JPanel();
            Ativar.setBackground(Color.WHITE);

            JButton ButaoAtivar = new JButton("Ativar Conta");
            Ativar.add(ButaoAtivar, BorderLayout.SOUTH);

            add(scrollPane, BorderLayout.CENTER);
            add(Ativar, BorderLayout.NORTH);
            //ativar contas


            ButaoAtivar.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {

                    String input = JOptionPane.showInputDialog(
                            AtivarContas.this,
                            "Introduza o ID da conta a ativar",
                            "Ativar Conta",
                            JOptionPane.QUESTION_MESSAGE
                    );

                    if (input != null) {
                        int idContaAtivar =  Integer.parseInt(input);

                        try {
                            boolean sucesso = adminController.ativarConta(userLogado, idContaAtivar);
                            if (sucesso) {
                                JOptionPane.showMessageDialog(AtivarContas.this,
                                        "Conta ID " + idContaAtivar + " ativada com sucesso!)",
                                        "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                            } else {
                                JOptionPane.showMessageDialog(AtivarContas.this,
                                        "Erro: O ID não existe.",
                                        "Erro", JOptionPane.ERROR_MESSAGE);
                            }
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                }
            });


        }



    }
}
