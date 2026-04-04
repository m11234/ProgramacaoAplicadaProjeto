package controller;
import model.Equipamento;
import model.Utilizador;
import model.dao.ClienteDAO;
import model.dao.EquipamentoDAO;
import model.dao.UtilizadoresDAO;
import java.sql.SQLException;
import model.dao.UtilizadoresDAO;
import java.util.Date;
import java.util.Scanner;

public class EquipamentoController {
    private EquipamentoDAO dao = new EquipamentoDAO();
    private controller.UtilizadorController controller = new controller.UtilizadorController();
    private UtilizadoresDAO dao2 = new UtilizadoresDAO();

    public static void criarEquipamento(Scanner sc, Utilizador logado){

        if (userLogado == null) {
            System.out.println("Fazer login!!!");
            return;
        }
        if (!ClienteDAO.VerSeCliente(userLogado.getId())) {
            System.out.println("So gestores podem fazer isto!!!!");
            return;
        }


        System.out.println("\nRegistar Equipamento:");

        System.out.println("Digite o marca do equipamento:");
        String marca = sc.nextLine();
        sc.nextLine();

        System.out.println("Digite o modelo do equipamento:");
        String modelo = sc.nextLine();
        sc.nextLine();

        System.out.println("Digite o SKU do equipamento:");
        String sku = sc.nextLine();
        sc.nextLine();

        System.out.println("Digite o lote do equipamento:");
        String lote = sc.nextLine();
        sc.nextLine();

        Date DataSubmissao = new Date();

        int idEquipamento = logado.getId();

        Equipamento e = new Equipamento()

    }


}
