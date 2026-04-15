package model;

import java.util.Date;

public class Teste {
    private String designacao;
    private String descricao;
    private Date dataTeste;
    private Float preco;

    public Teste(String designacao, String descricao, Date dataTeste, float preco) {
        this.designacao = designacao;
        this.descricao = descricao;
        this.dataTeste  = new Date();
        this.preco = preco;
    }

    //getters
    public String getDesignacao() {return designacao;}
    public String getDescricao() {return descricao;}
    public Date getDataTeste() {return dataTeste;}
    public Float getPreco() {return preco;}


    //setters
    public void setDescricao(String descricao) {this.descricao = descricao;}
    public void setDesignacao(String designacao) {this.designacao = designacao;}
    public void setDataTeste(String descricao) {this.descricao = descricao;}
    public void setPreco(float preco) {this.preco = preco;}
}
