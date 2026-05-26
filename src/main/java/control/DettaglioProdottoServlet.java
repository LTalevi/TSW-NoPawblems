package control;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.prodotto.Prodotto;
import model.prodotto.ProdottoDAO;

@WebServlet("/DettaglioProdotto")
public class DettaglioProdottoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public DettaglioProdottoServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String idProdottoParam = request.getParameter("idProdotto");
		Long idProdotto;
		
		if (idProdottoParam != null && !idProdottoParam.trim().isEmpty()) {
			try {
				idProdotto = Long.parseLong(idProdottoParam);
			} catch (NumberFormatException e) {
				request.getRequestDispatcher("400.jsp").forward(request, response);
				return;
			}
		} else {
			request.getRequestDispatcher("400.jsp").forward(request, response);
			return;
		}
		
		ProdottoDAO prodottoDAO = new ProdottoDAO();
		Prodotto prodotto = null;
		
		try {
			prodotto = prodottoDAO.doRetrieveByKey(idProdotto);
		} catch (SQLException s){
			s.printStackTrace();
			request.setAttribute("error", "Errore nella ricerca del prodotto: " + s.getMessage());
            request.getRequestDispatcher("/500.jsp").forward(request, response);
            return;
		}
		
		request.setAttribute("prodotto", prodotto);
		request.setAttribute("idProdotto", idProdotto);
		
		request.getRequestDispatcher("dettaglioProdotto.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
