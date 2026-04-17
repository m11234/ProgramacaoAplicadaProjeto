package model.dao;

import model.Admin;
import model.Equipamento;
import model.Reparacao;
import model.db.DBConnection;

import model.Utilizador;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AdminDao {
    /**
     * Metodo para verificar a existência de gestores registados na base de dados
     * <p>
     * O metodo realiza uma contagem total na tabela de administradores. Se o resultado for
     * superior a zero, confirma a existência de gestores no sistema. Caso contrário, exibe
     * uma mensagem indicando a ausência de gestores. Este método é usado para validações
     * de segurança, como garantir que existe pelo menos um administrador
     * antes de restringir certas operações.
     * </p>
     * @return {@code true} se existir pelo menos um gestor na base de dados, {@code false} caso contrário
     * @throws SQLException Se ocorrer um erro durante a ligação ou a execução da consulta SQL
     * na base de dados ({@code DBConnection.getconn} e {@code ps.executeQuery}).
     */
    public boolean verificarGestores() throws SQLException {
        String sql = "Select count(*) from administrador";
        boolean ExistemGestores = false;

        try (Connection conn = DBConnection.getconn();
             PreparedStatement ps = conn.prepareStatement(sql)){
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int count = rs.getInt(1);
                if (count > 0) {
                    ExistemGestores = true;
                }
                else  {
                    System.out.println("Nao existem gestores");
                }
            }
        }
        return ExistemGestores;
    }

    /**
     * Metodo para persistir o registo de um administrador/gestor na base de dados
     * <p>
     *      O método recebe um objeto do tipo {@link Admin} e executa uma instrução SQL {@code INSERT}
     *      para inserir o identificador (ID) do utilizador na tabela de administradores. Este processo
     *      efetiva a atribuição de privilégios de gestão a um perfil de utilizador já existente.
     *      Utiliza o padrão {@code try-with-resources} para garantir que a ligação à base de dados
     *      e o {@link PreparedStatement} são encerrados corretamente após a execução.
     * </p>
     * @param a O objeto {@link Admin} contendo os dados do administrador a ser registado,
     * nomeadamente o seu identificador único (ID)
     * @throws SQLException Se ocorrer um erro durante a ligação ou a execução da instrução SQL
     * na base de dados ({@code DBConnection.getconn} e {@code ps.executeUpdate}).
     */
    public void criarGestor(Admin a) throws SQLException {
        String sql = "INSERT INTO administrador (id) VALUES (?)";

        try (Connection conn = DBConnection.getconn();
             PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setInt(1,a.getId());
            ps.executeUpdate();

        }
    }


    /**
     * Metodo para verificar se um determinado utilizador possui privilégios de gestor
     * <p>
     *      O método realiza uma consulta na tabela "administrador" utilizando o identificador (ID)
     *      fornecido. Se for encontrado um registo correspondente, confirma que o utilizador é um gestor,
     *      exibe uma mensagem de validação na consola e retorna o valor lógico verdadeiro. Caso contrário,
     *      retorna falso, indicando que o utilizador não possui permissões administrativas.
     * </p>
     * @param Id O identificador único do utilizador a ser verificado na base de dados
     * @return {@code true} se o utilizador for um gestor, {@code false} caso contrário
     * @throws SQLException Se ocorrer um erro durante a ligação ou a execução da consulta SQL
     * na base de dados ({@code DBConnection.getconn} e {@code ps.executeQuery}).
     */
    public boolean VerSeGestor(int Id) throws SQLException {
        String sql = "SELECT * FROM administrador WHERE id=?";
        boolean GestorSer = false;
        try (Connection conn = DBConnection.getconn();
             PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setInt(1, Id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                System.out.println("O utilizador é um gestor proseguir...");
                GestorSer = true;
            }
        }
        return GestorSer;
    }

    /**
     * Metodo para listar todos os utilizadores registados no sistema por ordem alfabetica
     * <p>
     *      O metodo executa uma consulta SQL para selecionar todos os registos da tabela "utilizador",
     *      ordenando-os ascendentemente pelo nome. Os resultados são mapeados para uma lista de objetos
     *      {@link Utilizador} (armazenando apenas o nome e o username) e, por fim, a lista completa
     *      é impressa na consola.
     * </p>
     * @throws RuntimeException Se ocorrer um erro durante a ligação ou a execução da consulta SQL
     * na base de dados ({@code DBConnection.getconn} e {@code ps.executeQuery}), encapsulando
     * a respetiva {@link SQLException}.
     */
    public static void verUtilizadores() {
        String sql = "Select * from utilizador order by nome asc";
        List<Utilizador> listaA = new ArrayList<>();
        try (Connection conn = DBConnection.getconn();
            PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                Utilizador u = new Utilizador();
                u.setNome(rs.getString("nome"));
                u.setUsername(rs.getString("username"));
                listaA.add(u);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Lista de utilizadores: " + listaA);
    }

    /**
     * Metodo para procurar e obter os detalhes de uma reparação específica através do seu ID
     * <p>
     *      O metodo executa uma consulta SQL na base de dados para encontrar o registo correspondente
     *      ao identificador fornecido. Caso a reparação seja encontrada, os seus dados (ID, data de início,
     *      data de fim e observações) são mapeados para um novo objeto {@link Reparacao} e devolvidos.
     *      Se o registo não existir, o metodo imprime uma mensagem de aviso {@code null}.
     * </p>
     * @param idR O identificador único da reparação a ser consultada na base de dados
     * @return Um objeto {@link Reparacao} preenchido com os dados da base de dados se encontrada,
     * ou {@code null} caso contrário
     * @throws RuntimeException Se ocorrer um erro durante a ligação ou a execução da consulta SQL
     * na base de dados ({@code DBConnection.getconn} e {@code ps.executeQuery}), encapsulando
     * a respetiva {@link SQLException}.
     */
    public Reparacao verReparacoes(int idR) {
        String sql = "Select * from Reparacao where idR = ?";
        try (Connection conn = DBConnection.getconn();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idR);
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                Reparacao rep = new Reparacao();
                rep.setIdR(rs.getInt("idR"));
                rep.setDataInicio(rs.getDate("DataInicio"));
                rep.setDataFim(rs.getDate("DataFim"));
                rep.setObservacao(rs.getString("Observacao"));
                return rep;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Lista de Reparacoes: ");
        return null;
    }

    /**
     * Metodo para pesquisar e obter os detalhes de um equipamento através do seu identificador único
     * <p>
     *      O metodo executa uma consulta SQL para localizar o registo correspondente ao ID fornecido na tabela "equipamento".
     *      Caso o equipamento seja encontrado, os seus atributos (ID, marca, modelo, SKU, lote, data de submissão e
     *      data de reparação) são mapeados para um objeto {@link Equipamento} e devolvidos.
     *      Se o registo não existir na base de dados, o metodo imprime uma mensagem informativa {@code null}.
     * </p>
     * @param idEquip O identificador único do equipamento a ser consultado
     * @return Um objeto {@link Equipamento} contendo os dados recuperados, ou {@code null} se não for encontrado
     * @throws RuntimeException Se ocorrer um erro na comunicação com a base de dados durante a execução do
     * comando SQL, encapsulando a respetiva {@link SQLException}.
     */
    public Equipamento perquisarEquipamento(int idEquip) {
        String sql = "Select * from equipamento where idEquip = ? order by id";
        try (Connection conn = DBConnection.getconn();
            PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idEquip);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Equipamento eq = new Equipamento();
                eq.setIdEquipamento(rs.getInt("idEquip"));
                eq.setMarca(rs.getString("Marca"));
                eq.setModelo(rs.getString("Modelo"));
                eq.setSKU(rs.getString("SKU"));
                eq.setLote(rs.getString("Lote"));
                eq.setDataReparacao(rs.getDate("dataSubmissao"));
                eq.setDataSubmissao(rs.getDate("dataReparacao"));
                return eq;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Equipamento: ");
        return null;
    }
}
