package control.admin;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.ordine.Ordine;
import model.ordine.OrdineDAO;

@WebServlet("/admin/GestioneOrdiniAdminServlet")
public class GestioneOrdiniAdminServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public GestioneOrdiniAdminServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    String idClienteParam = request.getParameter("idCliente");
	    String dataInizioParam = request.getParameter("dataInizio");
	    String dataFineParam = request.getParameter("dataFine");
		
		Long idCliente = null;
		LocalDateTime dataInizio = null;
		LocalDateTime dataFine = null;
		
		if (idClienteParam != null && !idClienteParam.trim().isEmpty()) {
			try {
				idCliente = Long.parseLong(idClienteParam);
			} catch (NumberFormatException e) {
				idCliente = null; 
			}
		}
		
		if (dataInizioParam != null && !dataInizioParam.trim().isEmpty() &&
			    dataFineParam != null && !dataFineParam.trim().isEmpty()) {
				try {
					dataInizio = LocalDate.parse(dataInizioParam).atStartOfDay();
					dataFine = LocalDate.parse(dataFineParam).atTime(LocalTime.MAX); 
				} catch (DateTimeParseException e) {
					dataInizio = null;
					dataFine = null;
				}
			}
		
		OrdineDAO ordineDAO = new OrdineDAO();
		List<Ordine> ordini;
		
		try {
			if (dataInizio != null && dataFine != null) {
				ordini = ordineDAO.doRetrieveByDateInterval(dataInizio, dataFine);
			} else if (idCliente != null) {
				ordini = ordineDAO.doRetrieveByUtente(idCliente);
			} else {
				ordini = ordineDAO.doRetrieveAll();
			}

			request.setAttribute("ordiniAdmin", ordini);
			request.getRequestDispatcher("/admin/visualizzaOrdini.jsp").forward(request, response);
		} catch (SQLException s) {
			s.printStackTrace();
			request.setAttribute("error", "Errore accesso al database: " + s.getMessage());
            request.getRequestDispatcher("/500.jsp").forward(request, response);
            return;
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
