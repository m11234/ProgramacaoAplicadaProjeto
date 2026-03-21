package model;

public class Utilizador {

    private String nome;
    private String username;
    private String password;
    private String email;

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

    // getters
    public String getNome() { return nome; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getEmail() { return email; }
}

