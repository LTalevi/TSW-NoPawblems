package control;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.utente.Utente;
import model.utente.UtenteDAO;
import model.utils.Encryption;
import model.utils.Validation;

@WebServlet("/RegistrazioneServlet")
public class RegistrazioneServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public RegistrazioneServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("Registrazione.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String nome = (String) request.getParameter("nome");
		String cognome = (String) request.getParameter("cognome");
		String email = (String) request.getParameter("email");
		String telefono = (String) request.getParameter("telefono");
		String password = (String) request.getParameter("password");
		String confermaPassword = (String) request.getParameter("confermaPassword");
		
		Map<String, String> errorMap = new HashMap<>();
		if (nome == null || nome.trim().isEmpty()) {
			errorMap.put("errorNome", "Nome obbligatorio");
		} else if (nome.trim().length() < 2 || nome.trim().length() > 50) {
		    errorMap.put("errorNome", "Il nome deve essere compreso tra 2 e 50 caratteri");
		}
		
		if (cognome == null || cognome.trim().isEmpty()) {
			errorMap.put("errorCognome", "Cognome obbligatorio");
		}else if (cognome.trim().length() < 2 || cognome.trim().length() > 50) {
		    errorMap.put("errorCognome", "Il cognome deve essere compreso tra 2 e 50 caratteri");
		}
		
		if (email == null || email.trim().isEmpty()) {
			errorMap.put("errorEmail", "Email obbligatorio");
		} else if (!Validation.validateEmail(email)) {
			errorMap.put("errorEmail", "Formato email non valido");
		}
		
		if (telefono == null || telefono.trim().isEmpty()) {
			errorMap.put("errorTelefono", "Telefono obbligatorio");
		} else if (!Validation.validateTelefono(telefono)) {
			errorMap.put("errorTelefono", "Formato telefono non valido");
		}
		
		if(password == null || password.trim().isEmpty()) {
			errorMap.put("errorPassword", "Password obbligatoria");
		} else if (password.length() < 8) {
			errorMap.put("errorPassword", "La password deve essere di almeno 8 caratteri");
		} else if (!password.equals(confermaPassword)) {
			errorMap.put("errorPassword", "Le password non concidono");
		}
		
		UtenteDAO utenteDAO = new UtenteDAO();
		
		if (errorMap.isEmpty()) {
			try {
				Utente controlloUtente = utenteDAO.doRetrieveByEmail(email);
		
				if(controlloUtente != null) {
					errorMap.put("errorMail", "Email gia registrata");
				}
			} catch (SQLException s) {
				System.err.println("Errore durante l'accesso al database" + s.getMessage());
                errorMap.put("serverError","Errore interno del server");
			}
		}
		
		if (!errorMap.isEmpty()) {
			request.setAttribute("errorMap", errorMap);
			request.getRequestDispatcher("Registrazione.jsp").forward(request, response);
			return;
		}
		
		Utente utente = new Utente();
		utente.setNome(nome);
		utente.setCognome(cognome);
		utente.setEmail(email);
		utente.setTelefono(telefono);
		utente.setPassword(Encryption.hashPassword(password));
		utente.setAdmin(false);
		
		try {
			utenteDAO.doSave(utente);
			
			response.sendRedirect(request.getContextPath() + "/LoginServlet?success=Registrazione completata con successo!");
		} catch (SQLException s) {
			System.err.println("Errore durante l'accesso al database" + s.getMessage());
			errorMap.put("serverError", "Errore interno del server");
            request.setAttribute("errorMap", errorMap);
            request.getRequestDispatcher("Registrazione.jsp").forward(request, response);
		}
	}
}
