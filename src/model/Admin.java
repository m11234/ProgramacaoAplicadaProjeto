package model;

public class Admin {
    private int  idA;
    private int id;

    public Admin(int idA, int id) {
        this.idA = idA;
        this.id = id;
    }

    public Admin() {

    }

    public int getIdA() {return idA;}
    public int getId() {return id;}

    public void setIdA(int idA) {this.idA = idA;}
    public void setId(int id) {this.id = id;}
}
