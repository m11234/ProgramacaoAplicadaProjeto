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

    /**
     * Metodo para criar e registar um novo equipamento
     * <p>
     *      O método valida primeiro se o utilizador atual tem sessão iniciada e se tem permissões (é um cliente),
     *      de seguida pede ao cliente as informações necessárias do equipamento (marca, modelo, SKU e lote). Após recolher
     *      os dados, gera a data de submissão atual, associa o equipamento ao ID do utilizador com sessão iniciada e efetua
     *      o registo na base de dados, informando sobre o sucesso ou falha da operação.
     * </p>
     * @param sc O objeto {@link Scanner} é responsável por capturar a informação introduzida na consola e passar
     * para a criação do objeto {@link Equipamento} e posteriormente para o metodo ({@code dao3.RegistarEquipamento})
     * @param userLogado O objeto {@link Utilizador} representa a conta com sessao iniciada no momento que é utilizado
     * para verificar permissões e obter o identificador (ID) para associar ao equipamento registado
     * @throws SQLException Se existir algum erro na comunicação com a base de dados seja durante o query de pesquisa ou registo
     * ({@code ClienteDAO.VerSeCliente}) e ({@code dao3.RegistarEquipamento}).
     */
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
