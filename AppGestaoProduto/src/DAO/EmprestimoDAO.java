package DAO;

import modelo.Emprestimo;
import util.ConectaBanco;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class EmprestimoDAO {

    public void registrarEmprestimo(Emprestimo emp) {
        String sql = "INSERT INTO Emprestimo (matricula_usuario, id_livro, data_retirada, data_prevista_devolucao, situacao) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = ConectaBanco.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            
            stmt.setString(1, emp.getUsuario().getMatricula()); 
            stmt.setString(2, emp.getLivro().getId());
            
            // Convertendo as datas do LocalDate para o SQL
            stmt.setDate(3, java.sql.Date.valueOf(emp.getDataRetirada()));
            stmt.setDate(4, java.sql.Date.valueOf(emp.getDataPrevistaDevolucao()));
            
            stmt.setString(5, emp.getSituacao());

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao registrar empréstimo no banco: " + e.getMessage());
        }
    }
    public void registrarDevolucaoNoBanco(String matricula, String idLivro) {
        // Atualiza apenas o empréstimo que está "Ativo"
        String sql = "UPDATE Emprestimo SET situacao = 'Devolvido' WHERE matricula_usuario = ? AND id_livro = ? AND situacao = 'Ativo'";
        try (java.sql.Connection conn = util.ConectaBanco.getConexao();
             java.sql.PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, matricula);
            stmt.setString(2, idLivro);
            stmt.executeUpdate();
            
        } catch (java.sql.SQLException e) {
            throw new RuntimeException("Erro ao devolver no banco: " + e.getMessage());
        }
    }
}