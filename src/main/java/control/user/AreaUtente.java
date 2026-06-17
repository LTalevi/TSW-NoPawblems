package control.user;

import java.io.IOException;
import java.sql.SQLException;
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
import model.utente.Utente;

@WebServlet("/user/AreaUtente")
public class AreaUtente extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public AreaUtente() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
	    Utente utente = (Utente) session.getAttribute("utente");
	    
	    OrdineDAO ordineDAO = new OrdineDAO();
        IndirizzoDAO indirizzoDAO = new IndirizzoDAO();
        List<Ordine> ordini = null;
        List<Indirizzo> indirizzi = null;

    	try {
    		ordini = ordineDAO.doRetrieveByUtente(utente.getIdUtente());
    		indirizzi = indirizzoDAO.doRetrieveByUtente(utente.getIdUtente());
    	} catch (SQLException s) {
    		s.printStackTrace();
			request.setAttribute("error", "Errore accesso al database: " + s.getMessage());
            request.getRequestDispatcher("/Errore500.jsp").forward(request, response);
            return;
    	}
    	
    	request.setAttribute("ordini", ordini);
    	request.setAttribute("indirizzi", indirizzi);
    	
    	request.getRequestDispatcher("/AreaUtente.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
