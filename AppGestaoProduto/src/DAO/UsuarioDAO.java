package DAO;

import modelo.Usuario;
import modelo.Aluno;
import modelo.Professor;
import util.ConectaBanco;

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

public class UsuarioDAO {

    // CREATE - Salvar um novo Usuário (Aluno ou Professor)
    public void cadastrar(Usuario usuario) {
        String sql = "INSERT INTO Usuario (matricula, nome, endereco, tipo_usuario, curso, departamento) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConectaBanco.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, usuario.getMatricula());
            stmt.setString(2, usuario.getNome());
            stmt.setString(3, usuario.getEndereco());

            // Verifica se o objeto é um Aluno ou Professor para preencher os dados corretos
            if (usuario instanceof Aluno) {
                Aluno aluno = (Aluno) usuario;
                stmt.setString(4, "ALUNO");
                stmt.setString(5, aluno.getCurso());
                stmt.setNull(6, java.sql.Types.VARCHAR); // Aluno não tem departamento
            } else if (usuario instanceof Professor) {
                Professor prof = (Professor) usuario;
                stmt.setString(4, "PROFESSOR");
                stmt.setNull(5, java.sql.Types.VARCHAR); // Professor não tem curso
                stmt.setString(6, prof.getDepartamento());
            }

            stmt.executeUpdate();
            System.out.println("Usuário " + usuario.getNome() + " cadastrado com sucesso!");

        } catch (SQLException e) {
            throw new RuntimeException("Falha no banco de dados: " + e.getMessage());
        }
    }

    // READ - Buscar um usuário pela matrícula
    public Usuario buscarPorMatricula(String matricula) {
        String sql = "SELECT * FROM Usuario WHERE matricula = ?";
        Usuario usuarioEncontrado = null;

        try (Connection conn = ConectaBanco.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, matricula);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String nome = rs.getString("nome");
                    String endereco = rs.getString("endereco");
                    String tipo = rs.getString("tipo_usuario");

                    // Reconstrói o objeto correto dependendo do tipo salvo no banco
                    if ("ALUNO".equals(tipo)) {
                        String curso = rs.getString("curso");
                        usuarioEncontrado = new Aluno(nome, matricula, endereco, curso);
                    } else if ("PROFESSOR".equals(tipo)) {
                        String departamento = rs.getString("departamento");
                        usuarioEncontrado = new Professor(nome, matricula, endereco, departamento);
                    }
                }
            }

        } catch (SQLException e) {
            System.err.println("Erro ao buscar usuário: " + e.getMessage());
        }

        return usuarioEncontrado;
    }

    // READ - Listar todos os usuários
    public List<Usuario> listarTodos() {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT * FROM Usuario";

        try (Connection conn = ConectaBanco.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String matricula = rs.getString("matricula");
                String nome = rs.getString("nome");
                String endereco = rs.getString("endereco");
                String tipo = rs.getString("tipo_usuario");

                if ("ALUNO".equals(tipo)) {
                    usuarios.add(new Aluno(nome, matricula, endereco, rs.getString("curso")));
                } else if ("PROFESSOR".equals(tipo)) {
                    usuarios.add(new Professor(nome, matricula, endereco, rs.getString("departamento")));
                }
            }

        } catch (SQLException e) {
            System.err.println("Erro ao listar usuários: " + e.getMessage());
        }

        return usuarios;
    }

    // DELETE - Remover usuário pela matrícula
    public void remover(String matricula) {
        String sql = "DELETE FROM Usuario WHERE matricula = ?";

        try (Connection conn = ConectaBanco.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, matricula);
            stmt.executeUpdate();
            System.out.println("Usuário removido com sucesso.");

        } catch (SQLException e) {
            System.err.println("Erro ao remover usuário: " + e.getMessage());
        }
    }
}
