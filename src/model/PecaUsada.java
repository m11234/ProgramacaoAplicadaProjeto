package model;

public class PecaUsada {
    private int idPeca;
    private int idReparacao;

    public PecaUsada(int idPeca, int idReparacao) {
        this.idPeca = idPeca;
        this.idReparacao = idReparacao;
    }

    public int getIdPeca() {return idPeca;}
    public int getIdReparacao() {return idReparacao;}

    public void setIdPeca(int idPeca) {
        this.idPeca = idPeca;
    }
    public void setIdReparacao(int idReparacao) {
        this.idReparacao = idReparacao;
    }

}
