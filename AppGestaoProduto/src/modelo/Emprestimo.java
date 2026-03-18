package modelo;

import java.time.LocalDate;
import java.util.Date;
import java.time.temporal.ChronoUnit;

public class Emprestimo {
    private Usuario usuario;
    private Livro livro;
    private LocalDate dataRetirada;
    private LocalDate dataPrevistaDevolucao;
    private String situacao;

    // CONSTRUTOR 
    public Emprestimo(Usuario usuario, Livro livro, LocalDate dataRetirada, LocalDate dataPrevistaDevolucao) {
        this.usuario = usuario;
        this.livro = livro;
        this.dataRetirada = dataRetirada;
        this.dataPrevistaDevolucao = dataPrevistaDevolucao;
        this.situacao = "Ativo"; 
    }

    // MÉTODO REGISTRAR DEVOLUÇÃO 
    public void registrarDevolucao(Date data) {
        this.situacao = "Devolvido";
    }

    // MÉTODO CALCULAR MULTA
    public Multa calcularMulta() {
        LocalDate dataHoje = LocalDate.now();
        
        // Se a data de hoje for DEPOIS da data prevista, deu ruim (atrasou!)
        if (dataHoje.isAfter(dataPrevistaDevolucao)) {
            // Calcula quantos dias de atraso
            long diasAtraso = ChronoUnit.DAYS.between(dataPrevistaDevolucao, dataHoje);
            return new Multa((int) diasAtraso); 
        }
        
        return null; // Se não entrou no IF, entregou no prazo. Zero multa!
    }

    // Getters
    public Usuario getUsuario() { return usuario; }
    public Livro getLivro() { return livro; }
    public LocalDate getDataRetirada() { return dataRetirada; }
    public LocalDate getDataPrevistaDevolucao() { return dataPrevistaDevolucao; }
    public String getSituacao() { return situacao; }
}