package model.dao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Utilizador;
import model.db.DBConnection;


public class UtilizadoresDAO {
    /**
     * Regista um novo utilizador na base de dados.
     * <p>
     *      O processo começa por preparar uma instrução de inserção SQL para armazenar as informações
     *      principais do perfil (nome, username, password e email) na tabela correspondente. Durante a
     *      inserção, o estado da conta é definido automaticamente como 0 (a espera de aprovacao de um gestor).
     *      O método recorre ao padrão try-with-resources para garantir
     *      o fecho automático da ligação à base de dados. Caso os dados fornecidos violem restrições únicas
     *      (como a tentativa de registar um username ou email já existentes), o sistema emite um aviso na consola
     *      e lança uma exceção para interromper a execução.
     * </p>
     * @param u O objeto {@link Utilizador} que contém os dados pessoais e as credenciais a registar.
     * @return {@code true} se a inserção do novo utilizador for concluída com sucesso.
     * @throws RuntimeException Se ocorrer algum erro na manipulação da base de dados envia {@link SQLException}.
     */
    public boolean RegistarUtilizador(Utilizador u ) {
        String sql = "Insert into utilizador (nome,username,password,estado,email) values (?,?,?,0,?)";

        // o que for feito dentro do try é fechado automaticamente ()
        // falta fazer um select para ver se o username e email ja existem e verificar o email
        try (Connection conn = DBConnection.getconn();
             PreparedStatement ps = conn.prepareStatement(sql))  {

            ps.setString(1, u.getNome());
            ps.setString(2, u.getUsername());
            ps.setString(3, u.getPassword());
            ps.setString(4, u.getEmail());

            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println("Dados inseridos invalidos ou já existe um utilizador com os dados inseridos");
            throw new RuntimeException(e);
        }
    }

    /**
     * Login.
     * <p>
     *      O processo começa por executar uma consulta à base de dados na tabela "utilizador",
     *      procurando por um registo exato que corresponda ao nome de utilizador (username) e
     *      à palavra-passe (password) fornecidos. Se as credenciais estiverem corretas e o utilizador
     *      for encontrado, o método constrói um novo objeto {@link Utilizador} e preenche-o com
     *      as informações essenciais da conta (identificador, nome, username, password, email e estado),
     *      devolvendo este objeto que servirá para manter a sessão ativa. Caso as credenciais não
     *      coincidam com nenhum registo, a autenticação falha e o método retorna {@code null}.
     * </p>
     * @param u O objeto {@link Utilizador} que contém as credenciais introduzidas (username e password) a serem validadas na base de dados.
     * @return Um objeto {@link Utilizador} preenchido com os dados do perfil em caso de sucesso, ou {@code null} se o login falhar.
     * @throws SQLException Se ocorrer algum erro declarado na assinatura do método.
     * @throws RuntimeException Se ocorrer algum erro na manipulação da base de dados envia {@link SQLException}.
     */
    public Utilizador Login(Utilizador u) throws SQLException {
        String sql = "SELECT id,nome, username, password, email, estado FROM utilizador WHERE username = ? AND password = ?";

        try (Connection conn = DBConnection.getconn();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, u.getUsername());
            ps.setString(2, u.getPassword()); // ideal: comparar hash

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Utilizador logado = new Utilizador();
                logado.setNome(rs.getString("nome"));
                logado.setUsername(rs.getString("username"));
                logado.setPassword(rs.getString("password"));
                logado.setEmail(rs.getString("email"));
                logado.setEstado(rs.getInt("estado"));
                logado.setId(rs.getInt("id"));
                return logado;
            } else {
                return null;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Consulta e recupera os dados atualizados do perfil de um utilizador.
     * <p>
     *      O processo começa por executar uma consulta à base de dados na tabela "utilizador",
     *      procurando pelo registo que corresponda ao nome de utilizador (username) da conta
     *      fornecida. Se o registo for encontrado, o método constrói e devolve um novo objeto
     *      com as informações email e estado).
     * </p>
     * @param userLogado O objeto {@link Utilizador} que representa o utilizador atual e que contém o username a ser pesquisado na base de dados.
     * @return Um novo objeto {@link Utilizador} com os dados recuperados da base de dados, ou o objeto original se o registo não for encontrado.
     * @throws RuntimeException Se ocorrer algum erro na manipulação da base de dados envia {@link SQLException}.
     */
    public Utilizador ConsultarDados(Utilizador userLogado) {
        String sql = "Select nome, username, password , email , estado from utilizador where username = ?";

        try (Connection conn = DBConnection.getconn();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1,userLogado.getUsername());


            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Utilizador u = new Utilizador();
                u.setNome(rs.getString("nome"));
                u.setUsername(rs.getString("username"));
                u.setPassword(rs.getString("password"));
                u.setEmail(rs.getString("email"));
                u.setEstado(rs.getInt("estado"));
                return u;

            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return userLogado;
    }


    /**
     * Consulta os dados de qualquer conta de utilizador através do seu identificador.
     * <p>
     *      O processo começa por executar uma consulta à base de dados na tabela "utilizador",
     *      procurando pelo registo que corresponda ao identificador único (id) fornecido.
     *      Esta funcionalidade destina-se a perfis com privilégios de gestão, permitindo
     *      a verificação ou auditoria de qualquer conta registada no sistema. Se o registo
     *      for encontrado, o método constrói um novo objeto {@link Utilizador} e preenche-o
     *      com as informações essenciais do perfil (nome, username, password, email e estado),
     *      devolvendo este objeto. Caso a consulta não retorne resultados, o método devolve {@code null}.
     * </p>
     * @param id O identificador único do utilizador a ser pesquisado na base de dados.
     * @return Um objeto {@link Utilizador} com os dados recuperados da base de dados, ou {@code null} caso o registo não exista.
     * @throws RuntimeException Se ocorrer algum erro na manipulação da base de dados envia {@link SQLException}.
     */
    public Utilizador ConsultarDadosGestor(int id) {
        String sql = "Select nome, username, password , email , estado from utilizador where id = ?";

        try (Connection conn = DBConnection.getconn();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Utilizador Dados = new Utilizador();
                Dados.setNome(rs.getString("nome"));
                Dados.setUsername(rs.getString("username"));
                Dados.setPassword(rs.getString("password"));
                Dados.setEmail(rs.getString("email"));
                Dados.setEstado(rs.getInt("estado"));
                return Dados;

            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null ;
    }


    /**
     * Consulta as informações básicas de um utilizador através do seu nome.
     * <p>
     *      O processo começa por executar uma consulta à base de dados na tabela "utilizador",
     *      procurando pelo registo que corresponda de forma exata ao nome fornecido. Se o
     *      registo for encontrado, o método constrói um novo objeto {@link Utilizador} e
     *      preenche-o apenas com as informações essenciais de identificação e contacto
     *      (nome, username e email), devolvendo este objeto. Caso a consulta não retorne
     *      nenhum resultado, o método conclui a operação devolvendo {@code null}.
     * </p>
     * @param nome O nome completo ou designação do utilizador a ser pesquisado na base de dados.
     * @return Um objeto {@link Utilizador} com os dados recuperados da base de dados, ou {@code null} caso o registo não exista.
     * @throws RuntimeException Se ocorrer algum erro na manipulação da base de dados envia {@link SQLException}.
     */
    public Utilizador ConsultarNomeUtilizador(String nome) {
        String sql = "Select nome, username, email from utilizador where nome = ?";

        try (Connection conn = DBConnection.getconn();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, nome);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Utilizador Dados = new Utilizador();
                Dados.setNome(rs.getString("nome"));
                Dados.setUsername(rs.getString("username"));
                Dados.setEmail(rs.getString("email"));
                return Dados;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null ;
    }

    /**
     * Consulta as informações básicas de um utilizador através do seu username.
     * <p>
     *      O processo começa por executar uma consulta à base de dados na tabela "utilizador",
     *      procurando pelo registo que corresponda de forma exata ao username fornecido. Se o
     *      registo for encontrado, o método constrói um novo objeto {@link Utilizador} e
     *      preenche-o apenas com as informações essenciais de identificação e contacto
     *      (nome, username e email), devolvendo este objeto. Caso a consulta não retorne
     *      nenhum resultado, o método conclui a operação devolvendo {@code null}.
     * </p>
     * @param username O nome completo ou designação do utilizador a ser pesquisado na base de dados.
     * @return Um objeto {@link Utilizador} com os dados recuperados da base de dados, ou {@code null} caso o registo não exista.
     * @throws RuntimeException Se ocorrer algum erro na manipulação da base de dados envia {@link SQLException}.
     */
    public Utilizador ConsultarUsername(String username) {
        String sql = "Select nome, username, email from utilizador where username = ?";

        try (Connection conn = DBConnection.getconn();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Utilizador Dados = new Utilizador();
                Dados.setNome(rs.getString("nome"));
                Dados.setUsername(rs.getString("username"));
                Dados.setEmail(rs.getString("email"));
                return Dados;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null ;
    }

    /**
     * Consulta as informações básicas de um utilizador através do seu email.
     * <p>
     *      O processo começa por executar uma consulta à base de dados na tabela "utilizador",
     *      procurando pelo registo que corresponda de forma exata ao email fornecido. Se o
     *      registo for encontrado, o método constrói um novo objeto {@link Utilizador} e
     *      preenche-o apenas com as informações essenciais de identificação e contacto
     *      (nome, username e email), devolvendo este objeto. Caso a consulta não retorne
     *      nenhum resultado, o método conclui a operação devolvendo {@code null}.
     * </p>
     * @param email O nome completo ou designação do utilizador a ser pesquisado na base de dados.
     * @return Um objeto {@link Utilizador} com os dados recuperados da base de dados, ou {@code null} caso o registo não exista.
     * @throws RuntimeException Se ocorrer algum erro na manipulação da base de dados envia {@link SQLException}.
     */
    public Utilizador ConsultarEmailUtilizador(String email) {
        String sql = "Select nome, username, email from utilizador where email = ?";

        try (Connection conn = DBConnection.getconn();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Utilizador Dados = new Utilizador();
                Dados.setNome(rs.getString("nome"));
                Dados.setUsername(rs.getString("username"));
                Dados.setEmail(rs.getString("email"));
                return Dados;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null ;
    }

    /**
     * Atualiza as credenciais e informações de contacto de um utilizador autenticado.
     * <p>
     *      O processo começa por verificar se o nome de utilizador (username) da conta
     *      com sessão iniciada corresponde ao nome de utilizador dos novos dados fornecidos.
     *      Se os identificadores não coincidirem, a ação é imediatamente bloqueada e é lançada
     *      uma exceção de segurança, garantindo que um utilizador apenas possa alterar o seu
     *      próprio perfil. Ultrapassada esta validação de segurança, o método executa uma instrução
     *      de atualização na base de dados, modificando a palavra-passe e o endereço de email
     *      vinculados a essa conta específica.
     * </p>
     * @param userLogado O objeto {@link Utilizador} que representa a conta atualmente com sessão iniciada.
     * @param dadosNovos O objeto {@link Utilizador} contendo as novas informações (palavra-passe e email) a serem gravadas.
     * @return {@code true} se a atualização dos dados for concluída com sucesso na base de dados.
     * @throws SecurityException Se o utilizador tentar modificar os dados de uma conta diferente da sua.
     * @throws RuntimeException Se ocorrer algum erro na manipulação da base de dados envia {@link SQLException}.
     */
    public boolean AtualizarDados(Utilizador userLogado, Utilizador dadosNovos) {
        if (!userLogado.getUsername().equals(dadosNovos.getUsername())) {
            throw new SecurityException("Ação proibida!!");
        }
        String sql = "UPDATE utilizador SET password = ?, email = ?  WHERE username = ?";


        try (Connection conn = DBConnection.getconn();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1,dadosNovos.getPassword());
            ps.setString(2,dadosNovos.getEmail());
            ps.setString(3,dadosNovos.getUsername());
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return true;
    }

    /**
     * Inicia o processo de eliminação de uma conta de utilizador através da alteração do seu estado.
     * <p>
     O processo começa por verificar se o identificador fornecido corresponde ao identificador da
     conta com sessão iniciada. Se os identificadores não coincidirem, a ação é imediatamente bloqueada
     e é lançada uma exceção de segurança, garantindo que um utilizador apenas possa solicitar a
     eliminação da sua própria conta. Ultrapassada esta validação de segurança, o método executa
     uma instrução de atualização na base de dados, alterando o estado do utilizador para 3 (indicando
     que o pedido para apagar a conta foi iniciado).
     * </p>
     * @param userLogado O objeto {@link Utilizador} que representa a conta atualmente com sessão iniciada.
     * @param id O identificador único do utilizador cuja conta se pretende apagar.
     * @return {@code true} se o estado da conta for atualizado com sucesso na base de dados, {@code false} caso contrário.
     * @throws SecurityException Se o utilizador tentar apagar uma conta diferente da sua.
     * @throws RuntimeException Se ocorrer algum erro na manipulação da base de dados envia {@link SQLException}.
     */
    public boolean ApagarContaUtilizador(Utilizador userLogado,int id) {

        if (userLogado.getId() != id) {
            throw new SecurityException("Acao proibida!!");

    }
        String sql = "UPDATE utilizador SET estado = 3 WHERE id = ?";

        try (Connection conn = DBConnection.getconn();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1,id);
            int apagarConta = ps.executeUpdate();
            return apagarConta > 0;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Executa a eliminação administrativa e a anonimização dos dados de uma conta de utilizador.
     * <p>
     *      O processo consiste em efetuar uma eliminicação e ofuscação dos dados
     *      pessoais do utilizador. O método executa uma instrução de atualização na base de dados,
     *      alterando o estado da conta para 4 (indica que foi apagada).
     *      Simultaneamente, para garantir a privacidade e cumprir com as normas
     *      de proteção de dados, as informações sensíveis (nome, nome de utilizador e email) são
     *      substituídas por valores genéricos concatenados com o identificador único do perfil.
     *      Esta abordagem preserva a integridade referencial do sistema (como o histórico de ações e
     *      reparações associadas ao identificador) sem reter os dados reais e identificáveis da pessoa.
     * </p>
     * @param id O identificador único do utilizador cuja conta será apagada e anonimizada.
     * @throws RuntimeException Se ocorrer algum erro na manipulação da base de dados envia {@link SQLException}.
     */
    public void ApagarContaAdmin(int id) {

        String sql = "UPDATE utilizador SET estado = 4, nome = ?, username = ?, email = ? WHERE id = ?";

        try (Connection conn = DBConnection.getconn();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, "apagadoNome" + id);

            ps.setString(2, "apagadoUsername" + id);

            ps.setString(3, "apagadoEmail" + id + "@email.apagado");

            ps.setInt(4, id);

            ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Erro na base de dados ao apagar conta: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * Atualiza o endereço de email de uma conta de utilizador através de privilégios de gestão.
     * <p>
     *      O processo começa por preparar uma instrução de atualização na base de dados focada
     *      em modificar exclusivamente o endereço de email correspondente ao identificador (id)
     *      fornecido. Esta funcionalidade é concebida para ser utilizada por gestores ou
     *      administradores, permitindo-lhes a correção ou alteração dos dados de contacto de
     *      qualquer conta registada no sistema. O método executa a operação através de um
     *      {@link PreparedStatement} para garantir a segurança da instrução SQL e verifica se
     *      a atualização teve impacto em alguma linha da tabela, confirmando assim o sucesso da operação.
     * </p>
     * @param id O identificador único do utilizador cujo endereço de email será modificado.
     * @param dadosNovos O objeto {@link Utilizador} que contém o novo endereço de email a ser gravado na conta especificada.
     * @return {@code true} se o endereço de email for atualizado com sucesso na base de dados, {@code false} caso o identificador não seja encontrado.
     * @throws RuntimeException Se ocorrer algum erro na manipulação da base de dados envia {@link SQLException}.
     */
    public boolean atualizarEmailGestor(int id, Utilizador dadosNovos) {
        String sql = "UPDATE utilizador SET email = ? WHERE id = ?";

        try (Connection conn = DBConnection.getconn();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, dadosNovos.getEmail());

            ps.setInt(2, id);

            int linhasAfetadas = ps.executeUpdate();

            return linhasAfetadas > 0;

        } catch (SQLException e) {
            System.out.println("Erro ao tentar atualizar o email na base de dados.");
            throw new RuntimeException(e);
        }
    }

    /**
     * Ativa automaticamente a conta de um utilizador com perfil de gestor.
     * <p>
     *      O processo executa uma instrução de atualização na base de dados para alterar o estado
     *      da conta para 1 (ativa), utiliza o nome de utilizador (username) da conta fornecida.
     *      Esta funcionalidade é concebida para ser utilizada principalmente durante a primeira execução
     *      do programa (setup inicial), garantindo que a conta do administrador principal (gestor)
     *      fique imediatamente operacional, sem a necessidade de passar por um processo de aprovação manual.
     * </p>
     * @param userLogado O objeto {@link Utilizador} que representa a conta do gestor recém-criada e a ser ativada.
     * @throws SQLException Se ocorrer algum erro na manipulação da base de dados durante a ligação ou execução da instrução SQL.
     */
    public void mudarEstadoGestor(Utilizador userLogado) throws SQLException {
        String sql = "UPDATE utilizador SET estado = 1 WHERE username = ?";

        try (Connection conn = DBConnection.getconn();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1,userLogado.getEstado());
            ps.setString(1, userLogado.getUsername());
            ps.executeUpdate();
    }
    }

    /**
     * Altera o estado de uma conta de utilizador para ativo através do seu identificador.
     * <p>
     *      O processo executa uma instrução de atualização na base de dados para modificar o estado
     *      da conta para 1 (ativa), utilizando o identificador único (id) fornecido. Esta funcionalidade
     *      é utilizada por administradores/gestores aprovarem e desbloquear contas de
     *      utilizadores que se encontravam inativas, permitindo-lhes assim o acesso
     *      normal às funcionalidades do sistema.
     * </p>
     * @param id O identificador único do utilizador cuja conta será ativada.
     * @throws SQLException Se ocorrer algum erro na manipulação da base de dados durante a ligação ou execução da instrução SQL.
     */
    public void mudarEstado(int id) throws SQLException {
        String sql = "UPDATE utilizador SET estado = 1 WHERE id = ?";

        try (Connection conn = DBConnection.getconn();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();

        }
    }

    /**
     * Consulta e exibe a lista de contas de utilizador que se encontram pendentes de ativação.
     * <p>
     *      O processo começa por executar uma consulta à base de dados na tabela "utilizador",
     *      procurando por todos os registos cujo estado seja igual a 0 (indica
     *      que a conta está inativa). Para cada registo encontrado, o
     *      método constrói um novo objeto {@link Utilizador} e preenche-o com as informações
     *      essenciais (nome de utilizador e identificador único), adicionando-o a uma lista.
     *      No final, a lista completa é impressa na consola, permitindo aos gestores visualizarem rapidamente os perfis que necessitam de intervenção para serem ativados.
     * </p>
     * @throws RuntimeException Se ocorrer algum erro na manipulação da base de dados envia {@link SQLException}.
     */
    public void verContasPorAtivar() {
        String sql = "Select * from utilizador where estado = 0";
        List<Utilizador> lista = new ArrayList<>();
        try (Connection conn = DBConnection.getconn();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Utilizador u = new Utilizador();
                u.setUsername(rs.getString("username"));
                u.setId(rs.getInt("id"));
                lista.add(u);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Lista de utilizadores: " + lista);
    }

    /**
     * Consulta e exibe a lista de contas de utilizador que se encontram pendentes de apagar.
     * <p>
     O processo começa por executar uma consulta à base de dados na tabela "utilizador",
     procurando por todos os registos cujo estado seja igual a 3 (indica que a conta pediu para ser apagada).
     Para cada registo encontrado, o método constrói um novo objeto {@link Utilizador} e preenche-o com as informações
     essenciais (nome de utilizador e identificador único), adicionando-o a uma lista.
     No final, a lista completa é impressa na consola, permitindo aos gestores visualizarem rapidamente os perfis que necessitam de intervenção para serem desativados.
     * </p>
     * @throws RuntimeException Se ocorrer algum erro na manipulação da base de dados envia {@link SQLException}.
     */
    public void verContasPorApagar() {
        String sql = "Select * from utilizador where estado = 3";
        List<Utilizador> lista = new ArrayList<>();
        try (Connection conn = DBConnection.getconn();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Utilizador u = new Utilizador();
                u.setUsername(rs.getString("username"));
                u.setId(rs.getInt("id"));
                lista.add(u);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Lista de utilizadores: " + lista);
    }

    /**
     * Verifica a existência de uma conta de utilizador através do seu identificador.
     * <p>
     *      O processo começa por executar uma consulta à base de dados na tabela "utilizador",
     *      procurando por um registo que corresponda ao identificador único (id) fornecido.
     *      Se a consulta retornar algum resultado, o método confirma a existência da conta,
     *      alterando a variável de controlo interna e devolvendo {@code true}. Caso contrário,
     *      se o identificador não for encontrado, o método conclui a operação devolvendo {@code false}.
     *      Esta validação prévia é fundamental para garantir que operações subsequentes apenas
     *      sejam aplicadas a contas válidas e existentes no sistema.
     * </p>
     * @param id O identificador único da conta de utilizador a ser verificada na base de dados.
     * @return {@code true} se a conta existir, {@code false} caso não seja encontrado nenhum registo com esse identificador.
     * @throws SQLException Se ocorrer algum erro na manipulação da base de dados durante a ligação ou execução da instrução SQL.
     */
    public boolean verSeContaExiste(int id) throws SQLException {
        String sql = "Select * from utilizador WHERE id = ?";
        boolean contaExiste = false;

        try (Connection conn = DBConnection.getconn();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                contaExiste = true;
            }

        }
        return contaExiste;
    }


}




