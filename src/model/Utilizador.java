package model;

public class Utilizador {

    private String nome;
    private String username;
    private String password;
    private String email;
    private boolean estado;
    private String novaPassword;
    private String novoEmail;

    // construtor
    public Utilizador(String nome, String username, String password, String email) {
        this.nome = nome;
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public Utilizador(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Utilizador() {

    }




    // getters
    public String getNome() {
        return nome;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public boolean getEstado() {
        return estado;
    }


    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }
}

