package model;

import java.util.Date;

public class Equipamento {
    private String marca;
    private String modelo;
    private String SKU;
    private String lote;
    private Date dataSubmissao;
    private int id;
    private Date dataReparacao;
    private int idEquipamento;

    public Equipamento( String marca, String modelo, String SKU, String lote, Date dataSubmissao,int id){
        this.marca = marca;
        this.modelo = modelo;
        this.SKU = SKU;
        this.lote = lote;
        this.dataSubmissao = dataSubmissao;
        this.dataReparacao = dataSubmissao;
        this.id = id;
    }

    public Equipamento() {

    }

    //getters
    public int getIdEquipamento() {return idEquipamento;}
    public String getMarca() {return marca;}
    public String getModelo() {return modelo;}
    public String getSKU() {return SKU;}
    public String getLote() {return lote;}
    public Date getDataSubmissao() {return dataSubmissao;}
    public Date getDataReparacao() {return dataReparacao;}
    public int getid() {return id;}


    //setters
    public void setIdEquipamento(int idEquipamento) {this.idEquipamento = idEquipamento;}
    public void setMarca(String marca) {this.marca = marca;}
    public void setModelo(String modelo) {this.modelo = modelo;}
    public void setSKU(String SKU) {this.SKU = SKU;}
    public void setLote(String lote) {this.lote = lote;}
    public void setDataSubmissao(Date dataSubmissao) {this.dataSubmissao = dataSubmissao;}
    public void  setDataReparacao(Date dataReparacao) {this.dataReparacao = dataReparacao;}
}
