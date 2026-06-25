package view.Gestor;

import controller.ReparacaoController;
import model.Reparacao;
import model.Utilizador;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class AlertasReparacoes extends JPanel {
    private Utilizador userLogado;
    private ReparacaoController reparacaoController =  new ReparacaoController();

    public AlertasReparacoes(Utilizador u) throws SQLException {
        this.userLogado = u;

        setLayout(new BorderLayout());
        setBackground(Color.white);

        JLabel title = new JLabel("Reparacoes a ocorrer a mais de dez dias");
        title.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(title, BorderLayout.NORTH);

        List<Reparacao> m10Dias = reparacaoController.notificacaoDezDiasSemFinalizacao(userLogado);

        if (m10Dias != null) {
            String[] columnNames = {"idReparacai","data inicio","ide","obs"};
            String[][] data = new String[m10Dias.size()][4];

            for(int i = 0; i < m10Dias.size(); i++) {
                Reparacao Mais10 = m10Dias.get(i);
                data[i][0] = String.valueOf(Mais10.getIdR());
                data[i][1] = String.valueOf(Mais10.getDataInicio());
                data[i][2] = String.valueOf(Mais10.getIdEquip());
                data[i][3] = String.valueOf(Mais10.getObservacao());
            }
            JTable table = new JTable(data, columnNames);
            JScrollPane scrollPane = new JScrollPane(table);
            scrollPane.setBorder(BorderFactory.createEmptyBorder());

            add(scrollPane, BorderLayout.CENTER);
        }
    }

}
