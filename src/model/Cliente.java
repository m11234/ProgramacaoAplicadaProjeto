package model;

public class Cliente {
    private int nif;
    private int telemovel;
    private String  morada;
    private String sector;
    private String escalao;

    public Cliente(int nif, int telemovel, String morada, String sector, String escalao) {
        this.nif = nif;
        this.telemovel = telemovel;
        this.morada = morada;
        this.sector = sector;
        this.escalao = escalao;
    }

    //getters

    public int getNif() {return nif;}
    public int getTelemovel() {return telemovel;}
    public String getMorada() {return morada;}
    public String getSector() {return sector;}
    public String getEscalao() {return escalao;}

    //setters

    public void setNif(int nif) { this.nif = nif;}
    public void setTelemovel(int telemovel) {this.telemovel = telemovel;}
    public  void setMorada (String morada) {this.morada = morada;}
    public void setSector (String sector) {this.sector = sector;}
    public void setEscalao (String escalao) {this.escalao = escalao;}


}
