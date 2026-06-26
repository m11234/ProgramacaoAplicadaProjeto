package view.Cliente;

import controller.EquipamentoController;
import controller.ReparacaoController;
import model.Equipamento;
import model.Reparacao;
import model.Utilizador;
import model.Equipamento;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;


/**
 * Classe responsável por criar a interface gráfica para a gestão de equipamentos do cliente
 * <p>
 * Esta classe estende {@link JPanel} e disponibiliza ao utilizador uma tabela contendo todos os seus equipamentos
 * registados no sistema, permitindo também a adição de novos dispositivos através de um formulário interativo.
 * </p>
 */

public class EquipamentosCliente extends JPanel {
    private Utilizador userLogado;
    private EquipamentoController equipamentoController = new EquipamentoController();
    private ReparacaoController reparacaoController = new ReparacaoController();


    /**
     * Construtor da classe que inicializa a interface e carrega a listagem de equipamentos do cliente
     *     <p>
     *     O construtor define as propriedades visuais do painel e invoca o método ({@code equipamentoController.verEquipamentos})
     *     para preencher uma {@link JTable} com os detalhes dos dispositivos do utilizador. Adicionalmente, configura um
     *     event listener ({@link ActionListener}) no botão ({@code AdicionarEquipamento}), que gera um input
     *     para capturar os dados do novo dispositivo (marca, modelo, SKU e lote) e efetuar a submissão na base
     *     de dados através do método ({@code equipamentoController.criarEquipamento}).
     *     </p>
     * @param u
     * @throws SQLException
     */


    public EquipamentosCliente(Utilizador u) throws SQLException {
        this.userLogado = u;

        setLayout(new BorderLayout());
        setBackground(Color.white);

        JLabel title = new JLabel("Equipamentos");
        title.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(title, BorderLayout.NORTH);

        List<Equipamento> equimentos = equipamentoController.verEquipamentos(userLogado);

        if (equimentos != null) {
            String[] columnNames = {"idEquipamento", "Marca","Modelo", "SKU", "Lote", "dataEntrada", "dataReparacao"};
            String[][] data = new String[equimentos.size()][7];

            for(int i = 0; i < equimentos.size(); i++) {
                Equipamento equipar = equimentos.get(i);
                data[i][0] = String.valueOf(equipar.getIdEquipamento());
                data[i][1] = String.valueOf(equipar.getMarca());
                data[i][2] = String.valueOf(equipar.getModelo());
                data[i][3] = String.valueOf(equipar.getSKU());
                data[i][4] = String.valueOf(equipar.getLote());
                data[i][5] = String.valueOf(equipar.getDataSubmissao());
                data[i][6] = String.valueOf(equipar.getDataReparacao());
            }
            JTable table = new JTable(data, columnNames);
            JScrollPane scrollPane = new JScrollPane(table);
            scrollPane.setBorder(BorderFactory.createEmptyBorder());

            JPanel equipar =  new JPanel();
            equipar.setBackground(Color.white);

            JButton AdicionarEquipamento = new JButton("Adicionar Equipamento");
            AdicionarEquipamento.setToolTipText("Clique aqui para adicionar um equipamento");
            AdicionarEquipamento.setCursor(new Cursor(Cursor.HAND_CURSOR));
            equipar.add(AdicionarEquipamento);

            JButton SubmeterEquipamentoReparacao = new JButton("Pedido Reparacao");
            SubmeterEquipamentoReparacao.setToolTipText("Clique aqui para iniciar uma reparacao");
            SubmeterEquipamentoReparacao.setCursor(new Cursor(Cursor.HAND_CURSOR));
            equipar.add(SubmeterEquipamentoReparacao);

            add(scrollPane, BorderLayout.CENTER);
            add(equipar, BorderLayout.NORTH);

            AdicionarEquipamento.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                //marca
                JTextField Marca = new JTextField(15);
                Marca.setToolTipText("Marca do equipamento");
                JLabel MarcaLabel = new JLabel("Insira aqui a marca do equipamento");

                JTextField Modelo = new JTextField(15);
                Modelo.setToolTipText("Modelo do equipamento");
                JLabel ModeloLabel = new JLabel("Insira aqui o modelo do equipamento");

                JTextField Sku = new JTextField(15);
                Sku.setToolTipText("SKU do equipamento");
                JLabel SkuLabel = new JLabel("Insira aqui o sku");

                JTextField Lote = new JTextField(15);
                Lote.setToolTipText("Lote do equipamento");
                JLabel LoteLabel = new JLabel("Insira aqui o lote");

                  JComponent[] inputs = new JComponent[]{
                        Marca,
                        MarcaLabel,
                        Modelo,
                        ModeloLabel,
                        Sku,
                        SkuLabel,
                        Lote,
                        LoteLabel
                };


                int valor = JOptionPane.showConfirmDialog(
                        EquipamentosCliente.this,
                        inputs,
                        "Adicionar equipamento",
                        JOptionPane.OK_CANCEL_OPTION);


                String text = Marca.getText();
                String text2 = Modelo.getText();
                String text3 = Sku.getText();
                String text4 = Lote.getText();

                if(text!=null || text2!=null || text3!=null || text4!=null){
                    String marcaE = text;
                    String modeloE = text2;
                    String skuE = text3;
                    String loteE = text4;
                    if (valor == JOptionPane.OK_OPTION) {
                        try {
                           boolean sucesso = equipamentoController.criarEquipamento(marcaE,modeloE,skuE,loteE,userLogado);
                           if (sucesso){
                               JOptionPane.showMessageDialog(EquipamentosCliente.this,
                                       "Equipamento Insirido" + marcaE + modeloE + "insirido com sucesso)",
                                       "Sucesso" , JOptionPane.INFORMATION_MESSAGE);
                           } else {
                               JOptionPane.showMessageDialog(EquipamentosCliente.this,
                                       "Erro a insirir dados",
                                       "Erro" , JOptionPane.ERROR_MESSAGE);
                           }
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                }
                }
            });

            SubmeterEquipamentoReparacao.addActionListener(
                    new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            JTextField obs = new JTextField(15);
                            obs.setToolTipText("Breve descrição do problema do equipamento");
                            JLabel obsLabel = new JLabel("Insira aqui a sua descricao do problema");

                            JTextArea idEquip = new JTextArea();
                            idEquip.setToolTipText("ID do equipamento que deseja submeter para reparacao pode consultar na tabela o id do mesmo");
                            JLabel idEquipLabel = new JLabel("Insira o id do equipamento a reparar");

                            JComponent[] inputs2 = new JComponent[]{
                                    obs,
                                    obsLabel,
                                    idEquip,
                                    idEquipLabel,
                            };

                            int valor2 = JOptionPane.showConfirmDialog(
                                    EquipamentosCliente.this,
                                    inputs2,
                                    "Submeter Reparacao",
                                    JOptionPane.OK_CANCEL_OPTION
                            );

                            String text5 = obs.getText();
                            String text6 = idEquip.getText();

                            if(text5!=null || text6!=null){
                                String obsI = text5;
                                String EquipI = text6;

                                if (valor2 == JOptionPane.OK_OPTION) {
                                    try {
                                        boolean sucesso = reparacaoController.criarReparacao(obsI, Integer.parseInt(EquipI),userLogado);
                                        if (sucesso){
                                            JOptionPane.showMessageDialog(
                                                    EquipamentosCliente.this,
                                                    "Reparacao submetida com muito sucesso",
                                                    "Sucesso" , JOptionPane.INFORMATION_MESSAGE
                                            );
                                        }   else {
                                            JOptionPane.showMessageDialog(EquipamentosCliente.this,
                                                    "Erro a insirir dados",
                                                    "Erro" , JOptionPane.ERROR_MESSAGE);
                                        }
                                    } catch (SQLException ex) {
                                        throw new RuntimeException(ex);
                                    }
                                }
                            }



                        }
                    }
            );
        }
    }
}
