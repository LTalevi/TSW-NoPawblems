package control;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.prodotto.Prodotto;
import model.prodotto.ProdottoDAO;

@WebServlet("/CatalogoServlet")
public class CatalogoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public CatalogoServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String idCategoriaParam = request.getParameter("idCategoria");
		String prezzoMinParam = request.getParameter("prezzoMin");
		String prezzoMaxParam = request.getParameter("prezzoMax");
		String ricerca = request.getParameter("ricerca");
		String ordinamento = request.getParameter("ordinamento"); 

		Long idCategoria = null;
		Float prezzoMin = null;
		Float prezzoMax = null;
		
		if (idCategoriaParam != null && !idCategoriaParam.trim().isEmpty()) {
			try {
				idCategoria = Long.parseLong(idCategoriaParam);
			} catch (NumberFormatException e) {
				idCategoria = null; 
			}
		}
		
		if (prezzoMinParam != null && !prezzoMinParam.trim().isEmpty()) {
			try {
				prezzoMin = Float.parseFloat(prezzoMinParam);
			} catch (NumberFormatException e) {
				prezzoMin = null;
			}
		}
		
		if (prezzoMaxParam != null && !prezzoMaxParam.trim().isEmpty()) {
			try {
				prezzoMax = Float.parseFloat(prezzoMaxParam);
			} catch (NumberFormatException e) {
				prezzoMax = null;
			}
		}
		
		ProdottoDAO prodottoDAO = new ProdottoDAO();
		
		try {
			List<Prodotto> catalogo = prodottoDAO.doRetrieveByFilter(idCategoria, prezzoMin, prezzoMax, ricerca, ordinamento);
			
			request.setAttribute("prodotti", catalogo);
			request.setAttribute("idCategoriaSelezionata", idCategoria);
			request.setAttribute("prezzoMinInserito", prezzoMin);
			request.setAttribute("prezzoMaxInserito", prezzoMax);
			request.setAttribute("ricercaInserita", ricerca);
			request.setAttribute("ordinamentoSelezionato", ordinamento);
		} catch (SQLException s){
			s.printStackTrace();
			request.setAttribute("error", "Errore nella ricerca dei prodotti: " + s.getMessage());
            request.getRequestDispatcher("/500.jsp").forward(request, response);
            return;
		}
		
		request.getRequestDispatcher("catalogo.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
