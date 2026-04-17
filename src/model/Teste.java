package model;

import java.util.Date;

public class Teste {
    private int idReparacao;
    private String designacao;
    private String descricao;
    private Date dataTeste;
    private Float preco;

    /**
     * @param idReparacao
     * @param designacao
     * @param descricao
     * @param preco
     */
    public Teste(int idReparacao,String designacao, String descricao,float preco) {
        this.idReparacao = idReparacao;
        this.designacao = designacao;
        this.descricao = descricao;
        this.preco = preco;
    }

    /**
     * @return
     */
    //getters
    public int getIdReparacao() {return idReparacao;}

    /**
     * @return
     */
    public String getDesignacao() {return designacao;}

    /**
     * @return
     */
    public String getDescricao() {return descricao;}
    public Date getDataTeste() {return dataTeste;}

    /**
     * @return
     */
    public Float getPreco() {return preco;}


    //setters
    public void setIdReparacao(int idReparacao) {this.idReparacao = idReparacao;}
    public void setDescricao(String descricao) {this.descricao = descricao;}
    public void setDesignacao(String designacao) {this.designacao = designacao;}
    public void setDataTeste(String descricao) {this.descricao = descricao;}
    public void setPreco(float preco) {this.preco = preco;}
}
