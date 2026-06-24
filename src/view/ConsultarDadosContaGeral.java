package view;

import model.Utilizador;
import controller.UtilizadorController;

import javax.swing.*;
import java.awt.*;

/**
 * Classe responsável por criar a interface gráfica para consulta de dados gerais da conta
 * <p>
 * Esta classe estende {@link JPanel} e constrói uma vista que apresenta as informações pessoais do utilizador
 * atualmente logado (como nome, username e email), acompanhado pela exibição da foto de perfil.
 * </p>
 */
public class ConsultarDadosContaGeral extends JPanel {

    private Utilizador userLogado;
    private UtilizadorController controller = new UtilizadorController();


    /**
     * Construtor da classe que inicializa a interface e carrega os dados e a fotografia do utilizador
     * <p>
     * O construtor obtém os dados atualizados do utilizador invocando o método ({@code controller.ConsultarDados})
     * e configura o aspeto visual do painel. Define uma imagem de perfil padrão (caso não exista uma personalizada),
     * carrega o recurso gráfico da imagem com recurso ao {@link Toolkit} e organiza os dados textuais numa {@link JTable}
     * não editável posicionada no centro da interface.
     * </p>
     * @param u O objeto {@link Utilizador} que representa a conta com sessão iniciada no momento, utilizado como
     * parâmetro de pesquisa para recuperar o conjunto completo de dados e caminhos de ficheiros associados ao perfil.
     */

    public ConsultarDadosContaGeral(Utilizador u) {
        this.userLogado = u;

        Utilizador dados = controller.ConsultarDados(u);

        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        JLabel title = new JLabel("Consultar Dados da Conta", SwingConstants.CENTER);
        title.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(title, BorderLayout.NORTH);

        String caminho ;

        if (dados.getNome().isEmpty()) {
            caminho = "CoisasFeitas/meow_meow/IMG_0373.jpg";
            System.out.println("A foto por defeito existe? " + new java.io.File("CoisasFeitas/meow_meow/IMG_0373.jpg").exists());
        } else {
            caminho = dados.getFoto();
        }
        System.out.println(caminho);

        // solucao adaptada do powerpoint 6.3 pagina 33
        // solucao do Professor Marco Veloso
        //consultada no dia 23/06/2026

        Image image = Toolkit.getDefaultToolkit().getImage(caminho);

            String[] columnNames = {"Campo", "Informação Pessoal"};
            String[][] data = {
                    {"Nome", dados.getNome()},
                    {"Username", dados.getUsername()},
                    {"Email", dados.getEmail()},
            };

            JTable table = new JTable(data, columnNames);
            table.setRowHeight(30);
            table.setEnabled(false);

            JLabel myLabel = new JLabel(new ImageIcon(image));
            myLabel.setBorder(BorderFactory.createEmptyBorder(0, 30, 0, 0));


            JScrollPane scrollPane = new JScrollPane(table);
            scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 50, 50, 50));
            add(scrollPane, BorderLayout.CENTER);
            add(myLabel, BorderLayout.WEST);

        }
    }