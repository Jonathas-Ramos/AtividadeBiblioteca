package controle;

import DAO.LivroDAO;
import DAO.ReservaDAO;
import DAO.EmprestimoDAO;
import DAO.UsuarioDAO;
import modelo.Livro;
import modelo.Multa;
import modelo.Reserva;
import modelo.Usuario;
import modelo.Emprestimo;
import java.util.Date;
import java.util.Calendar;
import java.time.LocalDate; 

/**
 *
 * @author Jonathas Ramos
 */
public class BibliotecaControle {
    
    // Instanciando todos os DAOs para o Controle conversar com o Banco de Dados
    private LivroDAO livroDAO = new LivroDAO();
    private ReservaDAO reservaDAO = new ReservaDAO();
    private EmprestimoDAO emprestimoDAO = new EmprestimoDAO();
    private UsuarioDAO usuarioDAO = new UsuarioDAO();

    // ==========================================
    // MÉTODOS DE LIVROS
    // ==========================================

    public void cadastrarNovoLivro(String id, String titulo, String autor, int ano, int qtd, String categoria) {
        
        // 1. A TRAVA DE SEGURANÇA: Se for negativo, "grita" um erro para a Tela capturar no try-catch!
        if (qtd < 0) {
            throw new IllegalArgumentException("A quantidade de livros não pode ser negativa!");
        }

        // 2. Se passou da trava (qtd é 0 ou maior), continua o cadastro e envia para o DAO
        Livro novoLivro = new Livro(id, titulo, autor, ano, qtd, categoria);
        livroDAO.cadastrar(novoLivro);
    }

    public void removerLivro(String idLivro) {
        livroDAO.remover(idLivro);
        System.out.println("Livro removido do sistema.");
    }

    // ==========================================
    // MÉTODOS DE EMPRÉSTIMO E DEVOLUÇÃO
    // ==========================================

    public Emprestimo realizarEmprestimo(String matriculaUsuario, String idLivro) {
        Usuario usuario = usuarioDAO.buscarPorMatricula(matriculaUsuario);
        Livro livro = livroDAO.buscarPorId(idLivro);

        if (usuario == null || livro == null) {
            throw new IllegalArgumentException("Usuário ou Livro não encontrado no banco de dados!");
        }

        if (!usuario.verificarLimiteEmprestimos()) {
            throw new IllegalArgumentException("Usuário atingiu o limite de empréstimos permitidos.");
        }

        if (!livro.verificarDisponibilidade()) {
            throw new IllegalArgumentException("Livro indisponível no estoque no momento.");
        }

        // Tira 1 do estoque e SALVA A ALTERAÇÃO NO BANCO DE DADOS
        livro.atualizarQuantidade(-1);
        livroDAO.atualizar(livro); 

        // Calcula data de devolução (7 dias) usando LocalDate
        LocalDate dataRetirada = LocalDate.now();
        LocalDate dataPrevista = dataRetirada.plusDays(7);

        // Agora passamos os OBJETOS inteiros
        Emprestimo novoEmprestimo = new Emprestimo(usuario, livro, dataRetirada, dataPrevista);
    
        // Chama o método com o nome correto que criamos no DAO
        emprestimoDAO.registrarEmprestimo(novoEmprestimo);
        
        return novoEmprestimo;
    }

    public Multa devolverLivro(Emprestimo emprestimo) {
        Date dataAtual = new Date();
        
        // O método registrarDevolucao já devolve a quantidade (+1) pro objeto Livro
        emprestimo.registrarDevolucao(dataAtual);

        // Verifica se gerou multa
        Multa multa = emprestimo.calcularMulta();
        if (multa != null) {
            System.out.println("Atenção: Multa gerada por atraso no valor de R$ " + multa.getValor());
        }
        
        return multa;
    }

    // ==========================================
    // MÉTODOS DE RESERVA
    // ==========================================

    public void reservarLivro(String matriculaUsuario, String idLivro) {
        Usuario usuario = usuarioDAO.buscarPorMatricula(matriculaUsuario);
        Livro livro = livroDAO.buscarPorId(idLivro);
        
        if (usuario != null && livro != null) {
            Date dataSolicitacao = new Date();
            Calendar cal = Calendar.getInstance();
            cal.setTime(dataSolicitacao);
            cal.add(Calendar.DAY_OF_MONTH, 3); // Reserva vale por 3 dias
            Date dataExpiracao = cal.getTime();
            
            Reserva novaReserva = new Reserva(usuario, livro, dataSolicitacao, dataExpiracao);
            
            // DESCOMENTADO: Agora a reserva é salva de verdade no banco!
            reservaDAO.cadastrar(novaReserva); 
            
        } else {
            throw new IllegalArgumentException("Erro ao reservar: Usuário ou Livro não encontrados no banco.");
        }
    }
    
    public void processarDevolucao(String matricula, String idLivro) {
        // 1. Puxa o livro para devolver a quantidade (+1)
        Livro livro = livroDAO.buscarPorId(idLivro);
        if (livro != null) {
            livro.atualizarQuantidade(1);
            livroDAO.atualizar(livro); // Salva o novo estoque no banco
        }

        // 2. Muda o status no banco para "Devolvido"
        emprestimoDAO.registrarDevolucaoNoBanco(matricula, idLivro);
    }
}