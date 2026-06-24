package controller;

import view.LoginPagina;

import javax.swing.*;

public class MainNovo  {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new LoginPagina().setVisible(true);
            }
        });
    }
}


