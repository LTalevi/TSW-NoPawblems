package control;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.prodotto.Prodotto;
import model.prodotto.ProdottoDAO;
import model.prodottocarrello.ProdottoCarrello;
import model.prodottocarrello.ProdottoCarrelloDAO;
import model.utente.Utente;

@WebServlet(name = "HomeServlet", urlPatterns = {"", "/HomeServlet"})
public class HomeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public HomeServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		
		Utente utente = (Utente) session.getAttribute("utente");
		
		List<ProdottoCarrello> carrello = null;
		
		if (utente != null) {
			try {
				ProdottoCarrelloDAO prodottoCarrelloDAO = new ProdottoCarrelloDAO();
				carrello = prodottoCarrelloDAO.doRetrieveByUtente(utente.getIdUtente());
			} catch (SQLException s){
				s.printStackTrace();
				request.setAttribute("error", "Errore accesso al db: " + s.getMessage());
	            request.getRequestDispatcher("/Errore500.jsp").forward(request, response);
	            return;
			}
		} else {
			carrello = (List<ProdottoCarrello>) session.getAttribute("carrello");
			
			if (carrello == null) {
	            carrello = new ArrayList<ProdottoCarrello>();
	            session.setAttribute("carrello", carrello);
	        }
		} 
		
		int numeroPezziCarrello = 0;
		for (ProdottoCarrello item : carrello) {
		    numeroPezziCarrello += item.getQuantita();
		}
		
		ProdottoDAO prodottoDAO = new ProdottoDAO();
		
		List<Prodotto> prodottiOfferta = null;
		List<Prodotto> prodottiCane = null;
		List<Prodotto> prodottiGatto = null;
		
		try {
			prodottiOfferta = prodottoDAO.doRetrieveByFilter(null, null, null, 50f, null, "prezzoCrescente", true);
			prodottiCane = prodottoDAO.doRetrieveByFilter(1l, null, null, null, null, "nomeAZ", true);
			prodottiGatto = prodottoDAO.doRetrieveByFilter(2l, null, null, null, null, null, true);
		} catch (SQLException s) {
			s.printStackTrace();
			request.setAttribute("error", "Errore accesso al db: " + s.getMessage());
            request.getRequestDispatcher("/Errore500.jsp").forward(request, response);
            return;
		}
		
		session.setAttribute("carrello", carrello);
		session.setAttribute("numeroPezziCarrello", numeroPezziCarrello);
		request.setAttribute("prodottiOfferta", prodottiOfferta);
		request.setAttribute("prodottiCane", prodottiCane);
		request.setAttribute("prodottiGatto", prodottiGatto);
		
		request.getRequestDispatcher("Home.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
