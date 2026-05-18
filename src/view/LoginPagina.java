package view;

import javax.swing.*;
import java.awt.*;
import com.formdev.flatlaf.FlatDarkLaf;
public class LoginPagina extends javax.swing.JFrame {
    private Container cont;
    private JButton botaoOK;
    private JTextField caixaLogin, caixaPassword;

    public Object Login() {
            FlatDarkLaf.setup();
            cont = getContentPane();
            cont.setLayout(new BorderLayout());

            JPanel painelTopo = new JPanel(new FlowLayout(FlowLayout.CENTER));
            painelTopo.add(new JLabel("Pagina Login:"));

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
            setVisible(true);
            setEnabled(true);

        return null;
    } }
