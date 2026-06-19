package control;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonObject;

import model.utente.Utente;
import model.utente.UtenteDAO;

@WebServlet("/EmailCheck")
public class EmailCheck extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public EmailCheck() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json");

        String email = request.getParameter("email");
        JsonObject json = new JsonObject();

        if (email == null || email.trim().isEmpty()) {
            json.addProperty("esiste", false);
            response.getWriter().print(json.toString());
            return;
        }

        try {
            UtenteDAO utenteDAO = new UtenteDAO();
            Utente utente = utenteDAO.doRetrieveByEmail(email);

            if (utente == null) {
            	json.addProperty("esiste", false);
                
            } else {
            	json.addProperty("esiste", true);
            }

            response.getWriter().print(json.toString());
        } catch (Exception e) {
            e.printStackTrace(); 

            JsonObject jsonErrore = new JsonObject();
            jsonErrore.addProperty("error", "Errore nel check dell'email: " + e.getMessage());

            response.getWriter().print(jsonErrore.toString());
        }
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
