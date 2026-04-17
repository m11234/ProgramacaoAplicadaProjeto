package model;

public class Cliente {
    private int nif;
    private int telemovel;
    private String  morada;
    private String sector;
    private String escalao;
    private int idUtilizador; //FK para o utilizador associado
    private int idC;

    /**
     * @param nif
     * @param telemovel
     * @param morada
     * @param sector
     * @param escalao
     * @param idUtilizador
     */
    public Cliente(int nif, int telemovel, String morada, String sector, String escalao,int idUtilizador) {
        this.nif = nif;
        this.telemovel = telemovel;
        this.morada = morada;
        this.sector = sector;
        this.escalao = escalao;
        this.idUtilizador = idUtilizador;
    }

    //getters

    /**
     * @return
     */
    public int getNif() {return nif;}

    /**
     * @return
     */
    public int getTelemovel() {return telemovel;}
    public String getMorada() {return morada;}

    /**
     * @return
     */
    public String getSector() {return sector;}

    /**
     * @return
     */
    public String getEscalao() {return escalao;}

    /**
     * @return
     */
    public int getIdUtilizador() {return idUtilizador;}
    public int getIdC() {return idC;}

    //setters

    public void setNif(int nif) { this.nif = nif;}
    public void setTelemovel(int telemovel) {this.telemovel = telemovel;}
    public  void setMorada (String morada) {this.morada = morada;}
    public void setSector (String sector) {this.sector = sector;}
    public void setEscalao (String escalao) {this.escalao = escalao;}
    public void setIdUtilizador(int idUtilizador) {this.idUtilizador = idUtilizador;}
    public void setIdC(int idC) {this.idC = idC;}
}
