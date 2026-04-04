package model;

import java.util.Date;

public class Teste {
    private String designacao;
    private String descricao;
    private Date dataTeste;

    public Teste(String designacao, String descricao) {
        this.designacao = designacao;
        this.descricao = descricao;
        this.dataTeste  = new Date();
    }

    //getters
    public String getDesignacao() {return designacao;}
    public String getDescricao() {return descricao;}
    public Date getDataTeste() {return dataTeste;}


    //setters
    public void setDescricao(String descricao) {this.descricao = descricao;}
    public void setDesignacao(String designacao) {this.designacao = designacao;}
    public void setDataTeste(String descricao) {this.descricao = descricao;}
}
