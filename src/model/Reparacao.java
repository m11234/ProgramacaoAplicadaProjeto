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

    /**
     * @param idR
     * @param observacao
     * @param dataInicio
     * @param dataFim
     * @param custo
     * @param estado
     * @param idEquip
     * @param FuncionarioA
     */
    public Reparacao(int idR,String observacao, Date dataInicio, Date dataFim, float custo, int estado, int idEquip, int FuncionarioA) {
        this.idR = idR;
        this.observacao = observacao;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.custo = custo;
        this.estado = estado;
        this.idEquip = idEquip;
        Reparacao.FuncionarioA = FuncionarioA;
    }

    /**
     * @param observacao
     * @param idEquip
     * @param estado
     */
    public Reparacao(String observacao,int idEquip,int estado) {
        this.observacao = observacao;
        this.idEquip = idEquip;
        this.estado = estado;
    }

    /**
     * @param FuncionarioA
     * @param idR
     */
    public Reparacao(int FuncionarioA, int idR) {
        Reparacao.FuncionarioA = FuncionarioA;
        this.idR = idR;
    }

    /**
     * @param estado
     * @param idReparacao
     * @param idFuncionario
     */
    public Reparacao(int estado, int idReparacao, int idFuncionario) {
        this.estado = estado;
        this.idR = idReparacao;
        FuncionarioA = idFuncionario;
    }

    public Reparacao() {

    }

    /**
     * Metodo para formatar a visualizacao da peca.
     * Solucao adaptada de: Duncan Jones
     * Fonte: <a href="https://stackoverflow.com/questions/29140402/how-do-i-print-my-java-object-without-getting-sometype2f92e0f4">...</a>
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


    /**
     * @return
     */
    //getters
    public int getIdR() {return idR;}

    /**
     * @return
     */
    public int getEstado() {return estado;}
    public Date getDataInicio() {return dataInicio;}

    /**
     * @return
     */
    public Date getDataFim() {return dataFim;}
    public float getCusto() {return custo;}

    /**
     * @return
     */
    public String getObservacao() {return observacao;}

    /**
     * @return
     */
    public int getIdEquip() {return idEquip;}

    /**
     * @return
     */
    public static int getFuncionarioA() {return FuncionarioA;}

    /**
     * @param idR
     */
    //setters
    public void setIdR(int idR) {this.idR = idR;}
    public void setEstado(int estado) {this.estado = estado;}

    /**
     * @param dataInicio
     */
    public void setDataInicio(Date dataInicio) {this.dataInicio = dataInicio;}

    /**
     * @param dataFim
     */
    public void setDataFim(Date dataFim) {this.dataFim = dataFim;}

    /**
     * @param custo
     */
    public void setCusto(float custo) {this.custo = custo;}

    /**
     * @param observacao
     */
    public void setObservacao(String observacao) {this.observacao = observacao;}

    /**
     * @param idEquip
     */
    public void setIdEquip(int idEquip) {this.idEquip = idEquip;}
    public void setFuncionarioA(int FuncionarioA) {
        Reparacao.FuncionarioA = FuncionarioA;}
}
