package model.dao;

import model.Equipamento;
import model.db.DBConnection;

import java.sql.*;

public class EquipamentoDAO {

    /**
     * Metodo para persistir o registo de um novo equipamento na base de dados
     * <p>
     *      O metodo recebe um objeto do tipo {@link Equipamento} e executa uma instrução SQL {@code INSERT}
     *      para gravar as especificações técnicas do dispositivo (marca, modelo, SKU e lote), a data
     *      em que foi submetido e o identificador do utilizador que realizou o registo.
     *      A data de submissão é convertida do formato {@code java.util.Date} para {@code java.sql.Date}
     *      para garantir a compatibilidade com a base de dados.
     * </p>
     * @param e O objeto {@link Equipamento} contendo os dados a serem inseridos na tabela "equipamento"
     * @return {@code true} se o equipamento for registado com sucesso (pelo menos uma linha inserida),
     * {@code false} caso contrário
     * @throws RuntimeException Se ocorrer um erro de SQL durante a execução da instrução,
     * encapsulando a {@link SQLException} original.
     */
    public boolean RegistarEquipamento (Equipamento e){
        String sql = "Insert into equipamento (Marca, Modelo, SKU, Lote, DataSubmissao, id) values (?,?,?,?,?,?)";

        try (Connection conn = DBConnection.getconn();
        PreparedStatement ps = conn.prepareStatement(sql)){

            ps.setString(1, e.getMarca());
            ps.setString(2, e.getModelo());
            ps.setString(3, e.getSKU());
            ps.setString(4, e.getLote());
            ps.setDate(5, new java.sql.Date(e.getDataSubmissao().getTime()));
            ps.setInt(6, e.getid());

            int criarEquipamento = ps.executeUpdate();
            return (criarEquipamento > 0);

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * Metodo para verificar se um equipamento específico pertence a um determinado utilizador
     * <p>
     *      O metodo realiza uma consulta na base de dados procurando por um registo na tabela "equipamento"
     *      que coincida simultaneamente com o ID do equipamento e o ID do utilizador fornecidos.
     *      Esta validação é usada para garantir a segurança dos dados, impedindo
     *      que um utilizador aceda ou manipule equipamentos que não são seus. Se a correspondência
     *      for encontrada, confirma a propriedade e retorna verdadeiro.
     * </p>
     * @param idEquip O identificador único do equipamento a ser verificado
     * @param id O identificador único do utilizador (proprietário)
     * @return {@code true} se o equipamento existir e pertencer ao utilizador indicado,
     * {@code false} caso contrário
     * @throws RuntimeException Se ocorrer um erro de SQL durante a execução da consulta,
     * encapsulando a {@link SQLException} original.
     */
    public boolean verSeEquipPertence(int idEquip,int id) {
        String sql = "Select 1 from equipamento where idEquip = ? and id = ?";
        boolean existeAndPertence = false;
        try (Connection conn = DBConnection.getconn();
             PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setInt(1, idEquip);
            ps.setInt(2, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                System.out.println("Equipamento encontrado...");
                existeAndPertence = true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return existeAndPertence;
    }

}
