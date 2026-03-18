package modelo;

/**
 *
 * @author Jonathas Ramos
 */
public class Professor extends Usuario {
    
    private String departamento;
    private int limiteEmprestimos;

    public Professor(String nome, String matricula, String endereco, String departamento) {
        super(nome, matricula, endereco);
        this.departamento = departamento;
        this.limiteEmprestimos = 5; // Regra de negócio: professor pega até 5 livros
    }

    @Override
    public boolean verificarLimiteEmprestimos() {
        return true; // Lógica de validação viria aqui
    }

    public String getDepartamento() { return departamento; }
    public void setDepartamento(String departamento) { this.departamento = departamento; }
}