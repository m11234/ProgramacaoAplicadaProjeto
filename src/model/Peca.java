package model;

public class Peca {
    private int idPeca;
    private String codigo;
    private String designacao;
    private String fabricante;
    private int quantidade;


    public Peca(String codigo, String designacao, String fabricante, int quantidade) {
        this.codigo = codigo;
        this.designacao = designacao;
        this.fabricante = fabricante;
        this.quantidade = quantidade;
    }

    //getters
    public int getIdPeca() {return idPeca;}
    public String getCodigo() {return codigo;}
    public String getDesignacao() {return designacao;}
    public String getFabricante() {return fabricante;}
    public int getQuantidade() {return quantidade;}

    //setters
    public void setIdPeca(int idPeca) {this.idPeca = idPeca;}
    public void setCodigo(String codigo) {this.codigo = codigo;}
    public void setDesignacao(String designacao) {this.designacao = designacao;}
    public void setFabricante(String fabricante) {this.fabricante = fabricante;}
    public void setQuantidade(int quantidade) {this.quantidade = quantidade;}
}
