package control.user;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.indirizzo.Indirizzo;
import model.indirizzo.IndirizzoDAO;
import model.utente.Utente;
import model.utente.UtenteDAO;
import model.utils.Validation;

@WebServlet("/user/GestioneProfiloServlet")
public class GestioneProfiloServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public GestioneProfiloServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect(request.getContextPath() + "/user/AreaUtente");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
	    Utente utente = (Utente) session.getAttribute("utente");
	    
		String action = request.getParameter("action");
		
		if (action == null || action.trim().isEmpty()) {
			response.sendRedirect(request.getContextPath() + "/user/AreaUtente");
			return;
		}
		
		IndirizzoDAO indirizzoDAO = new IndirizzoDAO();
		UtenteDAO utenteDAO = new UtenteDAO();
		
		try {
			switch (action) {
				case "modificaUtente":
					String nome = request.getParameter("nome");
					String cognome = request.getParameter("cognome");
					String email = request.getParameter("email");
					String telefono = request.getParameter("telefono");
					
					Map<String, String> errorMap = new HashMap<>();
					
					if (nome == null || nome.trim().isEmpty()) {
						errorMap.put("errorNome", "Nome obbligatorio");
					} else if (nome.trim().length() < 2 || nome.trim().length() > 50) {
					    errorMap.put("errorNome", "Il nome deve essere compreso tra 2 e 50 caratteri");
					}
					
					if (cognome == null || cognome.trim().isEmpty()) {
						errorMap.put("errorCognome", "Cognome obbligatorio");
					} else if (cognome.trim().length() < 2 || cognome.trim().length() > 50) {
					    errorMap.put("errorCognome", "Il cognome deve essere compreso tra 2 e 50 caratteri");
					}
					
					if (email == null || email.trim().isEmpty()) {
						errorMap.put("errorEmail", "Email obbligatoria");
					} else if (!Validation.validateEmail(email)) {
						errorMap.put("errorEmail", "Formato email non valido");
					}
					
					if (telefono == null || telefono.trim().isEmpty()) {
						errorMap.put("errorTelefono", "Telefono obbligatorio");
					} else if (!Validation.validateTelefono(telefono)) {
						errorMap.put("errorTelefono", "Formato telefono non valido");
					}
					
					if (errorMap.isEmpty() && !email.equals(utente.getEmail())) {
						Utente controlloUtente = utenteDAO.doRetrieveByEmail(email);
						if(controlloUtente != null) {
							errorMap.put("errorEmail", "Email già registrata da un altro utente");
						}
					}
					
					if (!errorMap.isEmpty()) {
						session.setAttribute("errorMap", errorMap);
						break;
					}
					
					utente.setNome(nome);
					utente.setCognome(cognome);
					utente.setEmail(email);
					utente.setTelefono(telefono);
					
					utenteDAO.doUpdate(utente);
					session.setAttribute("utente", utente);
					session.setAttribute("success", "Profilo aggiornato con successo!");
					
					break;
				
				case "aggiungiIndirizzo":
					String via = request.getParameter("via");
					String citta = request.getParameter("citta");
					String cap = request.getParameter("cap");
					String provincia = request.getParameter("provincia");
					String nazione = request.getParameter("nazione");
					
					if (via == null || via.trim().isEmpty() || citta == null || citta.trim().isEmpty() || cap == null || cap.trim().isEmpty() || 
							provincia == null || provincia.trim().isEmpty() || nazione == null || nazione.trim().isEmpty()) {
						request.getRequestDispatcher("/400.jsp").forward(request, response);
			            return;
					}
					
					Indirizzo indirizzo = new Indirizzo();
					indirizzo.setUtente(utente.getIdUtente());
					indirizzo.setVia(via);
					indirizzo.setCitta(citta);
					indirizzo.setCap(cap);
					indirizzo.setProvincia(provincia);
					indirizzo.setNazione(nazione);
					
					
					indirizzoDAO.doSave(indirizzo);
					session.setAttribute("success", "Nuovo indirizzo salvato con successo!");
					
					break;
					
				case "rimuoviIndirizzo":
					String idIndirizzoParam = request.getParameter("idIndirizzo");
					Long idIndirizzo = null;
					
					if (idIndirizzoParam != null && !idIndirizzoParam.trim().isEmpty()) {
						try {
							idIndirizzo = Long.parseLong(idIndirizzoParam);
						} catch (NumberFormatException e) {
							idIndirizzo = null; 
						}
					}
					
					if (idIndirizzo == null) {
				        request.getRequestDispatcher("/400.jsp").forward(request, response);
				        return;
				    }
					
					Indirizzo daCancellare = indirizzoDAO.doRetrieveByKey(idIndirizzo);
					if ( daCancellare == null || !(daCancellare.getUtente() == utente.getIdUtente())) {
					    request.setAttribute("error", "Non hai i permessi per eliminare questo indirizzo.");
					    request.getRequestDispatcher("/403.jsp").forward(request, response);
					    return;
					}
					
					indirizzoDAO.doDelete(idIndirizzo);
					session.setAttribute("success", "Indirizzo eliminato con successo!");
					
					break;
			}
		} catch (SQLException s) {
			s.printStackTrace();
			request.setAttribute("error", "Errore accesso al database: " + s.getMessage());
            request.getRequestDispatcher("/500.jsp").forward(request, response);
            return;
		}
		
		response.sendRedirect(request.getContextPath() + "/user/AreaUtente");
	}
}
