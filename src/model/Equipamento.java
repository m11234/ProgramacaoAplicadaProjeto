package model;

import java.util.Date;

public class Equipamento {
    private int idEquipamento;
    private String marca;
    private String modelo;
    private String SKU;
    private String lote;
    private Date dataSubmissao;
    private Date dataReparacao;
    private int idC;

    public Equipamento(int idEquipamento, String marca, String modelo, String SKU, String lote, Date dataSubmissao, Date getDataReparacao){
        this.idEquipamento = idEquipamento;
        this.marca = marca;
        this.modelo = modelo;
        this.SKU = SKU;
        this.lote = lote;
        this.dataSubmissao = dataSubmissao;
        this.dataReparacao = dataSubmissao;
        this.idC = idC;
    }

    //getters
    public int getIdEquipamento() {return idEquipamento;}
    public String getMarca() {return marca;}
    public String getModelo() {return modelo;}
    public String getSKU() {return SKU;}
    public String getLote() {return lote;}
    public Date getDataSubmissao() {return dataSubmissao;}
    public Date getDataReparacao() {return dataReparacao;}
    public int getIdC() {return idC;}


    //setters
    public void setIdEquipamento(int idEquipamento) {this.idEquipamento = idEquipamento;}
    public void setMarca(String marca) {this.marca = marca;}
    public void setModelo(String modelo) {this.modelo = modelo;}
    public void setSKU(String SKU) {this.SKU = SKU;}
    public void setLote(String lote) {this.lote = lote;}
    public void setDataSubmissao(Date dataSubmissao) {this.dataSubmissao = dataSubmissao;}
    public void  setDataReparacao(Date dataReparacao) {this.dataReparacao = dataReparacao;}
    public void setIdC(int idC) {this.idC = idC;}
}
