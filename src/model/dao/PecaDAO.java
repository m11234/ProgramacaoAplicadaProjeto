package model.dao;


import model.Peca;
import model.PecaUsada;
import model.db.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PecaDAO {

    /**
     * Metodo estático para inserir o registo de uma nova peça no inventário da base de dados
     * <p>
     *      O metodo recebe um objeto do tipo {@link Peca} e executa uma instrução SQL {@code INSERT}
     *      para gravar os detalhes do componente (designação, fabricante, quantidade disponível e preço unitário)
     *      na tabela "peca". A operação é realizada de forma segura utilizando um {@link PreparedStatement}
     *      para prevenir injeções de SQL. Se a inserção for bem-sucedida, o metodo retorna verdadeiro,
     *      confirmando a atualização da tabelsa.
     * </p>
     * @param p O objeto {@link Peca} contendo as informações técnicas e comerciais a serem registadas
     * @return {@code true} se a peça for inserida com sucesso (pelo menos uma linha afetada),
     * {@code false} caso contrário
     * @throws RuntimeException Se ocorrer um erro crítico durante a comunicação com a base de dados,
     * encapsulando a {@link SQLException} original.
     */
    public static boolean InserirPeca(Peca p){
        String sql = "Insert into peca (designacao,fabricante,quantidade,preco) values (?,?,?,?)";

        try (Connection conn = DBConnection.getconn();
        PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1,p.getDesignacao());
            ps.setString(2, p.getFabricante());
            ps.setInt(3,p.getQuantidade());
            ps.setFloat(4,p.getPreco());

            int pecaInserida =  ps.executeUpdate();
            return pecaInserida > 0;


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Metodo estático para registar a utilização de uma peça numa reparação específica
     * <p>
     *      O metodo executa uma inserção na tabela "peca_usada" baseada numa subconsulta de validação.
     *      A peça só é registada se a reparação indicada (idR) estiver atribuída ao funcionário
     *      logado (idFuncionario) e se a reparação estiver no estado correto (Estado = 3 - em curso).
     *      Esta abordagem garante a integridade dos dados e impede que funcionários manipulem
     *      reparações que não lhes pertencem ou que não estão ativas. Caso a condição falhe,
     *      é lançada uma exceção de segurança.
     * </p>
     * @param up O objeto {@link PecaUsada} contendo os identificadores da peça e da reparação
     * @param idFuncionario O identificador único do funcionário que está a realizar a operação
     * @return {@code true} se o registo da peça usada for inserido com sucesso
     * @throws SecurityException Se a reparação não for encontrada, não pertencer ao funcionário
     * ou não estiver no estado adequado para edição
     * @throws RuntimeException Se ocorrer um erro crítico de SQL durante a comunicação com
     * a base de dados ({@code DBConnection.getconn}).
     */
    public static boolean PecasUsadas(PecaUsada up, int idFuncionario) {

        // assim so insere se a reparacao pertencer aquele funcionario e a peca existir
        String sql = "INSERT INTO peca_usada (idPeca, idReparacao)" +
                "SELECT ?, idR FROM reparacao WHERE idR = ? AND FuncionarioA = ? and Estado = 3";


        try (Connection conn = DBConnection.getconn();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1,up.getIdPeca());
            ps.setInt(2,up.getIdReparacao());
            ps.setInt(3,idFuncionario);

            int pecaUsada = ps.executeUpdate();

            if (pecaUsada == 0) {
                throw new SecurityException("A peca/reparacao não existe ou não pertence a este funcionario!");
            }
            return true;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Metodo estático para listar as peças com stock reduzido (inferior a 10 unidades)
     * <p>
     *      O metodo executa uma consulta na tabela "peca" para identificar todos os componentes cuja
     *      quantidade em inventário seja inferior a 10. Os registos encontrados são mapeados para
     *      objetos {@link Peca}, contendo a designação, fabricante e a quantidade atual. A lista
     *      resultante é impressa na consola para fins de alerta de stock baixo.
     * </p>
     * @return Uma {@link List} de objetos {@link Peca} que se encontram em situação de stock baixo
     * @throws RuntimeException Se ocorrer um erro durante a ligação ou a execução da consulta SQL
     * na base de dados, encapsulando a respetiva {@link SQLException}.
     */
    public static List<Peca>  pecaInferior () {
        String sql = "SELECT * FROM peca WHERE quantidade < 10";
        List<Peca> listaPecas = new ArrayList<>();
        try (Connection conn = DBConnection.getconn();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Peca pu = new Peca();
                pu.setDesignacao(rs.getString("designacao"));
                pu.setFabricante(rs.getString("fabricante"));
                pu.setQuantidade(rs.getInt("quantidade"));
                listaPecas.add(pu);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
            System.out.println("Peças com stock inferior a 10 unidades: " + listaPecas.toString());
            return listaPecas;
        }
    }
