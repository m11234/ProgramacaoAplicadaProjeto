package model.dao;

import model.Reparacao;
import model.Utilizador;
import model.db.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class ReparacaoDAO {
    /**
     * Metodo para registar a submissão de uma nova reparação na base de dados
     * <p>
     *      O metodo recebe um objeto do tipo {@link Reparacao} e executa uma instrução SQL {@code INSERT}
     *      para gravar os detalhes iniciais do processo de assistência técnica. São registadas as
     *      observações/descrição do problema, a associação ao equipamento específico (idEquip)
     *      e o estado inicial da reparação. A operação utiliza o padrão {@code try-with-resources}
     *      para garantir que a ligação à base de dados seja fechada após a execução.
     * </p>
     * @param r O objeto {@link Reparacao} contendo os dados necessários para o novo registo
     * na tabela "reparacao"
     * @return {@code true} se a reparação for submetida com sucesso (pelo menos uma linha afetada),
     * {@code false} caso contrário
     * @throws RuntimeException Se ocorrer um erro crítico de SQL durante a comunicação com a
     * base de dados, encapsulando a {@link SQLException} original.
     */
    public boolean SubmeterREparacao(Reparacao r){
        String sql = "Insert into reparacao (observacao, idEquip,Estado) values (?, ?,?)";

        try (Connection conn = DBConnection.getconn();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, r.getObservacao());
            ps.setInt(2, r.getIdEquip());
            ps.setInt(3, r.getEstado());

            int criarReparacao = ps.executeUpdate();
            return (criarReparacao > 0);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Metodo para consultar e listar na consola todas as reparações pendentes de aprovação inicial
     * <p>
     *      O metodo executa uma consulta SQL na tabela "reparacao" para selecionar todos os registos
     *      que se encontram no estado 1 (geralmente correspondente a "Aguardar Aprovação"),
     *      ordenando-os pelo identificador único (idR). Para cada registo encontrado, é instanciado um
     *      objeto {@link Reparacao} contendo o ID do equipamento e as observações. No final, a lista
     *      completa é impressa na consola para visualização por parte dos utilizadores autorizados.
     * </p>
     * @throws RuntimeException Se ocorrer um erro crítico durante a ligação ou a execução da consulta
     * SQL na base de dados ({@code DBConnection.getconn} e {@code ps.executeQuery}), encapsulando
     * a respetiva {@link SQLException}.
     */
    public void verReparacoesPorAprovar() {
        String sql = "Select * from reparacao where estado = 1 order by idR";
        List<Reparacao> listaR = new ArrayList<>();
        try (Connection conn = DBConnection.getconn();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Reparacao r = new Reparacao();
                r.setIdEquip(rs.getInt("idEquip"));
                r.setObservacao(rs.getString("Observacao"));
                listaR.add(r);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Lista de reparacoes por aprovar: " + listaR);
    }

    /**
     * Metodo para listar as reparações atribuídas a um funcionário específico que aguardam aprovação do mesmo
     * <p>
     *      O metodo consulta a tabela "reparacao" em busca de registos onde o estado seja igual a 2 (correspondente a aguardar aprovacao do funcionario)
     *      e que estejam atribuídos ao funcionário com sessão iniciada
     *      iniciada. Para cada registo encontrado, os dados (ID da reparação, ID do equipamento e observações)
     *      são encapsulados num objeto {@link Reparacao} e adicionados a uma lista. A lista resultante é
     *      impressa na consola.
     * </p>
     * @param userlogado O objeto {@link Utilizador} que representa o funcionário atualmente autenticado
     * @return Uma {@link List} de objetos {@link Reparacao} contendo os processos pendentes do funcionário
     * @throws RuntimeException Se ocorrer um erro de SQL durante a execução da consulta,
     * encapsulando a {@link SQLException} original.
     */
    public List<Reparacao> verReparacoesPorAprovarF(Utilizador userlogado) {
        String sql = "Select * from reparacao where estado = 2 and FuncionarioA = ? order by idR";
        List<Reparacao> listaR = new ArrayList<>();
        try (Connection conn = DBConnection.getconn();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userlogado.getId());

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Reparacao r = new Reparacao();
                ps.setInt(1, (userlogado.getId()));
                r.setIdR(rs.getInt("idR"));
                r.setIdEquip(rs.getInt("idEquip"));
                r.setObservacao(rs.getString("Observacao"));
                listaR.add(r);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Lista de reparacoes por aprovar: " + listaR);
        return listaR;
    }

    /**
     * Metodo para listar as reparações em curso atribuídas a um funcionário específico
     * <p>
     *      O metodo consulta a tabela "reparacao" filtrando pelos registos que se encontram no
     *      estado 3 (geralmente correspondente a "Em Curso") e que estão
     *      sob a responsabilidade do funcionário atualmente autentificado. Para cada reparação
     *      encontrada, os dados essenciais (ID da reparação, ID do equipamento e observações)
     *      são extraídos e armazenados numa lista. Esta lista permite ao funcionário gerir as
     *      suas tarefas.
     * </p>
     * @param userlogado O objeto {@link Utilizador} que representa o funcionário autentificado.
     * @return Uma {@link List} de objetos {@link Reparacao} contendo os processos ativos do funcionário
     * @throws RuntimeException Se ocorrer um erro de SQL durante a execução da consulta,
     * encapsulando a respetiva {@link SQLException}.
     */
    public List<Reparacao> verReparacoesPorFinalizarF(Utilizador userlogado) {
        String sql = "Select * from reparacao where estado = 3 and FuncionarioA = ?";
        List<Reparacao> listaR = new ArrayList<>();
        try (Connection conn = DBConnection.getconn();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userlogado.getId());

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Reparacao r = new Reparacao();
                ps.setInt(1, (userlogado.getId()));
                r.setIdR(rs.getInt("idR"));
                r.setIdEquip(rs.getInt("idEquip"));
                r.setObservacao(rs.getString("Observacao"));
                listaR.add(r);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Lista de reparacoes por aprovar: " + listaR);
        return listaR;
    }

    /**
     * Metodo para registar a aceitação de uma reparação por parte de um gestor
     * <p>
     *      O metodo atualiza o registo de uma reparação específica na base de dados, alterando o seu
     *      estado para 2 e atribuindo-lhe o identificador do funcionário responsável (FuncionarioA).
     *      Esta operação efetiva a vinculação entre o funcionario e uma reparacao.
     *      Utiliza um {@link PreparedStatement} para garantir a execução segura do query SQL {@code UPDATE}.
     </p>
     * @param r O objeto {@link Reparacao} que contém o identificador da reparação (idR) a ser atualizada
     * @return {@code true} se a atualização for bem-sucedida (pelo menos uma linha afetada),
     * {@code false} caso contrário
     * @throws RuntimeException Se ocorrer um erro crítico de SQL durante a ligação ou execução do
     * comando na base de dados, encapsulando a {@link SQLException} original.
     */
    public boolean AceitarReparacaoDAO(Reparacao r){
        String sql = "Update reparacao set Estado = 2 , FuncionarioA = ? where idR = ? ";
        try (Connection conn = DBConnection.getconn();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, Reparacao.getFuncionarioA());
            ps.setInt(2,r.getIdR());
            int ReparacaoAceite = ps.executeUpdate();
            return (ReparacaoAceite > 0);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * Atualiza o estado de uma reparação atribuída a um funcionário e regista o seu início.
     * <p>
     *      O processo começa por verificar se o identificador do funcionário fornecido corresponde
     *      ao funcionário responsável pela reparação. Se os identificadores não coincidirem, a ação
     *      é imediatamente bloqueada para garantir que um funcionário só possa aceitar ou rejeitar
     *      as suas próprias reparações. Após esta validação de segurança, o método atualiza o
     *      estado da reparação na base de dados e define automaticamente a data atual como a data
     *      de início da intervenção.
     * </p>
     * @param r O objeto {@link Reparacao} que contém o identificador da reparação e o novo estado a aplicar.
     * @param id O identificador único do funcionário que está a tentar executar a ação.
     * @return {@code true} se o estado da reparação for atualizado com sucesso na base de dados, {@code false} caso contrário.
     * @throws SecurityException Se o funcionário tentar modificar uma reparação que não lhe está atribuída.
     * @throws RuntimeException Se ocorrer algum erro na manipulação da base de dados envia {@link SQLException}.
     */
    public boolean AceitarReparacaoFDAO(Reparacao r , int id) {

        if (id != (Reparacao.getFuncionarioA())) {
            throw new SecurityException("Ação proibida: Só pode aceitar ou rejeitar a sua própria reparação!");
        }
        String sql = "Update reparacao set Estado = ? , DataInicio = current_date  where idR = ? and FuncionarioA = ? ";

        try (Connection conn = DBConnection.getconn();
        PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, r.getEstado());
            ps.setInt(2, r.getIdR());
            ps.setInt(3, Reparacao.getFuncionarioA());

            int ReparacaoAceiteF = ps.executeUpdate();
            return (ReparacaoAceiteF > 0);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Finaliza o processo de uma reparação atribuída a um funcionário e regista a data de conclusão.
     * <p>
     *      O processo começa por verificar se o identificador do funcionário fornecido corresponde
     *      ao funcionario responsável pela reparação. Se os identificadores não coincidirem, a ação
     *      é imediatamente bloqueada, assegurando que um funcionário apenas possa concluir as
     *      suas próprias reparações. Ultrapassada esta validação de segurança, o método atualiza o
     *      estado da reparação na base de dados e define automaticamente a data atual como a
     *      data de fim (término) da intervenção.
     * </p>
     * @param r O objeto {@link Reparacao} que contém o identificador da reparação e o estado final a aplicar.
     * @param id O identificador único do funcionário que está a tentar executar a ação.
     * @return {@code true} se o estado e a data da reparação forem atualizados com sucesso na base de dados, {@code false} caso contrário.
     * @throws SecurityException Se o funcionário tentar finalizar uma reparação que não lhe pertence.
     * @throws RuntimeException Se ocorrer algum erro na manipulação da base de dados envia {@link SQLException}.
     */
    public boolean FinalizarReparacaoFDAO(Reparacao r , int id) {

        if (id != (Reparacao.getFuncionarioA())) {
            throw new SecurityException("Ação proibida: Só pode finalizar a sua própria reparação!");
        }
        String sql = "Update reparacao set Estado = ? , DataFim = current_date  where idR = ? and FuncionarioA = ? ";

        try (Connection conn = DBConnection.getconn();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, r.getEstado());
            ps.setInt(2, r.getIdR());
            ps.setInt(3, Reparacao.getFuncionarioA());

            int ReparacaoAceiteF = ps.executeUpdate();
            return (ReparacaoAceiteF > 0);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Identifica as reparações em curso há mais de 10 dias sem conclusão.
     * <p>
     *      O processo começa por executar uma consulta na base de dados para procurar todas as
     *      reparações cujo tempo decorrido desde a data de início da intervenção seja superior a dez dias
     *      e que ainda não se encontrem finalizadas (estado diferente de 4). Após a obtenção dos dados,
     *      o método constrói uma lista com as informações essenciais destas reparações (identificador,
     *      data de início, equipamento associado e observações) e imprime-a na consola. Esta listagem
     *      funciona como uma notificação.
     * </p>
     * @throws RuntimeException Se ocorrer algum erro na manipulação da base de dados envia {@link SQLException}.
     */
    public static void notificacaoDezDiasSemFinalizacao(){
        String sql = "Select * from reparacao where (CURRENT_DATE - DataInicio) > 10 and estado != 4";
        List<Reparacao> listaR = new ArrayList<>();
        try (Connection conn = DBConnection.getconn();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                Reparacao r = new Reparacao();
                r.setIdR(rs.getInt("idR"));
                r.setDataInicio(rs.getDate("DataInicio"));
                r.setIdEquip(rs.getInt("idEquip"));
                r.setObservacao(rs.getString("Observacao"));
                listaR.add(r);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Lista de reparacoes que passou 10 dias sem ser finalizado: " + listaR);
    }

    /**
     * Pesquisa e recupera os detalhes de um pedido de reparação através do seu identificador.
     * <p>
     *      O processo começa por executar uma consulta à base de dados na tabela "reparacao",
     *      procurando por um registo exato que corresponda ao identificador fornecido. Se a
     *      reparação for encontrada, o método constrói um novo objeto {@link Reparacao} e
     *      preenche-o com as informações essenciais do processo, nomeadamente as datas de
     *      início e fim, o custo associado e as observações descritivas, devolvendo este objeto.
     *      Caso não seja encontrado nenhum registo com o identificador indicado, o método imprime
     *      uma breve mensagem na consola e retorna {@code null}.
     * </p>
     * @param idR O identificador único do pedido de reparação a pesquisar na base de dados.
     * @return Um objeto {@link Reparacao} contendo os dados do pedido se este for encontrado, ou {@code null} caso contrário.
     * @throws RuntimeException Se ocorrer algum erro na manipulação da base de dados envia {@link SQLException}.
     */
    public static Reparacao PesquisarPedidosReparacao(int idR) {
        String sql = "SELECT * FROM reparacao WHERE idR=?";
        try (Connection conn = DBConnection.getconn();
             PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setInt(1, idR);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Reparacao re = new Reparacao();
                re.setIdR(rs.getInt("IdR"));
                re.setDataInicio(rs.getDate("DataInicio"));
                re.setDataFim(rs.getDate("DataFim"));
                re.setCusto(rs.getInt("Custo"));
                re.setObservacao(rs.getString("Observacao"));
                return re;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Pedido: ");
        return null;

    }
}
