package model;

import java.util.Date;

public class Reparacao {

    private int idR;
    private int estado;
    private Date dataInicio;
    private Date dataFim;
    private float custo;
    private String observacao;
    private int idEquip;
    private static int FuncionarioA;

    public Reparacao(int idR,String observacao, Date dataInicio, Date dataFim, float custo, int estado, int idEquip, int FuncionarioA) {
        this.idR = idR;
        this.observacao = observacao;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.custo = custo;
        this.estado = estado;
        this.idEquip = idEquip;
        this.FuncionarioA = FuncionarioA;
    }

    public Reparacao(String observacao,int idEquip,int estado) {
        this.observacao = observacao;
        this.idEquip = idEquip;
        this.estado = estado;
    }

    public Reparacao(int FuncionarioA, int idR) {
        this.FuncionarioA = FuncionarioA;
        this.idR = idR;
    }

    public Reparacao(int estado, int idReparacao, int idFuncionario) {
        this.estado = estado;
        this.idR = idReparacao;
        this.FuncionarioA = idFuncionario;
    }

    public Reparacao() {

    }

    /**
     * Metodo para formatar a visualizacao da peca.
     * Solucao adaptada de: Duncan Jones
     * Fonte: https://stackoverflow.com/questions/29140402/how-do-i-print-my-java-object-without-getting-sometype2f92e0f4
     * Acedido em: 16 de Abril de 2026.
     */
    @Override
    public String toString() {
        return "Reparacao{" +
                "estado=" + estado +
                ", dataInicio=" + dataInicio +
                ", dataFim=" + dataFim +
                ", custo=" + custo +
                ", observacao='" + observacao + '\'' +
                ", idEquip=" + idEquip +
                '}';
    }


    //getters
    public int getIdR() {return idR;}
    public int getEstado() {return estado;}
    public Date getDataInicio() {return dataInicio;}
    public Date getDataFim() {return dataFim;}
    public float getCusto() {return custo;}
    public String getObservacao() {return observacao;}
    public int getIdEquip() {return idEquip;}
    public static int getFuncionarioA() {return FuncionarioA;}

    //setters
    public void setIdR(int idR) {this.idR = idR;}
    public void setEstado(int estado) {this.estado = estado;}
    public void setDataInicio(Date dataInicio) {this.dataInicio = dataInicio;}
    public void setDataFim(Date dataFim) {this.dataFim = dataFim;}
    public void setCusto(float custo) {this.custo = custo;}
    public void setObservacao(String observacao) {this.observacao = observacao;}
    public void setIdEquip(int idEquip) {this.idEquip = idEquip;}
    public void setFuncionarioA(int FuncionarioA) {this.FuncionarioA = FuncionarioA;}
}
