package DAO;
import util.ConectaBanco;
import modelo.Livro;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jonathas Ramos
 */
public class LivroDAO {

    // CREATE - Inserir livro na tabela SQL
    public void cadastrar(Livro livro) {
        String sql = "INSERT INTO Livro (id, titulo, autor, ano_publicacao, quantidade_disponivel, categoria) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConectaBanco.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Pegando os dados REAIS do objeto Livro que veio da tela
            stmt.setString(1, livro.getId());
            stmt.setString(2, livro.getTitulo());
            stmt.setString(3, livro.getAutor());
            stmt.setInt(4, 0); // Agora pega o ano real!
            stmt.setInt(5, livro.getQuantidadeDisponivel());
            stmt.setString(6, livro.getCategoria());

            // Executa a inserção no banco
            stmt.executeUpdate();

        } catch (SQLException e) {
            // Em vez de só imprimir no console, JOGA o erro para a Tela explodir o pop-up vermelho!
            throw new RuntimeException("Erro ao salvar no banco de dados: " + e.getMessage());
        }
    }

    // READ - Buscar todos os livros da tabela SQL
    public List<Livro> listarTodos() {
        List<Livro> livros = new ArrayList<>();
        String sql = "SELECT * FROM Livro";

        try (Connection conn = ConectaBanco.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            // O ResultSet (rs) funciona como um cursor lendo linha por linha da tabela
            while (rs.next()) {
                String id = rs.getString("id");
                String titulo = rs.getString("titulo");
                String autor = rs.getString("autor");
                int ano = rs.getInt("ano_publicacao");
                int qtd = rs.getInt("quantidade_disponivel");
                String categoria = rs.getString("categoria");

                // Monta o objeto Java com os dados que vieram do SQL
                Livro livro = new Livro(id, titulo, autor, ano, qtd, categoria);
                livros.add(livro);
            }

        } catch (SQLException e) {
            System.err.println("Erro ao listar os livros: " + e.getMessage());
        }

        return livros;
    }
    
    // READ - Buscar um livro específico pelo ID
    public Livro buscarPorId(String id) {
        String sql = "SELECT * FROM Livro WHERE id = ?";
        Livro livroEncontrado = null;

        try (java.sql.Connection conn = util.ConectaBanco.getConexao(); 
             java.sql.PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, id); // Troca o "?" pelo ID que estamos buscando

            try (java.sql.ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    // Se achou no banco, pega os dados da linha
                    String titulo = rs.getString("titulo");
                    String autor = rs.getString("autor");
                    int ano = rs.getInt("ano_publicacao");
                    int qtd = rs.getInt("quantidade_disponivel");
                    String categoria = rs.getString("categoria");

                    // Monta o objeto Livro para devolver ao Controle
                    livroEncontrado = new Livro(id, titulo, autor, ano, qtd, categoria);
                }
            }

        } catch (java.sql.SQLException e) {
            System.err.println("Erro ao buscar o livro por ID: " + e.getMessage());
        }

        return livroEncontrado;
    }

    // UPDATE - Atualizar a quantidade disponível
    public void atualizar(Livro livro) {
        String sql = "UPDATE Livro SET quantidade_disponivel = ? WHERE id = ?";

        try (Connection conn = ConectaBanco.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, livro.getQuantidadeDisponivel());
            stmt.setString(2, livro.getId());

            stmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Erro ao atualizar o livro: " + e.getMessage());
        }
    }

    // DELETE - Remover um livro pelo ID
    public void remover(String id) {
        String sql = "DELETE FROM Livro WHERE id = ?";

        try (Connection conn = ConectaBanco.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Erro ao deletar o livro: " + e.getMessage());
        }
    }
    
    
    
}