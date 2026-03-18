package modelo;

/**
 *
 * @author Jonathas Ramos
 */
public class Funcionario {
    private String id;
    private String nome;
    private String cargo;

    public Funcionario(String id, String nome, String cargo) {
        this.id = id;
        this.nome = nome;
        this.cargo = cargo;
    }

    // Getters e Setters
    public String getId() { return id; }
    public String getNome() { return nome; }
    public String getCargo() { return cargo; }
}