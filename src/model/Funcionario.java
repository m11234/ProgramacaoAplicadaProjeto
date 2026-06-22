package model;

import java.util.Date;

public class Funcionario {
    private String nif;
    private String telemovel;
    private String morada;
    private String nivelE;
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
    public Funcionario(String nif, String telemovel, String morada, String nivelE, Date dataI, int idUtilizador) {
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
    public String getNif() { return nif; }

    /**
     * @return
     */
    public String getTelemovel() { return telemovel; }

    /**
     * @return
     */
    public String getMorada() { return morada; }

    /**
     * @return
     */
    public String getNivelE() { return nivelE; }

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