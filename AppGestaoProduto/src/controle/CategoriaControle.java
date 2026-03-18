/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controle;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jonathas Ramos
 */
public class CategoriaControle {
        public static List<String> listarCategorias() throws ClassNotFoundException, SQLException {
        List<String> lista = new ArrayList<>();
        DAO.CategoriaDAO dao = new DAO.CategoriaDAO();
        List<modelo.Categoria> itens = dao.listarTodas();
        for (modelo.Categoria c : itens ) {
            lista.add(c.getNome());
        }
        return lista;
    }

    private static class SQLException extends Exception {

        public SQLException() {
        }
    }
}
