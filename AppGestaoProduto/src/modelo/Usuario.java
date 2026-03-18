package modelo;

/**
 *
 * @author Jonathas Ramos
 */
public abstract class Usuario {
    
    // Atributos base do Usuário
    private String nome;
    private String matricula;
    private String endereco;

    // Construtor para inicializar os dados
    public Usuario(String nome, String matricula, String endereco) {
        this.nome = nome;
        this.matricula = matricula;
        this.endereco = endereco;
    }

    // A única regra de negócio que fica aqui é o limite, 
    // pois Aluno e Professor calculam isso de forma diferente.
    public abstract boolean verificarLimiteEmprestimos();

    // ==========================================
    // GETTERS E SETTERS
    // ==========================================
    
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }
}