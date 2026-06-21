package control.user;

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

import model.ordine.Ordine;
import model.ordine.OrdineDAO;
import model.prodotto.Prodotto;
import model.prodotto.ProdottoDAO;
import model.utente.Utente;

@WebServlet("/user/DettaglioOrdine")
public class DettaglioOrdine extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public DettaglioOrdine() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
	    Utente utente = (Utente) session.getAttribute("utente");
	    
		String idOrdineParam = request.getParameter("idOrdine");
		Long idOrdine = null;
		OrdineDAO ordineDAO = new OrdineDAO();
		Ordine ordine = null;
		ProdottoDAO prodottoDAO = new ProdottoDAO();
		List<Prodotto> prodottiDettaglio = new ArrayList<>();
		
		if (idOrdineParam != null && !idOrdineParam.trim().isEmpty()) {
			try {
				idOrdine = Long.parseLong(idOrdineParam);
			} catch (NumberFormatException e) {
				idOrdine = null; 
			}
		}
		
		if (idOrdine == null) {
	        request.getRequestDispatcher("/400.jsp").forward(request, response);
	        return;
	    }
		
		try {
			ordine = ordineDAO.doRetrieveByKey(idOrdine);
			
			if (ordine == null) {
                request.setAttribute("error", "Ordine non trovato.");
                request.getRequestDispatcher("/Errore404.jsp").forward(request, response);
                return;
            }
            
            if (!(ordine.getUtente() == utente.getIdUtente()) && !(utente.isAdmin())) {
                request.setAttribute("error", "Non hai i permessi per visualizzare questo ordine.");
                request.getRequestDispatcher("/403.jsp").forward(request, response);
                return;
            } 
			
		    for (model.dettaglioordine.DettaglioOrdine item : ordine.getDettagli()) {
		            try {
		                long idPadre = item.getVariante().getProdottoPadre();
		                Prodotto prodotto = prodottoDAO.doRetrieveByKey(idPadre);
		                prodottiDettaglio.add(prodotto);
		            } catch (SQLException e) {
		                e.printStackTrace();
		                prodottiDettaglio.add(null);
		            }
		        }
		} catch (SQLException s) {
			s.printStackTrace();
			request.setAttribute("error", "Errore accesso al database: " + s.getMessage());
            request.getRequestDispatcher("/Errore500.jsp").forward(request, response);
            return;
		}
		
		request.setAttribute("ordine", ordine);
		request.setAttribute("prodottiDettaglio", prodottiDettaglio);
		
		request.getRequestDispatcher("/DettaglioOrdine.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
