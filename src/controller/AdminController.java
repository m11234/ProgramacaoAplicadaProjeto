package controller;

import model.Admin;
import model.Equipamento;
import model.Reparacao;
import model.Utilizador;
import model.dao.AdminDao;

import java.sql.SQLException;
import java.util.Scanner;
import java.util.regex.Pattern;

import model.dao.UtilizadoresDAO;

public class AdminController {
    private static final AdminDao adminDao = new AdminDao();
    private final UtilizadorController controller = new UtilizadorController();
    private final UtilizadoresDAO dao = new UtilizadoresDAO();
    //email
    private static final String EMAIL_REGEX = "^[^@\\s]+@[^@\\s]+\\.[^@\\s]{2,}$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

    /**
     * Verifica se existem admins (gestores) na base de dados.
     * <p>
     * Se não for encontrado nenhum gestor na base de dados significa que é a primeira execução
     * do programa o metodo vai iniciar o processo de criação do primeiro gestor/utilizador
     * O processo começa por chamar o metodo que regista o primeiro utilizador, depois confirmar os dados
     * introduzidos e por fim a atribuição dos privilégios de gestor,
     * </p>
     * @param sc O objeto {@link Scanner} é usado para capturar a informação que o utilizador entra na consola.
     * @throws RuntimeException Se ocorrer algum erro na manipulação da base de dados envia {@link SQLException}
     */
    public void verAdmins(Scanner sc) {
        try {
            System.out.println("A verificar se existem gestores....");

            if (!adminDao.verificarGestores()) {
                System.out.println("Primeira execucao do programa");
                System.out.println("A criar gestor (Siga os passos de registo)...");

                controller.registarG(sc);
                sc.nextLine();

                System.out.println("\n--- Criar Gestor ---");
                System.out.println("Por favor, confirme as credenciais que acabou de criar:");
                System.out.print("Username: ");
                String username = sc.nextLine();
                System.out.print("Password: ");
                String password = sc.nextLine();

                Utilizador uPrimeiro = new Utilizador(username, password);
                Utilizador userCriado = dao.Login(uPrimeiro);
                dao.mudarEstadoGestor(userCriado);


                Admin novoGestor = new Admin();
                novoGestor.setId(userCriado.getId());

                adminDao.criarGestor(novoGestor);
                System.out.println("Conta de Gestor criada com sucesso para o ID: " + userCriado.getId());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * Apresenta ao gestor a lista de contas pendentes de ativação
     * <p> O metodo valida primeiro se o utilizador atual tem permissões (é um gestor) e tem sessão iniciada. </p>
     * @param userLogado O objeto {@link Utilizador} representa a conta com sessão iniciada no momento. Utilizado
     *                   para verificar permissões.
     * @throws SQLException Se ocorrer algum erro na comunicação com a base de dados seja durante a verificação
     * de privilégios ({@code adminDao.VerSeGestor})  ou durante a query a base de dados ({@code dao.verContasPorAtivar}).
     *
     */
    public void verContasPorAtivar(Utilizador userLogado) throws SQLException {

        if (userLogado == null) {
            System.out.println("Fazer login!!!");
            return;
        }
        if (!adminDao.VerSeGestor(userLogado.getId())) {
            System.out.println("So gestores podem fazer isto!!!!");
            return;
        }

        dao.verContasPorAtivar();
    }


    /**
     * Apresenta ao gestor a lista de contas pendentes de desativação
     * <p>
     *     O metodo valida primeiro se o utilizador atual tem permissões (é um gestor) e tem sessão iniciada.
     * </p>
     * @param userLogado O objeto {@link Utilizador} representa a conta com sessão iniciada no momento. Utilizad
     *                   para verificar permissões.
     * @throws SQLException Se ocorrer algum erro na comunicação com a base de dados seja durante a verificação
     *de privilégios ({@code adminDao.VerSeGestor})  ou durante a query a base de dados ({@code dao.verContasPorApagar}).
     */
    public void verContasPorApagar(Utilizador userLogado) throws SQLException {

        if (userLogado == null) {
            System.out.println("Fazer login!!!");
            return;
        }
        if (!adminDao.VerSeGestor(userLogado.getId())) {
            System.out.println("So gestores podem fazer isto!!!!");
            return;
        }

        dao.verContasPorApagar();
    }


    /**
     * Metodo para ativar contas de utilizadores
     * <p>
     *     O metodo valida primeiro se o utilizador atual tem permissões (é um gestor) e tem sessão iniciada,
     *     de seguida pede ao gestor o id da conta a ativar, verifica se a mesma existe e se sim ativa essa
     *     mesma conta pelo id inserido.
     * </p>
     * @param userLogado O objeto {@link Utilizador} representa a conta com sessao iniciada no momento que é utilizado
     *                   para verificar permissões
     * @param sc O objeto {@link Scanner} é responsavel por capturar a informação introduzida na consola e passar
     *           para o metodo ({@code dao.verSeContaExiste}) e ({@code dao.mudarEstado})
     * @throws SQLException Se exister algum erro na comunicação com a base de dados seja durante o query de pesquisa
     * ({@code dao.verSeContaExiste}), ({@code dao.mudarEstado}) e ({@code adminDao.VerSeGestor}).
     */
    public void ativarConta(Utilizador userLogado, Scanner sc) throws SQLException {
        if (userLogado == null) {
            System.out.println("Fazer login!!!");
            return;
        }
        if (!adminDao.VerSeGestor(userLogado.getId())) {
            System.out.println("So gestores podem fazer isto!!!!");
            return;
        }
        System.out.println("Inserir id da conta a ativar");
        int id = sc.nextInt();
        if (!dao.verSeContaExiste(id)) {
            System.out.println("Essa conta nao existe!!");
            return;
        }
        dao.mudarEstado(id);
        System.out.println("Conta atualizada com sucesso para o ID: " + id);
    }

    /**
     * Método para apagar contas de utilizadores do sistema.
     * <p>
     *     O método valida inicialmente se o utilizador atual tem sessão iniciada e se possui
     *     permissões de gestor. Após a validação, solicita ao gestor o ID da conta que deseja apagar,
     *     oferecendo a opção de introduzir '0' para cancelar a operação. Caso o utilizador prossiga,
     *     o método verifica se o ID corresponde a uma conta existente e, em caso afirmativo, elimina-a
     *     da base de dados.
     * </p>
     * @param userLogado O objeto {@link Utilizador} que representa a conta com sessão iniciada no momento,
     * sendo utilizado para verificar permissões.
     * @param sc O objeto {@link Scanner} responsável por capturar a entrada de dados na consola.
     * @throws SQLException Se ocorrer algum erro na comunicação com a base de dados, seja durante a
     * verificação de privilégios ({@code adminDao.VerSeGestor}), na validação da existência da conta
     * ({@code dao.verSeContaExiste}) ou na eliminação efetiva da conta ({@code dao.ApagarContaAdmin}).
     */

    public void apagarContas(Utilizador userLogado, Scanner sc) throws SQLException {
        if (userLogado == null) {
            System.out.println("Fazer login!!!");
            return;
        }
        if (!adminDao.VerSeGestor(userLogado.getId())) {
            System.out.println("So gestores podem fazer isto!!!!");
            return;
        }
        System.out.println("Inserir id da conta a apagar 0 para abortar");
        int id = sc.nextInt();
        if (id == 0) {
            System.out.println("A abortar processo....");
            return;
        }
        if (!dao.verSeContaExiste(id)) {
            System.out.println("Essa conta nao existe!!");
            return;
        }
        dao.ApagarContaAdmin(id);
        System.out.println("Conta apagada com sucesso para o ID: " + id);
    }

    /**
     * Metodo para criar outras contas de gestor
     * <p>
     *     O metodo valida primeiro se o utilizador atual tem permissões (é um gestor) e tem sessão iniciada,
     *     de seguida inicia o processo de registo da nova conta, pede a confirmação das credenciais introduzidas,
     *     efetua o login dessa conta para obter os seus dados e, por fim, altera o seu estado e regista-a como um novo gestor.
     * </p>
     * @param userLogado O objeto {@link Utilizador} representa a conta com sessao iniciada no momento que é utilizado
     * para verificar permissões
     * @param sc O objeto {@link Scanner} é responsavel por capturar a informação introduzida na consola e passar
     * para o metodo ({@code controller.registarG}) e para a criação do objeto de login {@link Utilizador}
     * @throws SQLException Se existir algum erro na comunicação com a base de dados seja durante o query de pesquisa ou durante
     * um insert ({@code adminDao.VerSeGestor}), ({@code dao.Login}), ({@code dao.mudarEstadoGestor}) e ({@code adminDao.criarGestor}).
     */
    public void CriarOutroGestor(Utilizador userLogado, Scanner sc) throws SQLException {
        if (userLogado == null) {
            System.out.println("Fazer login!!!");
            return;
        }
        if (!adminDao.VerSeGestor(userLogado.getId())) {
            System.out.println("So gestores podem fazer isto!!!!");
            return;
        }
        System.out.println("A proceder a criacao de um gestor");
        controller.registarG(sc);
        sc.nextLine();

        System.out.println("\n--- Criar Gestor ---");
        System.out.println("Por favor, confirme as credenciais que acabou de criar:");
        System.out.print("Username: ");
        String username = sc.nextLine();
        System.out.print("Password: ");
        String password = sc.nextLine();

        Utilizador uPrimeiro = new Utilizador(username, password);
        Utilizador userCriado = dao.Login(uPrimeiro);
        dao.mudarEstadoGestor(userCriado);

        Admin novoGestor = new Admin();
        novoGestor.setId(userCriado.getId());

        adminDao.criarGestor(novoGestor);
        System.out.println("Conta de Gestor criada com sucesso para o ID: " + userCriado.getId());
    }

    /**
     * Método para consultar os dados de um utilizador
     * <p>
     *     O método valida primeiro se o utilizador atual tem permissões (é um gestor) e tem sessão iniciada,
     *     de seguida pede ao gestor o id do utilizador que pretende consultar, procura os seus dados e imprime
     *     as suas informações detalhadas (nome, email e username) na consola caso o utilizador exista.
     * </p>
     * @param userLogado O objeto {@link Utilizador} representa a conta com sessao iniciada no momento que é utilizado
     * para verificar permissões
     * @param sc O objeto {@link Scanner} é responsavel por capturar a informação introduzida na consola e passar
     * para o metodo ({@code dao.ConsultarDadosGestor})
     * @throws SQLException Se existir algum erro na comunicação com a base de dados seja durante o query de pesquisa
     * ({@code adminDao.VerSeGestor}) e ({@code dao.ConsultarDadosGestor}).
     */
    public void ConsultarDadosGestor(Utilizador userLogado, Scanner sc) throws SQLException {
        if (userLogado == null) {
            System.out.println("Fazer login!!!");
            return;
        }
        if (!adminDao.VerSeGestor(userLogado.getId())) {
            System.out.println("So gestores podem fazer isto!!!!");
            return;
        }
        System.out.println("Id do utilizador a consultar dados");
        int id = sc.nextInt();
        Utilizador Ver = dao.ConsultarDadosGestor(id);
        if (Ver != null) {
            System.out.println("\n Dados Utilizador");
            System.out.println("Nome:" + Ver.getNome());
            System.out.println("Email:" + Ver.getEmail());
            System.out.println("username:" + Ver.getUsername());
        } else {
            System.out.println("Nao foi posssivel encontrar os dados");
        }

    }

    /**
     * Método para atualizar os dados de outros utilizadores
     * <p>
     *      O método valida primeiro se o utilizador atual tem permissões (é um gestor) e tem sessão iniciada,
     *      de seguida pede ao gestor o id da conta que deseja alterar e verifica se a mesma existe. Caso exista,
     *      apresenta um menu para escolher o dado a alterar (atualmente funcional para a atualização de email),
     *      valida o formato da nova informação e atualiza os dados na base de dados, mostrando as informações
     *      atualizadas da conta no final.
     * </p>
     * @param sc O objeto {@link Scanner} é responsável por capturar a informação introduzida na consola e passar
     * para os métodos ({@code dao.verSeContaExiste}), ({@code dao.ConsultarDadosGestor}) e ({@code dao.atualizarEmailGestor})
     * @param userLogado O objeto {@link Utilizador} representa a conta com sessão iniciada no momento utilizado
     * para verificar permissões
     * @throws SQLException Se existir algum erro na comunicação com a base de dados seja durante o query de pesquisa ou atualização
     * ({@code adminDao.VerSeGestor}), ({@code dao.verSeContaExiste}), ({@code dao.ConsultarDadosGestor}) e ({@code dao.atualizarEmailGestor}).
     */
    public void atualizarDadosGestor(Scanner sc, Utilizador userLogado) throws SQLException {

        if (userLogado == null || !adminDao.VerSeGestor(userLogado.getId())) {
            System.out.println("\nApenas Gestores podem fazer isto.");
            return;
        }

        System.out.println("\n--- Atualizar Dados de Outra Conta ---");
        System.out.print("Introduza o ID do utilizador que deseja alterar: ");
        int idAlvo = sc.nextInt();
        sc.nextLine();

        if (!dao.verSeContaExiste(idAlvo)) {
            System.out.println("\nErro: Não existe nenhum utilizador com o ID " + idAlvo);
            return;
        }

        Utilizador utilizadorAlvo = dao.ConsultarDadosGestor(idAlvo);
        System.out.println("\n[ A alterar dados de: " + utilizadorAlvo.getUsername() + " ]");
        System.out.println("1 - Atualizar password (Ainda nao funcional)");
        System.out.println("2 - Atualizar email(Funcional)");
        System.out.print("Escolha: ");
        int atualizar = sc.nextInt();
        sc.nextLine();

        switch (atualizar) {
            case 1:
                System.out.println("Funcionalidade ainda não implementada.");
                break;
            case 2:
                boolean emailValido = false;
                String novoEmail = "";

                while (!emailValido) {
                    System.out.print("Inserir novo email: ");
                    novoEmail = sc.nextLine();

                    if (EMAIL_PATTERN.matcher(novoEmail).matches()) {
                        emailValido = true;
                    } else {
                        System.out.println("Erro: formato de email inválido.");
                    }
                }

                Utilizador dadosNovos = new Utilizador();
                dadosNovos.setEmail(novoEmail);

                boolean atualizadoComSucesso = dao.atualizarEmailGestor(idAlvo, dadosNovos);

                if (atualizadoComSucesso) {
                    System.out.println("\n[SUCESSO] Dados atualizados com sucesso!");
                    Utilizador atualizado = dao.ConsultarDadosGestor(idAlvo);
                    System.out.println("Nome: " + atualizado.getNome());
                    System.out.println("Username: " + atualizado.getUsername());
                    System.out.println("Novo Email: " + atualizado.getEmail());
                } else {
                    System.out.println("\n[ERRO] Ocorreu um problema ao atualizar o email na base de dados.");
                }
                break;

            default:
                System.out.println("Opção inválida!");
                break;
        }
    }

    /**
     * Método para visualizar todos os utilizadores do sistema
     * <p>
     *     O método valida primeiro se o utilizador atual tem permissões (é um gestor) e tem sessão iniciada,
     *     de seguida invoca a base de dados para apresentar a lista de todos os utilizadores registados.
     * </p>
     * @param userLogado O objeto {@link Utilizador} representa a conta com sessão iniciada no momento utilizado
     * para verificar permissões
     * @throws SQLException Se existir algum erro na comunicação com a base de dados seja durante o query de pesquisa
     * ({@code adminDao.VerSeGestor}) e ({@code AdminDao.verUtilizadores}).
     */
    public void verUtilizador(Utilizador userLogado) throws SQLException {
        if (userLogado == null) {
            System.out.println("Fazer login!!!");
            return;
        }
        if (!adminDao.VerSeGestor(userLogado.getId())) {
            System.out.println("So gestores podem fazer isto!!!!");
            return;
        }
        AdminDao.verUtilizadores();
    }

    /**
     * Método para consultar os dados de um utilizador através do seu nome
     * <p>
     *     O método valida primeiro se o utilizador atual tem permissões (é um gestor) e tem sessão iniciada,
     *     de seguida pede ao gestor o nome do utilizador que pretende pesquisar, procura os seus dados e imprime
     *     as suas informações detalhadas (nome, username e email) na consola caso o utilizador exista
     * </p>
     * @param userLogado O objeto {@link Utilizador} representa a conta com sessao iniciada no momento utilizado
     * para verificar permissões
     * @param sc O objeto {@link Scanner} é responsável por capturar a informação introduzida na consola e passar
     * para o método ({@code dao.ConsultarNomeUtilizador})
     * @throws SQLException Se existir algum erro na comunicação com a base de dados seja durante o query de pesquisa
     * ({@code adminDao.VerSeGestor}) e ({@code dao.ConsultarNomeUtilizador}).
     */
    public void ConsultarNome(Utilizador userLogado, Scanner sc) throws SQLException {
        if (userLogado == null) {
            System.out.println("Fazer login!!!");
            return;
        }
        if (!adminDao.VerSeGestor(userLogado.getId())) {
            System.out.println("So gestores podem fazer isto!!!!");
            return;
        }
        System.out.println("Insira o nome para pesquisar: ");
        sc.nextLine();
        String nome = sc.nextLine();
        Utilizador Ver = dao.ConsultarNomeUtilizador(nome);
        if (Ver != null) {
            System.out.println("\n Dados Utilizador");
            System.out.println("Nome:" + Ver.getNome());
            System.out.println("Username:" + Ver.getUsername());
            System.out.println("Email:" + Ver.getEmail());
        } else {
            System.out.println("Nao foi posssivel encontrar os dados");
        }

    }

    /**
     * Método para consultar os dados de um utilizador através do seu username
     * <p>
     *     O método valida primeiro se o utilizador atual tem permissões (é um gestor) e tem sessão iniciada,
     *     de seguida pede ao gestor o username do utilizador que pretende pesquisar, procura os seus dados e imprime
     *     as suas informações detalhadas (nome, username e email) na consola caso o utilizador exista
     * </p>
     * @param userLogado O objeto {@link Utilizador} representa a conta com sessao iniciada no momento utilizado
     * para verificar permissões
     * @param sc O objeto {@link Scanner} é responsável por capturar a informação introduzida na consola e passar
     * para o método ({@code dao.ConsultarUsername})
     * @throws SQLException Se existir algum erro na comunicação com a base de dados seja durante o query de pesquisa
     * ({@code adminDao.VerSeGestor}) e ({@code dao.ConsultarUsername}).
     */
    public void ConsultarUsername(Utilizador userLogado, Scanner sc) throws SQLException {
        if (userLogado == null) {
            System.out.println("Fazer login!!!");
            return;
        }
        if (!adminDao.VerSeGestor(userLogado.getId())) {
            System.out.println("So gestores podem fazer isto!!!!");
            return;
        }
        System.out.println("Insira o username para pesquisar: ");
        sc.nextLine();
        String username = sc.nextLine();
        Utilizador Ver = dao.ConsultarUsername(username);
        if (Ver != null) {
            System.out.println("\n Dados Utilizador");
            System.out.println("Nome:" + Ver.getNome());
            System.out.println("Username:" + Ver.getUsername());
            System.out.println("Email:" + Ver.getEmail());
        } else {
            System.out.println("Nao foi posssivel encontrar os dados");
        }

    }

    /**
     * Método para consultar os dados de um utilizador através do seu email
     * <p>
     *     O método valida primeiro se o utilizador atual tem permissões (é um gestor) e tem sessão iniciada,
     *     de seguida pede ao gestor o email do utilizador que pretende pesquisar, procura os seus dados e imprime
     *     as suas informações detalhadas (nome, username e email) na consola caso o utilizador exista
     * </p>
     * @param userLogado O objeto {@link Utilizador} representa a conta com sessao iniciada no momento utilizado
     * para verificar permissões
     * @param sc O objeto {@link Scanner} é responsável por capturar a informação introduzida na consola e passar
     * para o método ({@code dao.ConsultarEmailUtilizador})
     * @throws SQLException Se existir algum erro na comunicação com a base de dados seja durante o query de pesquisa
     * ({@code adminDao.VerSeGestor}) e ({@code dao.ConsultarEmailUtilizador}).
     */
    public void ConsultarEmail(Utilizador userLogado, Scanner sc) throws SQLException {
        if (userLogado == null) {
            System.out.println("Fazer login!!!");
            return;
        }
        if (!adminDao.VerSeGestor(userLogado.getId())) {
            System.out.println("So gestores podem fazer isto!!!!");
            return;
        }
        System.out.println("Insira o email para pesquisar: ");
        sc.nextLine();
        String email = sc.nextLine();
        Utilizador Ver = dao.ConsultarEmailUtilizador(email);
        if (Ver != null) {
            System.out.println("\n Dados Utilizador");
            System.out.println("Nome:" + Ver.getNome());
            System.out.println("Username:" + Ver.getUsername());
            System.out.println("Email:" + Ver.getEmail());
        } else {
            System.out.println("Nao foi posssivel encontrar os dados");
        }

    }

    /**
     * Metodo para consultar os dados de uma reparação através do seu número
     * <p>
     *      O método valida primeiro se o utilizador atual tem permissões (é um gestor) e tem sessão iniciada,
     *      a seguir pede ao gestor o número (ID) da reparação que pretende pesquisar, procura os seus dados e imprime
     *      as suas informações detalhadas (número, data de início, data de fim e observação) na consola caso a reparação exista.
     * </p>
     * @param userLogado O objeto {@link Utilizador} representa a conta com sessão iniciada no momento utilizado
     * para verificar permissões
     * @param sc O objeto {@link Scanner} é responsável por capturar a informação introduzida na consola e passar
     * para o método ({@code adminDao.verReparacoes})
     * @throws SQLException Se existir algum erro na comunicação com a base de dados seja durante o query de pesquisa
     * ({@code adminDao.VerSeGestor}) e ({@code adminDao.verReparacoes}).
     */
    public void ConsultarReparacao(Utilizador userLogado, Scanner sc) throws SQLException {
        if (userLogado == null) {
            System.out.println("Fazer login!!!");
            return;
        }
        if (!adminDao.VerSeGestor(userLogado.getId())) {
            System.out.println("So gestores podem fazer isto!!!!");
            return;
        }
        System.out.println("Insira o numero da reparacao para pesquisar: ");
        sc.nextLine();
        int idR = sc.nextInt();
        Reparacao Ver = adminDao.verReparacoes(idR);
        if (Ver != null) {
            System.out.println("\n Dados da Reparacao");
            System.out.println("Numero: " + Ver.getIdR());
            System.out.println("Data: " + Ver.getDataInicio());
            System.out.println("Data fim: " + Ver.getDataFim());
            System.out.println("Observacao: " + Ver.getObservacao());
        } else {
            System.out.println("Nao foi posssivel encontrar os dados");
        }
    }

    /**
     * Metodo para pesquisar e consultar os dados de um equipamento através do seu número
     * <p>
     *      O metodo valida primeiro se o utilizador atual tem permissões (é um gestor) e tem sessão iniciada,
     *      a seguir pede ao gestor o número (ID) do equipamento que pretende pesquisar, procura os seus dados e imprime
     *      as suas informações detalhadas (número, marca, modelo, SKU, data de submissão e data de reparação) na consola caso o equipamento exista.
     * </p>
     * @param userLogado O objeto {@link Utilizador} representa a conta com sessão iniciada no momento utilizado
     * para verificar permissões
     * @param sc O objeto {@link Scanner} é responsavel por capturar a informação introduzida na consola e passar
     * para o método ({@code adminDao.perquisarEquipamento})
     * @throws SQLException Se existir algum erro na comunicação com a base de dados seja durante o query de pesquisa
     * ({@code adminDao.VerSeGestor}) e ({@code adminDao.perquisarEquipamento}).
     */
    public void PesquisarEquipamento(Utilizador userLogado, Scanner sc) throws SQLException {
        if (userLogado == null) {
            System.out.println("Fazer login!!!");
            return;
        }
        if (!adminDao.VerSeGestor(userLogado.getId())) {
            System.out.println("So gestores podem fazer isto!!!!");
            return;
        }
        System.out.println("Insira o numero do equipamento para pesquisar: ");
        sc.nextLine();
        int idEquip = sc.nextInt();
        Equipamento Ver = adminDao.perquisarEquipamento(idEquip);
        if (Ver != null) {
            System.out.println("\n Dados da Reparacao");
            System.out.println("Numero: " + Ver.getIdEquipamento());
            System.out.println("Marca: " + Ver.getMarca());
            System.out.println("Modelo: " + Ver.getModelo());
            System.out.println("SKU: " + Ver.getSKU());
            System.out.println("Data: " + Ver.getDataSubmissao());
            System.out.println("Data fim: " + Ver.getDataReparacao());
        } else {
            System.out.println("Nao foi posssivel encontrar os dados");
        }
    }

}