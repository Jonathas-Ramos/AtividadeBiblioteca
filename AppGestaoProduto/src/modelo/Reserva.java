package modelo;

import java.util.Date;
/**
 *
 * @author Jonathas Ramos
 */
public class Reserva {
    private Date dataSolicitacao;
    private Date dataExpiracao;
    private Livro livro;
    private Usuario usuario;
    private boolean ativa;

    public Reserva(Usuario usuario, Livro livro, Date dataSolicitacao, Date dataExpiracao) {
        this.usuario = usuario;
        this.livro = livro;
        this.dataSolicitacao = dataSolicitacao;
        this.dataExpiracao = dataExpiracao;
        this.ativa = true;
    }

    public boolean verificarValidade() {
        return ativa && new Date().before(dataExpiracao);
    }

    public void cancelarReserva() {
        this.ativa = false;
    }
    
    // Getters
    public Livro getLivro() { return livro; }
    public Usuario getUsuario() { return usuario; }
}
