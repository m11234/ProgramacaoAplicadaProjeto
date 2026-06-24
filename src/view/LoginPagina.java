package view;

import controller.UtilizadorController;
import model.Funcionario;
import model.Utilizador;
import model.dao.AdminDao;
import model.dao.ClienteDAO;
import model.dao.FuncionarioDAO;
import model.dao.UtilizadoresDAO;
import view.RegistarPagina;
import view.Menu;
import view.MenuCliente;
import view.MenuFuncionario;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

/**
 * Classe responsável por criar a interface gráfica principal do login da aplicação
 * <p>
 * Esta classe estende {@link JFrame} e gere a interface de Login, validando as credenciais dos utilizadores,
 * avaliando os seus estados e redirecionando-os para os respetivos menus específicos (Cliente, Gestor ou Funcionário)
 * com base no seu perfil guardado na base de dados.
 * </p>
 */

public class LoginPagina extends JFrame {

        private JTextField username = new JTextField(15);
        private JPasswordField pass = new JPasswordField(15);
        private JButton loginButao = new JButton("Login");
        private JButton IrRegistoButao = new JButton("Registar Conta");

        private final UtilizadorController controller = new UtilizadorController();
        private Utilizador userLogado = null;


    /**
     * Construtor da classe que inicializa, configura e monta a interface de login
     * <p>
     * O construtor define as propriedades básicas do {@link JFrame}, cria e organiza os painéis para os campos
     * de texto e botões, e adiciona 2 ouvintes de eventos ({@link ActionListener}): 1 para processar a tentativa
     * de início de sessão (({@code loginButao})) e outro para permitir o encaminhamento do utilizador para a
     * página de registo de novas contas (({@code IrRegistoButao})).
     * </p>
     */
        public LoginPagina()  {

                setTitle("Login");
                setSize(400, 300);
                setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                JPanel painelUsername = new JPanel(new FlowLayout());
                painelUsername.add(new JLabel("Username:", SwingConstants.RIGHT));
                painelUsername.setPreferredSize(new Dimension(100,20));
                painelUsername.add(username);
                username.setToolTipText("Insira aqui o seu username");

                JPanel painelPassword = new JPanel(new FlowLayout());
                painelPassword.add(new JLabel("Password:", SwingConstants.RIGHT));
                painelPassword.setPreferredSize(new Dimension(100,20));
                painelPassword.add(pass);
                pass.setToolTipText("Insira aqui a sua password");

                JPanel PainelIrRegistoButao = new JPanel(new FlowLayout());
                PainelIrRegistoButao.add(IrRegistoButao);
                IrRegistoButao.setCursor(new Cursor(Cursor.HAND_CURSOR));

                JPanel painelLoginPrincipal = new JPanel(new GridLayout(4, 1));
                painelLoginPrincipal.add(new JLabel("Login", SwingConstants.CENTER));
                painelLoginPrincipal.add(painelUsername);
                painelLoginPrincipal.add(painelPassword);
                painelLoginPrincipal.add(PainelIrRegistoButao);

                JPanel painelBotoes = new JPanel(new FlowLayout());
                painelBotoes.add(loginButao);
                loginButao.setCursor(new Cursor(Cursor.HAND_CURSOR));

                Container contentor = getContentPane();
                contentor.setLayout(new BorderLayout());

                contentor.add(painelLoginPrincipal, BorderLayout.NORTH);
                contentor.add(new JPanel(), BorderLayout.CENTER);
                contentor.add(painelBotoes, BorderLayout.SOUTH);

                loginButao.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                                String userText = username.getText();
                                String passText = pass.getText();

                                if (userText.isEmpty() || passText.isEmpty()) {
                                        JOptionPane.showMessageDialog(
                                                LoginPagina.this ,
                                                "Por favor preencha todos os campos.",
                                                "Erro",
                                                JOptionPane.ERROR_MESSAGE );
                                } else {
                                        try {
                                                userLogado = controller.Login(userText,passText);
                                        } catch (SQLException ex) {
                                                throw new RuntimeException(ex);
                                        }

                                        if (userLogado != null) {
                                                dispose();
                                            try {
                                                if(ClienteDAO.VerSeCliente(userLogado.getId())) {
                                                        //trocar depois com o menu do cliente
                                                        MenuCliente paginaCliente = new MenuCliente(userLogado);
                                                        paginaCliente.setVisible(true);
                                                }
                                                if(AdminDao.VerSeGestor(userLogado.getId())){
                                                        MenuGestor paginaGestor = new MenuGestor(userLogado);
                                                        paginaGestor.setVisible(true);
                                                }
                                                if(FuncionarioDAO.verSeFuncionario(userLogado.getId())){
                                                        //trocar pelas coisas do menu do funcionario
                                                        MenuFuncionario paginaFuncionario = new MenuFuncionario(userLogado);
                                                        paginaFuncionario.setVisible(true);
                                                }
                                                if(userLogado.getEstado() == 0) {
                                                        JOptionPane.showMessageDialog(
                                                                LoginPagina.this,
                                                                "A sua conta ainda nao foi aprovada",
                                                                "Conta nao aprovada",
                                                                JOptionPane.ERROR_MESSAGE 
                                                        );
                                                }
                                                if(userLogado.getEstado() == 3 || userLogado.getEstado() == 4) {
                                                        JOptionPane.showMessageDialog(
                                                                LoginPagina.this,
                                                                "A sua conta esta em processo de ser apagada ou apagada",
                                                                "Conta nao valida",
                                                                JOptionPane.ERROR_MESSAGE
                                                        );
                                                }
                                            } catch (SQLException ex) {
                                                throw new RuntimeException(ex);
                                            }
                                        } else {
                                                JOptionPane.showMessageDialog(
                                                        LoginPagina.this,
                                                        "Username ou password incorretos",
                                                        "Erro",
                                                        JOptionPane.ERROR_MESSAGE
                                                );
                                        }
                                }
                        }
                });

                IrRegistoButao.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                                int valor = JOptionPane.showConfirmDialog(
                                        LoginPagina.this,
                                        "Deseja ir para o menu para registar uma conta?",
                                        "Confirmacao de Registo",
                                        JOptionPane.OK_CANCEL_OPTION
                                );

                                if (valor == JOptionPane.OK_OPTION) {
                                        dispose();

                                        RegistarPagina paginaRegisto = new RegistarPagina();
                                        paginaRegisto.setVisible(true);
                                }
                        }
                });
        }

        /**
         * Método principal (ponto de entrada) utilizado para executar a aplicação de forma independente
         * <p>
         * Este método garante que a criação e visualização da janela gráfica ocorrem de forma segura dentro da
         * linha de execução de despacho de eventos do Swing (Event Dispatch Thread), chamando o método
         * ({@code SwingUtilities.invokeLater}).
         * </p>
         * @param args Os argumentos de linha de comando passados durante a inicialização do programa (não utilizados).
         */
        public static void main(String[] args) {
                SwingUtilities.invokeLater(new Runnable() {
                        public void run() {
                                new LoginPagina().setVisible(true);
                        }
                });
        }
}