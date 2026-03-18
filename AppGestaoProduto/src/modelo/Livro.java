package modelo;

/**
 *
 * @author Jonathas Ramos
 */
public class Livro {
    private String id;
    private String titulo;
    private String autor; // Manipulação de autores é feita através deste atributo
    private int anoPublicacao;
    private int quantidadeDisponivel;
    private String categoria;

    public Livro(String id, String titulo, String autor, int anoPublicacao, int quantidadeDisponivel, String categoria) {
        this.id = id;
        this.titulo = titulo;
        this.autor = autor;
        this.anoPublicacao = anoPublicacao;
        this.quantidadeDisponivel = quantidadeDisponivel;
        this.categoria = categoria;
    }

    public boolean verificarDisponibilidade() {
        return this.quantidadeDisponivel > 0;
    }

    public void atualizarQuantidade(int qtd) {
        this.quantidadeDisponivel += qtd;
    }

    // Getters e Setters
    public String getId() { return id; }
    public String getTitulo() { return titulo; }
    public String getAutor() { return autor; }
    public int getQuantidadeDisponivel() { return quantidadeDisponivel; }
    public String getCategoria() { return categoria; }
    public int getAnoPublicacao() { return anoPublicacao; }
}