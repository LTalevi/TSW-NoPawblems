package control;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.utils.Encryption;
import model.utente.*;

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
		
		UtenteDAO utenteDAO= new UtenteDAO();
		Utente utente = null;
		
		try {
			utente = utenteDAO.doRetrieveByEmail(email);
		} catch (SQLException s){
			s.printStackTrace();
			request.setAttribute("error", "Errore durante l'accesso al database");
			request.getRequestDispatcher("login.jsp").forward(request, response);
			return; 
		}
		
		if (utente != null && utente.getPassword().equals(Encryption.hashPassword(password))) {
			HttpSession session = request.getSession();
			session.setAttribute("utente", utente);
			response.sendRedirect(request.getContextPath() + "/HomepageServlet");
		} else {
			request.setAttribute("error", "Email o password errate");
			request.getRequestDispatcher("login.jsp").forward(request, response);
		}
	}
}
