package view;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class LoginPagina extends JFrame {

        private Container cont;
        private JButton botaoOK;
        private JTextField caixaLogin, caixaPassword;

        public LoginPagina() {
                cont = getContentPane();
                cont.setLayout(new BorderLayout());

                JPanel painelTopo = new JPanel(new FlowLayout(FlowLayout.CENTER));
                painelTopo.add(new JLabel("Janela de Autentica��o"));

                GridLayout gl = new GridLayout(3,2);
                gl.setHgap(2);
                gl.setVgap(2);
                JPanel painelLogin = new JPanel(gl);

                JPanel painelLabelLogin = new JPanel(new FlowLayout(FlowLayout.RIGHT));
                painelLabelLogin.add(new JLabel("Login"));

                JPanel painelLabelPassword= new JPanel(new FlowLayout(FlowLayout.RIGHT));
                painelLabelPassword.add(new JLabel("Password"));

                JPanel painelCaixaLogin = new JPanel(new FlowLayout(FlowLayout.LEFT));
                caixaLogin = new JTextField(10);
                painelCaixaLogin.add(caixaLogin);

                JPanel painelCaixaPassword = new JPanel(new FlowLayout(FlowLayout.LEFT));
                caixaPassword = new JTextField(10);
                painelCaixaPassword.add(caixaPassword);

                JPanel painelBotao = new JPanel(new FlowLayout(FlowLayout.CENTER));
                botaoOK = new JButton ("  OK  ");
                botaoOK.setActionCommand("ok");
                painelBotao.add(botaoOK);

                painelLogin.add(painelLabelLogin);
                painelLogin.add(painelCaixaLogin);

                painelLogin.add(painelLabelPassword);
                painelLogin.add(painelCaixaPassword);

                painelLogin.add(new JPanel());
                painelLogin.add(painelBotao);


                cont.add(painelTopo, BorderLayout.NORTH);
                cont.add(painelLogin, BorderLayout.CENTER);
                cont.add(new JPanel(), BorderLayout.SOUTH);
        }


        public int autenticacao() {
                String login = caixaLogin.getText();
                String password = caixaPassword.getText();

                if (login.length() > 0 && password.length() > 0)
                        return (verificaAutenticacao(login, password)? 1: -1);
                else {
                        String mensagem;

                        if (login.length() == 0) {
                                if (password.length() == 0) {
                                        mensagem = "Os campos LOGIN e PASSWORD devem ser preenchidos";
                                } else {
                                        mensagem = "O campo LOGIN deve ser preenchido";
                                }
                        } else {
                                mensagem = "O campo PASSWORD deve ser preenchido";
                        }

                        JOptionPane.showMessageDialog(this,  mensagem,  "Informa��o", JOptionPane.ERROR_MESSAGE);
                        return 0;
                }
        }

        private boolean verificaAutenticacao(String aLogin, String aPawword) {
                //acesso � BD e compara��o com os dados armazenados de forma persistente
                return true;
        }


}
