package model;

import java.util.Date;

public class Funcionario {
    private int nif;
    private int telemovel;
    private String morada;
    private int nivelE;
    private Date dataI;
    private int idUtilizador; // FK para o utilizador associado

    public Funcionario(int nif, int telemovel, String morada, int nivelE, Date dataI, int idUtilizador) {
        this.nif = nif;
        this.telemovel = telemovel;
        this.morada = morada;
        this.nivelE = nivelE;
        this.dataI = dataI;
        this.idUtilizador = idUtilizador;
    }

    // getters
    public int getNif() { return nif; }
    public int getTelemovel() { return telemovel; }
    public String getMorada() { return morada; }
    public int getNivelE() { return nivelE; }
    public Date getDataI() { return dataI; }
    public int getIdUtilizador() { return idUtilizador; }

    // setters
    public void setNif(int nif) { this.nif = nif; }
    public void setTelemovel(int telemovel) { this.telemovel = telemovel; }
    public void setMorada(String morada) { this.morada = morada; }
    public void setNivelE(int nivelE) { this.nivelE = nivelE; }
    public void setDataI(Date dataI) { this.dataI = dataI; }
    public void setIdUtilizador(int idUtilizador) { this.idUtilizador = idUtilizador; }
}