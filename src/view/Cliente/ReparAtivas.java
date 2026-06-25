package view.Cliente;

import controller.ReparacaoController;
import controller.UtilizadorController;
import model.Reparacao;
import model.Utilizador;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;
public class ReparAtivas extends JPanel {
    private Utilizador userLogado;
    private UtilizadorController utilizadorController = new UtilizadorController();
    private ReparacaoController reparacaoController =  new ReparacaoController();
    private Reparacao reparacao;

    public ReparAtivas(Utilizador u) throws SQLException {
        this.userLogado = u;

        setLayout(new BorderLayout());
        setBackground(Color.white);

        JLabel title = new JLabel("Reparacoes ativas");
        title.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(title, BorderLayout.NORTH);

        List<Reparacao> reparacaos = ReparacaoController.ReparAtivas(userLogado);

        if (reparacaos != null) {
            String[] columnNames = {"id","data_inicio","data_fim","custo","obs"};
            String[][] data = new String[reparacaos.size()][5];

            for(int i = 0; i < reparacaos.size(); i++) {
                Reparacao elesTodos = reparacaos.get(i);
                data[i][0] = String.valueOf(elesTodos.getIdR());
                data[i][1] = String.valueOf(elesTodos.getDataInicio());
                data[i][2] = String.valueOf(elesTodos.getDataFim());
                data[i][3] = String.valueOf(elesTodos.getCusto());
                data[i][4] = String.valueOf(elesTodos.getObservacao());
            }


            JTable table = new JTable(data, columnNames);
            JScrollPane scrollPane = new JScrollPane(table);
            scrollPane.setBorder(BorderFactory.createEmptyBorder());
            removeAll();
            add(scrollPane, BorderLayout.CENTER);
            revalidate();
            repaint();
        }
    }
}
