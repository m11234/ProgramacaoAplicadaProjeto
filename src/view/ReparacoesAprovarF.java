package view;

import controller.AdminController;
import controller.ReparacaoController;
import model.Reparacao;
import model.Utilizador;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;


/**
 * Classe responsável por criar a interface gráfica para a gestão de reparações por parte do funcionário
 * <p>
 * Esta classe estende {@link JPanel} e disponibiliza ao funcionário uma tabela contendo as reparações que lhe
 * foram atribuídas, permitindo-lhe atualizar o estado das mesmas para aceite ou rejeitado.
 * </p>
 */

public class ReparacoesAprovarF extends JPanel {
    private Utilizador userLogado;
    private ReparacaoController reparacaoController =  new ReparacaoController();
    int Estado;


    /**
     * Construtor da classe que inicializa a interface gráfica e carrega a listagem de reparações atribuídas ao funcionário
     * <p>
     * O construtor define as propriedades visuais do painel e invoca o método ({@code reparacaoController.verReparacoesPorAprovarF})
     * para preencher uma {@link JTable} com os identificadores e observações das reparações pendentes. Configura também um
     * ouvinte de eventos ({@link ActionListener}) no botão de ação, gerando uma caixa de diálogo personalizada que permite
     * ao funcionário definir o novo estado do pedido e submeter a decisão através do método ({@code ReparacaoController.aceitarReparacaoF}).
     * </p>
     * @param u O objeto {@link Utilizador} que representa o funcionário com sessão iniciada no momento, utilizado para
     * filtrar as reparações que se encontram associadas ao seu identificador.
     * @throws SQLException Se ocorrer alguma falha ou erro na comunicação com a base de dados ao tentar obter a
     * lista de reparações pendentes do funcionário.
     */

    public ReparacoesAprovarF(Utilizador u) throws SQLException {
        this.userLogado = u;

        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        JLabel title = new JLabel("Reparações por Aprovar", SwingConstants.CENTER);
        title.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(title, BorderLayout.NORTH);

        List<Reparacao> porAtivar = reparacaoController.verReparacoesPorAprovarF(userLogado);

        if (porAtivar != null) {
            String[] columnNames = {"id", "nome","obs"};
            String[][] data = new String[porAtivar.size()][3];

            for (int i = 0; i < porAtivar.size(); i++) {
                Reparacao Ativar = porAtivar.get(i);
                data[i][0] = String.valueOf(Ativar.getIdR());
                data[i][1] = String.valueOf(Ativar.getIdEquip());
                data[i][2] = String.valueOf(Ativar.getObservacao());
            }

            JTable table = new JTable(data, columnNames);
            JScrollPane scrollPane = new JScrollPane(table);
            scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 50, 50, 50));

            JPanel Ativar = new JPanel();
            Ativar.setBackground(Color.WHITE);

            JButton ButaoAtivar = new JButton("Aceitar/Rejeitar reparação");
            Ativar.add(ButaoAtivar, BorderLayout.SOUTH);

            add(scrollPane, BorderLayout.CENTER);
            add(Ativar, BorderLayout.NORTH);


            /*
              Nota: Aceitar ou rejeitar reparacoes.
              Solução adaptada dos slides 16 e 17 do powerpoint 6.3 do Professor Marco Veloso.
              Consulta relizada: 24 de Junho de 2026.
             */

            ButaoAtivar.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    JTextField myText1 = new JTextField(10);
                    JLabel myLabel1 = new JLabel("Insira o ID da reparacao que pretender aceitar/rejeitar");
                    JComponent[] inputs = new JComponent[]
                                {
                                    myLabel1,
                                    myText1,};

                    UIManager.put("OptionPane.cancelButtonText", "Rejeitar");
                    UIManager.put("OptionPane.okButtonText", "Aceitar");

                    int valor = JOptionPane.showConfirmDialog(
                            ReparacoesAprovarF.this,
                            inputs,
                            "Aceitar reparacao",
                            JOptionPane.OK_CANCEL_OPTION);

                    String text = myText1.getText();

                    if (text != null) {
                        int idR = Integer.parseInt(myText1.getText());
                        if(valor == JOptionPane.OK_OPTION){
                            Estado = 3;
                            System.out.println("Reparacao aceite");

                        } else if (valor == JOptionPane.CANCEL_OPTION) {
                            Estado = 1;
                            System.out.println("Reparacao rejeitada");
                        }

                        boolean sucesso = ReparacaoController.aceitarReparacaoF(idR, Estado, userLogado);
                        if (sucesso) {
                            JOptionPane.showMessageDialog(ReparacoesAprovarF.this,
                                    "Reparação ID " + idR + " ativada com sucesso!)",
                                    "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            JOptionPane.showMessageDialog(ReparacoesAprovarF.this,
                                    "Erro: O ID não existe.",
                                    "Erro", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
            });

    }


}}

