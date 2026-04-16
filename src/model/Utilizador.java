package model;

public class Utilizador {

    private String nome;
    private String username;
    private String password;
    private String email;
    private int estado;
    private String novaPassword;
    private String novoEmail;
    private int id;

    /**
     * Metodo para formatar a visualizacao da peca.
     * Solucao adaptada de: Duncan Jones
     * Fonte: https://stackoverflow.com/questions/29140402/how-do-i-print-my-java-object-without-getting-sometype2f92e0f4
     * Acedido em: 16 de Abril de 2026.
     */
    @Override
    public String toString() {
        return "Utilizador [Username: " + username + " | ID: " + id + "]";
    }

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

    public Utilizador(int id) {
        this.id = id;
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

    public int getEstado() {
        return estado;
    }

    public int getId() {
        return id;
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

    public void setEstado(int estado) {
        this.estado = estado;
    }
    public void setId(int id) {
        this.id = id;
    }
}

