package model;

import java.util.Date;

public class Noticacao {
    private String mensagem;
    private Date dataEnvio;
    private boolean lido;
    private int idN;

    /**
     * @param mensagem
     * @param dataEnvio
     * @param lido
     * @param idN
     */
    public Noticacao(String mensagem, Date dataEnvio, boolean lido, int idN) {
        this.mensagem = mensagem;
        this.dataEnvio = dataEnvio;
        this.lido = lido;
        this.idN = idN;
    }

    //getters
    public String getMensagem() {return mensagem;}
    public Date getDataEnvio() {return dataEnvio;}
    public boolean isLido() {return lido;}
    public int getIdN() {return idN;}

    //setters
    public void setMensagem(String mensagem) {this.mensagem = mensagem;}
    public void setDataEnvio(Date dataEnvio) {this.dataEnvio = dataEnvio;}
    public void setLido(boolean lido) {this.lido = lido;}
    public void setIdN(int idN) {this.idN = idN;}
}
