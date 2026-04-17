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

    /**
     * @param marca
     * @param modelo
     * @param SKU
     * @param lote
     * @param dataSubmissao
     * @param id
     */
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

    /**
     * @return
     */
    //getters
    public int getIdEquipamento() {return idEquipamento;}

    /**
     * @return
     */
    public String getMarca() {return marca;}

    /**
     * @return
     */
    public String getModelo() {return modelo;}

    /**
     * @return
     */
    public String getSKU() {return SKU;}

    /**
     * @return
     */
    public String getLote() {return lote;}

    /**
     * @return
     */
    public Date getDataSubmissao() {return dataSubmissao;}

    /**
     * @return
     */
    public Date getDataReparacao() {return dataReparacao;}

    /**
     * @return
     */
    public int getid() {return id;}


    /**
     * @param idEquipamento
     */
    //setters
    public void setIdEquipamento(int idEquipamento) {this.idEquipamento = idEquipamento;}

    /**
     * @param marca
     */
    public void setMarca(String marca) {this.marca = marca;}

    /**
     * @param modelo
     */
    public void setModelo(String modelo) {this.modelo = modelo;}

    /**
     * @param SKU
     */
    public void setSKU(String SKU) {this.SKU = SKU;}

    /**
     * @param lote
     */
    public void setLote(String lote) {this.lote = lote;}

    /**
     * @param dataSubmissao
     */
    public void setDataSubmissao(Date dataSubmissao) {this.dataSubmissao = dataSubmissao;}

    /**
     * @param dataReparacao
     */
    public void  setDataReparacao(Date dataReparacao) {this.dataReparacao = dataReparacao;}
}
