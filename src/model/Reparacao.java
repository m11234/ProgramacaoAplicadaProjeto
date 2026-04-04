package model;

import java.util.Date;

public class Reparacao {
    private int estado;
    private Date dataInicio;
    private Date dataFim;
    private float custo;
    private String observacao;

    public Reparacao(String observacao, Date dataInicio, Date dataFim, float custo, int estado) {
        this.observacao = observacao;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.custo = custo;
        this.estado = estado;
    }

    //getters
    public int getEstado() {return estado;}
    public Date getDataInicio() {return dataInicio;}
    public Date getDataFim() {return dataFim;}
    public float getCusto() {return custo;}
    public String getObservacao() {return observacao;}

    //setters
    public void setEstado(int estado) {this.estado = estado;}
    public void setDataInicio(Date dataInicio) {this.dataInicio = dataInicio;}
    public void setDataFim(Date dataFim) {this.dataFim = dataFim;}
    public void setCusto(float custo) {this.custo = custo;}
    public void setObservacao(String observacao) {this.observacao = observacao;}
}
