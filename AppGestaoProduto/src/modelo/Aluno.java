package modelo;

/**
 *
 * @author Jonathas Ramos
 */
public class Aluno extends Usuario {
    
    private String curso;
    private int limiteEmprestimos;

    public Aluno(String nome, String matricula, String endereco, String curso) {
        // O "super" chama o construtor da classe pai (Usuario)
        super(nome, matricula, endereco);
        this.curso = curso;
        this.limiteEmprestimos = 3; // Regra de negócio: aluno pega até 3 livros
    }

    // Implementação obrigatória do método abstrato do Usuario
    @Override
    public boolean verificarLimiteEmprestimos() {
        return true; 
    }

    public String getCurso() { return curso; }
    public void setCurso(String curso) { this.curso = curso; }
}