package control.user;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.indirizzo.Indirizzo;
import model.indirizzo.IndirizzoDAO;
import model.ordine.Ordine;
import model.ordine.OrdineDAO;
import model.prodotto.Prodotto;
import model.prodotto.ProdottoDAO;
import model.prodottocarrello.ProdottoCarrello;
import model.prodottocarrello.ProdottoCarrelloDAO;
import model.utente.Utente;

@WebServlet("/user/CheckoutServlet")
public class CheckoutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public CheckoutServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		Utente utente = (Utente) session.getAttribute("utente");
		if (utente == null) {
		    request.setAttribute("error", "Sessione scaduta o utente non loggato.");
		    request.getRequestDispatcher("/Login.jsp").forward(request, response); 
		    return;
		}
		
		ProdottoCarrelloDAO prodottoCarrelloDAO = new ProdottoCarrelloDAO();
		IndirizzoDAO indirizzoDAO = new IndirizzoDAO();
		List<ProdottoCarrello> carrello = null;
		List<Indirizzo> indirizzi = null;
		
		try {
			carrello = prodottoCarrelloDAO.doRetrieveByUtente(utente.getIdUtente());
		} catch (SQLException s) {
			s.printStackTrace();
			request.setAttribute("error", "Errore accesso al db: " + s.getMessage());
            request.getRequestDispatcher("/Errore500.jsp").forward(request, response);
            return;
		}
		
		if (carrello == null || carrello.isEmpty()) {
			request.setAttribute("error", "Carrello vuoto");
			request.getRequestDispatcher("/Carrello.jsp").forward(request, response);
			return;
		}
		
		float totale = 0.0f;
		int numeroPezziCarrello = 0;
		List<Prodotto> prodottiDettaglio = new ArrayList<>();
		ProdottoDAO prodottoDAO = new ProdottoDAO();
		
		for (ProdottoCarrello item : carrello) {
			totale += (item.getVariante().getPrezzo() * item.getQuantita());
			numeroPezziCarrello += item.getQuantita();
			
			try {
				long idPadre = item.getVariante().getProdottoPadre();
				Prodotto p = prodottoDAO.doRetrieveByKey(idPadre);
				prodottiDettaglio.add(p);
			} catch (SQLException e) {
				e.printStackTrace();
				prodottiDettaglio.add(null);
			}
		}
		
		try {
			indirizzi = indirizzoDAO.doRetrieveByUtente(utente.getIdUtente());
		} catch (SQLException s) {
			s.printStackTrace();
			request.setAttribute("error", "Errore accesso al db: " + s.getMessage());
            request.getRequestDispatcher("/Errore500.jsp").forward(request, response);
            return;
		}
		
		request.setAttribute("indirizzi", indirizzi);
		request.setAttribute("carrello", carrello);
		request.setAttribute("totale", totale);
		request.setAttribute("prodottiDettaglio", prodottiDettaglio);
		request.setAttribute("numeroPezziCarrello", numeroPezziCarrello);
		
		request.getRequestDispatcher("/Checkout.jsp").forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String via = request.getParameter("via");
		String citta = request.getParameter("citta");
		String cap = request.getParameter("cap");
		String provincia = request.getParameter("provincia");
		String nazione = request.getParameter("nazione");
		String salvaIndirizzoParam = request.getParameter("salvaIndirizzo");
		Boolean salvaIndirizzo = (salvaIndirizzoParam != null);
		
		if (via == null || via.trim().isEmpty() || citta == null || citta.trim().isEmpty() || cap == null || cap.trim().isEmpty() || 
				provincia == null || provincia.trim().isEmpty() || nazione == null || nazione.trim().isEmpty()) {
			request.getRequestDispatcher("/Errore500.jsp").forward(request, response);
            return;
		}
		
		HttpSession session = request.getSession();
		Utente utente = (Utente) session.getAttribute("utente");
		if (utente == null) {
		    request.setAttribute("error", "Sessione scaduta o utente non loggato.");
		    request.getRequestDispatcher("/Login.jsp").forward(request, response); 
		    return;
		}
		
		ProdottoCarrelloDAO prodottoCarrelloDAO = new ProdottoCarrelloDAO();
		OrdineDAO ordineDAO = new OrdineDAO();
		IndirizzoDAO indirizzoDAO = new IndirizzoDAO();
		List<ProdottoCarrello> carrello = null;
		Ordine ordine = new Ordine();
		
		try {
			carrello = prodottoCarrelloDAO.doRetrieveByUtente(utente.getIdUtente());
		} catch (SQLException s) {
			s.printStackTrace();
			request.setAttribute("error", "Errore accesso al db: " + s.getMessage());
            request.getRequestDispatcher("/Errore500.jsp").forward(request, response);
            return;
		}
		
		if (carrello == null || carrello.isEmpty()) {
			request.setAttribute("error", "Carrello vuoto");
			request.getRequestDispatcher("/Carrello.jsp").forward(request, response);
			return;
		}
		
		float totale = 0.0f;
		for (ProdottoCarrello prodotto : carrello) {
			totale += (prodotto.getVariante().getPrezzo() * prodotto.getQuantita());
		}
		
		try {
			ordine.setUtente(utente.getIdUtente());
			ordine.setViaSpedizione(via);
			ordine.setCittaSpedizione(citta);
		    ordine.setCapSpedizione(cap);
		    ordine.setProvinciaSpedizione(provincia);
		    ordine.setNazioneSpedizione(nazione);
		    ordine.setDataOrdine(LocalDateTime.now()); 
	        ordine.setStato("InElaborazione");
	        ordine.setTotale(totale);
	        java.time.format.DateTimeFormatter dtf = java.time.format.DateTimeFormatter.ofPattern("yyyy-MMddHHmmss");
	        ordine.setNumeroFattura("FATT-" + java.time.LocalDateTime.now().format(dtf));
		   
			ordineDAO.doSaveOrdineCompleto(ordine, carrello);
			
			if(salvaIndirizzo) {
				Indirizzo indirizzo = new Indirizzo();
				indirizzo.setUtente(utente.getIdUtente());
				indirizzo.setVia(via);
				indirizzo.setCitta(citta);
				indirizzo.setCap(cap);
				indirizzo.setProvincia(provincia);
				indirizzo.setNazione(nazione);
				
				indirizzoDAO.doSave(indirizzo);
			}
			
			prodottoCarrelloDAO.doClearCarrello(utente.getIdUtente());
		} catch (SQLException s) {
			s.printStackTrace();
			request.setAttribute("error", "Errore impossibilie completare l'ordine: " + s.getMessage());
            request.getRequestDispatcher("/Errore500.jsp").forward(request, response);
            return;
		}
		
		session.setAttribute("numeroPezziCarrello", 0); 
		response.sendRedirect(request.getContextPath() + "/ConfermaOrdine.jsp");
	}
}