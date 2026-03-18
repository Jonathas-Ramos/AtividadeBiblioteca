package DAO;

import modelo.Reserva;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author Jonathas Ramos
 */
public class ReservaDAO {
    
    // Lista em memória para simular o banco de dados das reservas
    private List<Reserva> bancoReservas = new ArrayList<>();

    // CREATE - Salva uma nova reserva
    public void cadastrar(Reserva reserva) {
        bancoReservas.add(reserva);
        System.out.println("Reserva salva no banco de dados.");
    }

    // READ - Lista todas as reservas
    public List<Reserva> listarTodas() {
        return bancoReservas;
    }

    // DELETE - Cancela/Remove uma reserva
    public void remover(Reserva reserva) {
        bancoReservas.remove(reserva);
        System.out.println("Reserva removida do banco de dados.");
    }
}