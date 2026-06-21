package control;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import model.prodotto.Prodotto;
import model.prodotto.ProdottoDAO;

@WebServlet("/SuggerimentiRicerca")
public class SuggerimentiRicerca extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public SuggerimentiRicerca() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json");

		String query = request.getParameter("query");
        JsonArray jsonArray = new JsonArray();

        if (query == null || query.trim().length() < 2) {
            response.getWriter().print(jsonArray.toString());
            return;
        }

        try {
            ProdottoDAO prodottoDAO = new ProdottoDAO();
            List<Prodotto> prodotti = prodottoDAO.doRetrieveByFilter(null, null, null, null, query.trim(), null, true);
            
            int count = 0;
            for (Prodotto p : prodotti) {
                if (count >= 5) break; 
                
                JsonObject jsonProd = new JsonObject();
                jsonProd.addProperty("id", p.getIdProdotto());
                jsonProd.addProperty("nome", p.getNome());
                
                if (!p.getImmagini().isEmpty()) {
                    jsonProd.addProperty("immagine", p.getImmagini().get(0).getUrl());
                } else {
                    jsonProd.addProperty("immagine", "errori/ImmagineMancante.jpg");
                }
                
                jsonArray.add(jsonProd);
                count++;
            }

            response.getWriter().print(jsonArray.toString());
        } catch (Exception e) {
            e.printStackTrace(); 

            JsonObject jsonErrore = new JsonObject();
            jsonErrore.addProperty("error", "Errore: " + e.getMessage());

            response.getWriter().print(jsonErrore.toString());
        }
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
