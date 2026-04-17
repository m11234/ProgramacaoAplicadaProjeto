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

    /**
     * @param nome
     * @param username
     * @param password
     * @param email
     */
    public Utilizador(String nome, String username, String password, String email) {
        this.nome = nome;
        this.username = username;
        this.password = password;
        this.email = email;
    }

    /**
     * @param username
     * @param password
     */
    public Utilizador(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * @param id
     */
    public Utilizador(int id) {
        this.id = id;
    }

    public Utilizador() {

    }


    /**
     * @return
     */
    // getters
    public String getNome() {
        return nome;
    }

    /**
     * @return
     */
    public String getUsername() {
        return username;
    }

    /**
     * @return
     */
    public String getPassword() {
        return password;
    }

    /**
     * @return
     */
    public String getEmail() {
        return email;
    }

    /**
     * @return
     */
    public int getEstado() {
        return estado;
    }

    /**
     * @return
     */
    public int getId() {
        return id;
    }


    /**
     * @param nome
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @param estado
     */
    public void setEstado(int estado) {
        this.estado = estado;
    }

    /**
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }
}

