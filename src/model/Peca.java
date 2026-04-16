package model;

public class Peca {
    private int idPeca;
    private String designacao;
    private String fabricante;
    private int quantidade;
    private float preco;


    public Peca( String designacao, String fabricante, int quantidade,float preco) {
        this.designacao = designacao;
        this.fabricante = fabricante;
        this.quantidade = quantidade;
        this.preco = preco;
    }

    //getters
    public int getIdPeca() {return idPeca;}
    public String getDesignacao() {return designacao;}
    public String getFabricante() {return fabricante;}
    public int getQuantidade() {return quantidade;}
    public float getPreco() {return preco;}

    //setters
    public void setIdPeca(int idPeca) {this.idPeca = idPeca;}
    public void setDesignacao(String designacao) {this.designacao = designacao;}
    public void setFabricante(String fabricante) {this.fabricante = fabricante;}
    public void setQuantidade(int quantidade) {this.quantidade = quantidade;}
    public void setPreco(float preco) {this.preco = preco;}
}
