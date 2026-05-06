package view;

import javax.swing.*;
import java.awt.*;

public class MenuNovo extends javax.swing.JFrame {
    public Object Login() {
        JFrame frame = new JFrame();
        Container cont = getContentPane();
        cont.setLayout(new BorderLayout());

        JButton Login = new JButton("Login");
        cont.add(Login, BorderLayout.NORTH);
        frame.setVisible(true);
        return null;

    }

}
