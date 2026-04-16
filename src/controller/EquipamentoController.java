package controller;
import model.Equipamento;
import model.Utilizador;
import model.dao.ClienteDAO;
import model.dao.EquipamentoDAO;
import java.sql.SQLException;
import java.util.Date;
import java.util.Scanner;

public class EquipamentoController {
    private final EquipamentoDAO dao3 = new EquipamentoDAO();

    public void criarEquipamento(Scanner sc, Utilizador userLogado) throws SQLException {

        if (userLogado == null) {
            System.out.println("Fazer login!!!");
            return;
        }
        if (!ClienteDAO.VerSeCliente(userLogado.getId())) {
            System.out.println("So clientes podem fazer isto!!!!");
            return;
        }

        System.out.println("\nRegistar Equipamento:");

        System.out.println("Digite o marca do equipamento:");
        sc.nextLine();
        String marca = sc.nextLine();

        System.out.println("Digite o modelo do equipamento:");
        String modelo = sc.nextLine();

        System.out.println("Digite o SKU do equipamento:");
        String sku = sc.nextLine();

        System.out.println("Digite o lote do equipamento:");
        String lote = sc.nextLine();

        Date DataSubmissao = new Date();

        int id = userLogado.getId();

        Equipamento e = new Equipamento(marca,modelo,sku,lote,DataSubmissao,id);
        boolean sucesso = dao3.RegistarEquipamento(e);

        if (sucesso) {
            System.out.println("Equipamento registado com sucesso!!!");
        }
        else {
            System.out.println("Erro!!!");
        }


    }


}
