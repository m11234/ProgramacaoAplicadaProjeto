package model;

import java.util.Date;

public class Funcionario {
    private int nif;
    private int telemovel;
    private String morada;
    private int nivelE;
    private Date dataI;
    private int idUtilizador; // FK para o utilizador associado

    /**
     * @param nif
     * @param telemovel
     * @param morada
     * @param nivelE
     * @param dataI
     * @param idUtilizador
     */
    public Funcionario(int nif, int telemovel, String morada, int nivelE, Date dataI, int idUtilizador) {
        this.nif = nif;
        this.telemovel = telemovel;
        this.morada = morada;
        this.nivelE = nivelE;
        this.dataI = dataI;
        this.idUtilizador = idUtilizador;
    }

    /**
     * @return
     */
    // getters
    public int getNif() { return nif; }

    /**
     * @return
     */
    public int getTelemovel() { return telemovel; }

    /**
     * @return
     */
    public String getMorada() { return morada; }

    /**
     * @return
     */
    public int getNivelE() { return nivelE; }

    /**
     * @return
     */
    public Date getDataI() { return dataI; }

    /**
     * @return
     */
    public int getIdUtilizador() { return idUtilizador; }

    // setters
}