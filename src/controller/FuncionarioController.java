package controller;
import java.util.Date;
import java.util.Scanner;

import model.Funcionario;
import model.Utilizador;
import model.dao.FuncionarioDAO;

public class FuncionarioController {
    private static final FuncionarioDAO dao = new FuncionarioDAO();

    /**
     * Método para criar e registar um novo funcionário no sistema
     * <p>
     *     O método recolhe os dados necessários para o registo (NIF, telemóvel, morada e nível).
     *     Durante o processo, efetua a validação do número de telemóvel introduzido (garantindo que possui 9 dígitos
     *     e começa por 9, 2 ou 3). Após a validação e recolha da informação, gera a data de registo/início atual,
     *     associa os dados ao identificador (ID) do utilizador com sessão iniciada e regista o funcionário na base de dados,
     *     informando o utilizador sobre o sucesso ou falha da operação.
     * </p>
     * @param sc O objeto {@link Scanner} é responsável por capturar a informação introduzida na consola e passar
     * para a criação do objeto {@link Funcionario} e posteriormente para o metodo ({@code dao.RegistarFuncionario})
     * @param logado O objeto {@link Utilizador} representa a conta com sessão iniciada no momento utilizado
     * para obter o identificador do utilizador e associar ao novo registo de funcionário
     */
    public static void criarFuncionario(Scanner sc, Utilizador logado) {
        System.out.println("\n Registar Funcionario");

        System.out.println("NIF");
        int nif = sc.nextInt();
        sc.nextLine();

        int telemovel = 0;
        boolean contactoValido = false;

        while (!contactoValido) {
            System.out.println("Telemovel:");
            String entrada = sc.nextLine();

            if (entrada.matches("[923][0-9]{8}")) {
                telemovel = Integer.parseInt(entrada); //serve para retornar um número inteiro
                contactoValido = true;
            } else {
                System.out.println("Erro: O número de telemovel deve ter 9 dígitos e começar por 9, 2 ou 3.");
            }
        }

        System.out.println("Morada:");
        String morada = sc.nextLine();

        System.out.println("Nivel E:");
        int nivelE = sc.nextInt();
        sc.nextLine();

        Date dataI = new Date();
        int idUtilizador = logado.getId();

        Funcionario f = new Funcionario(nif,telemovel,morada,nivelE,dataI,idUtilizador);
        boolean sucesso = dao.RegistarFuncionario(f);
        if (sucesso) {
            System.out.println("Funcionario registado");
        } else {
            System.out.println("Err ");
        }
    }
}
