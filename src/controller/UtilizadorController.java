package controller;


import java.sql.SQLException;
import java.util.Scanner;
import java.util.regex.Pattern; //importação para a validação do email

import model.Utilizador;
import model.dao.UtilizadoresDAO;

public class UtilizadorController {

    private final UtilizadoresDAO dao = new UtilizadoresDAO();



    //email
    private static final String EMAIL_REGEX = "^[^@\\s]+@[^@\\s]+\\.[^@\\s]{2,}$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

    /**
     * Metodo para realizar o registo completo de um novo utilizador no sistema
     * <p>
     *      O metodo guia o utilizador através de um processo de dois passos. No primeiro passo, recolhe os dados
     *      básicos (nome, username, password e email), validando o formato do email através de um regex.
     *      Se os dados forem inseridos com sucesso na base de dados, o metodo avança para o segundo passo, onde
     *      solicita a definição do tipo de conta (Funcionário ou Cliente). Consoante a escolha, o utilizador é
     *      autenticado automaticamente e redirecionado para o respetivo controlador para concluir o registo
     *      de detalhes específicos do perfil.
     * </p>
     * @param sc O objeto {@link Scanner} é responsavel por capturar a informação introduzida na consola e passar
     * para a criação do objeto {@link Utilizador} e para os controladores de perfil
     * @throws SQLException Se existir algum erro na comunicação com a base de dados seja durante o registo inicial
     * ({@code dao.RegistarUtilizador}), autenticação automática ({@code dao.Login}) ou nos registos de perfil
     * ({@code criarFuncionario} ou {@code criarCliente}).
     */
    public void registar(Scanner sc) throws SQLException {

        System.out.println("\n--- Registo de Utilizador ---");

        System.out.print("Nome: ");
        String nome = sc.nextLine();

        System.out.print("Username: ");
        String username = sc.nextLine();

        System.out.print("Password: ");
        String password = sc.nextLine();

        String email = "";
        boolean emailValido = false;

        /*
          Logica de validacao de enderecos de email em Java.
          Solucao adaptada de: Nicolas Rio
          Fonte: https://www.abstractapi.com/guides/api-functions/email-validation-in-java
          Acedido em: Dezasseis de Abril de dois mil e vinte e seis.
         */
        while (!emailValido) {
            System.out.print("Email: ");
            email = sc.nextLine();

            if (EMAIL_PATTERN.matcher(email).matches()) {
                emailValido = true;
            } else {
                System.out.println("Erro: O email deve apresentar um formato válido e obrigatório ([designação] @ [entidade] . [domínio]).");
            }
        }

        Utilizador u = new Utilizador(nome, username, password, email);

        boolean sucesso = dao.RegistarUtilizador(u);

        if (sucesso) {
            System.out.println("Dados inseridos com sucesso");
            System.out.println("\nPASSO 2 - Registo ");
            System.out.println("\n 2 Associar utilizador como cliente");
            System.out.println("\n 1 Associar utilizador como funcionario");
            int tipoDeConta;
            Utilizador userLogado;
            tipoDeConta = sc.nextInt();
            switch (tipoDeConta) {
                case 1:
                    Utilizador l = new Utilizador(username, password);
                    userLogado = dao.Login(l);
                    FuncionarioController.criarFuncionario(sc,userLogado);
                    break;
                case 2:
                    Utilizador x = new Utilizador(username, password);
                    userLogado = dao.Login(x);
                    ClienteController.criarCliente(sc,userLogado);
                    break;
            }
        } else {
            System.out.println("Erro no registo.");
        }
    }
    /**
     * Metodo para realizar o registo inicial de uma conta de gestor no sistema
     * <p>
     *      O metodo recolhe as informações básicas necessárias para a criação de um perfil de utilizador
     *      (nome, username, password e email). Inclui uma validação rigorosa do formato do email através
     *      de uma expressão regular. Após a validação e recolha dos dados, o novo utilizador é inserido
     *      na base de dados. Este metodo foca-se no registo da entidade Utilizador, sendo geralmente
     *      o primeiro passo antes da atribuição de privilégios de administração.
     * </p>
     * @param sc O objeto {@link Scanner} é responsavel por capturar a informação introduzida na consola e passar
     * para a criação do objeto {@link Utilizador}
     * @throws SQLException Se existir algum erro na comunicação com a base de dados durante o processo
     * de inserção do utilizador ({@code dao.RegistarUtilizador}).
     */
    public void registarG(Scanner sc) throws SQLException {

        System.out.println("\n--- Registo de Utilizador ---");

        System.out.print("Nome: ");
        sc.nextLine();
        String nome = sc.nextLine();

        System.out.print("Username: ");
        String username = sc.nextLine();

        System.out.print("Password: ");
        String password = sc.nextLine();

        String email = "";
        boolean emailValido = false;

        //BASEADO NESTA IMPLEMENTAÇÃO: https://www.abstractapi.com/guides/api-functions/email-validation-in-java
        while (!emailValido) {
            System.out.print("Email: ");
            email = sc.nextLine();

            if (EMAIL_PATTERN.matcher(email).matches()) {
                emailValido = true;
            } else {
                System.out.println("Erro: O email deve apresentar um formato válido e obrigatório ([designação] @ [entidade] . [domínio]).");
            }
        }

        Utilizador u = new Utilizador(nome, username, password, email);

        boolean sucesso = dao.RegistarUtilizador(u);

        if (sucesso) {
            System.out.println("Dados inseridos com sucesso");
        } else {
            System.out.println("Erro no registo.");
        }
    }

    /**
     * Metodo para realizar a autenticação (Login) de um utilizador no sistema
     * <p>
     *      O metodo executa um ciclo que solicita as credenciais (username e password) ao utilizador
     *      até que uma autenticação seja bem-sucedida. Após a validação dos dados na base de dados,
     *      verifica o estado da conta; se a conta não estiver ativa (estado diferente de 1), informa
     *      o utilizador para contactar um gestor. O metodo retorna o objeto do utilizador autenticado
     *      para que possa ser utilizado como a sessão atual do sistema.
     * </p>
     * @param sc O objeto {@link Scanner} é responsavel por capturar as credenciais introduzidas na consola
     * @return O objeto {@link Utilizador} correspondente à conta autenticada, ou mantém o ciclo até
     * obter um login válido
     * @throws SQLException Se existir algum erro na comunicação com a base de dados durante o processo
     * de verificação de credenciais ({@code dao.Login}).
     */
    public Utilizador Login(Scanner sc) throws SQLException {
        Utilizador logado = null;

        while (logado == null) {
            System.out.println("\n--- Login ---");
            System.out.print("Username: ");
            String username = sc.nextLine();
            System.out.print("Password: ");
            String password = sc.nextLine();

            Utilizador u = new Utilizador(username, password);
            logado = dao.Login(u);

            if (logado != null) {
                System.out.println("\nBem-vindo " + logado.getUsername() + ".\n");
                if (logado.getEstado() != 1) {
                    System.out.println("Erro: Conta ainda nao ativada pelo gestor contacte um gestor");
                } }
            else {
                System.out.println("Erro dados invalidos ou conta foi apagada contacte um gestor");

            }
        }

        return logado;
    }

    /**
     * Metodo para realizar o encerramento da sessão (Logout) do utilizador
     * <p>
     *      O metodo verifica se existe uma sessão ativa através do objeto de utilizador fornecido.
     *      Caso exista um utilizador logado, exibe uma mensagem de despedida personalizada e
     *      retorna {@code null} para invalidar a referência da sessão atual no sistema.
     *      Se não houver uma sessão ativa, informa que não existe nenhuma sessão para encerrar.
     * </p>
     * @param logado O objeto {@link Utilizador} que representa a sessão atual que se pretende encerrar
     * @return Retorna {@code null} para garantir que a variável que armazena o utilizador logado seja limpa
     */
    public Utilizador Logout(Utilizador logado) {
        if (logado != null){
            System.out.println("\nAdeus " + logado.getUsername() + ".\n");
        } else {
            System.out.println("\nErro: Não existe nenhuma sessão para encerrar.");
        }
        return null;
    }


    /**
     * Metodo para consultar e apresentar os dados do perfil do utilizador atual
     * <p>
     O metodo solicita à base de dados as informações detalhadas do utilizador com sessão iniciada.
     Caso os dados sejam recuperados com sucesso e a conta esteja ativa (estado igual a 1),
     imprime no ecrã o nome, o email e o nome de utilizador (username). Se a conta não estiver
     ativa ou os dados não forem encontrados, informa o utilizador sobre a impossibilidade
     de aceder à informação.
     * </p>
     * @param logado O objeto {@link Utilizador} que representa a conta com sessão iniciada,
     * utilizado como referência para a consulta na base de dados
     * @return O objeto {@link Utilizador} contendo os dados carregados a partir da base de dados
     */
    public Utilizador ConsultarDados(Utilizador logado) {
        Utilizador u = dao.ConsultarDados(logado);

        assert u != null;
        if (u.getEstado() == 1){
            System.out.println("\n Dados Utilizador");
            System.out.println("Nome:" + u.getNome());
            System.out.println("Email:" + u.getEmail());
            System.out.println("username:" + u.getUsername());
        } else {
            System.out.println("Nao foi posssivel encontrar os dados");
        }


        return u;
    }


    /**
     * Metodo para atualizar as informações de perfil (password ou email) do utilizador
     * <p>
     *      O metodo permite ao utilizador optar por alterar a sua password ou o seu endereço de email.
     *      Caso a escolha seja o email, o sistema aplica uma validação de formato através de um regex
     *      regular para garantir a integridade do dado. Após a recolha da nova informação, os dados são
     *      guardados na base de dados. Em caso de sucesso, o metodo consulta e exibe os dados atualizados
     *      para confirmação visual do utilizador.
     * </p>
     * @param sc O objeto {@link Scanner} é responsavel por capturar a opção de menu e os novos dados
     * introduzidos pelo utilizador
     * @param logado O objeto {@link Utilizador} que representa a sessão atual, servindo de base para
     * identificar o registo a ser alterado e manter os dados que não serão modificados
     */
    public void atualizarDados(Scanner sc, Utilizador logado) {
        int atualizar;
        boolean atualizadoComSucesso;
        System.out.println("\n--- Atualizar Dados ---");
        System.out.println("1 - Atualizar password");
        System.out.println("2 - Atualizar email");
        System.out.print("Escolha: ");
        atualizar = sc.nextInt();
        sc.nextLine();

        String novaPassword = logado.getPassword();
        String novoEmail = logado.getEmail();

        switch (atualizar) {
            case 1:
                System.out.print("Inserir nova password: ");
                novaPassword = sc.nextLine();
                break;

            case 2:
                boolean emailValido = false;
                while (!emailValido) {
                    System.out.print("Inserir novo email: ");
                    novoEmail = sc.nextLine();

                    if (EMAIL_PATTERN.matcher(novoEmail).matches()) {
                        emailValido = true;
                    } else {
                        System.out.println("Erro: email inválido.");
                    }
                }
                break;

            default:
                System.out.println("Opção inválida!");
                return;
        }

        // Criar objeto com os novos dados
        Utilizador dadosNovos = new Utilizador(
                logado.getNome(),
                logado.getUsername(),
                novaPassword,
                novoEmail
        );

        atualizadoComSucesso = dao.AtualizarDados(logado, dadosNovos);

        if (atualizadoComSucesso) {
            System.out.println("Dados atualizados com sucesso!");
            // Consultar e mostrar os dados atualizados
            Utilizador atualizado = dao.ConsultarDados(logado);
            if (atualizado != null) {
                System.out.println("Nome: " + atualizado.getNome());
                System.out.println("Username: " + atualizado.getUsername());
                System.out.println("Email: " + atualizado.getEmail());
            }
        } else {
            System.out.println("Erro ao atualizar dados.");
        }
    }
    }



