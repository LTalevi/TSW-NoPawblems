package control;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.prodottocarrello.ProdottoCarrello;
import model.prodottocarrello.ProdottoCarrelloDAO;
import model.utente.Utente;
import model.utente.UtenteDAO;
import model.utils.Encryption;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public LoginServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String error = request.getParameter("error");
	    String success = request.getParameter("success");

	    if (error != null && !error.trim().isEmpty()) {
	        request.setAttribute("error", error);
	    }

	    if (success != null && !success.trim().isEmpty()) {
	        request.setAttribute("success", success);
	    }

	    request.getRequestDispatcher("login.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		
		if (email == null || email.trim().isEmpty() || password == null || password.trim().isEmpty()) {
			request.setAttribute("error", "Inserire email e password");
	        request.getRequestDispatcher("login.jsp").forward(request, response);
	        return;
		}
		
		UtenteDAO utenteDAO = new UtenteDAO();
		Utente utente = null;
		
		try {
			utente = utenteDAO.doRetrieveByEmail(email);
		} catch (SQLException s){
			s.printStackTrace();
			request.setAttribute("error", "Errore durante l'accesso al database");
			request.getRequestDispatcher("login.jsp").forward(request, response);
			return; 
		}
		
		HttpSession session = request.getSession();
		
		if (utente != null && utente.getPassword().equals(Encryption.hashPassword(password))) {
			session.setAttribute("utente", utente);
		} else {
			request.setAttribute("error", "Email o password errate");
			request.getRequestDispatcher("login.jsp").forward(request, response);
			return;
		}
		
		ProdottoCarrelloDAO prodottoCarrelloDAO = new ProdottoCarrelloDAO();
		List<ProdottoCarrello> carrello = (List<ProdottoCarrello>) session.getAttribute("carrello");
		
		if (carrello != null) {
			for (ProdottoCarrello item : carrello) {
				item.setUtente(utente.getIdUtente());
				try {
					prodottoCarrelloDAO.doSave(item);
				} catch (SQLException s){
					s.printStackTrace();
					request.setAttribute("error", "Errore nell'aggiornamento dal carrello: " + s.getMessage());
		            request.getRequestDispatcher("/500.jsp").forward(request, response);
		            return;
				}
	        }
		}
		
		session.removeAttribute("carrello");
		session.removeAttribute("numeroPezziCarrello");
		
		response.sendRedirect(request.getContextPath() + "/HomepageServlet");
	}
}
