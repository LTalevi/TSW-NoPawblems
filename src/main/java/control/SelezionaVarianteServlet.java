package control;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonObject;

import model.varianteprodotto.VarianteProdotto;
import model.varianteprodotto.VarianteProdottoDAO;

@WebServlet("/SelezionaVariante")
public class SelezionaVarianteServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");

        String idProdottoParam = request.getParameter("idProdotto");
        String taglia = request.getParameter("taglia");
        String colore = request.getParameter("colore");
        
        if (idProdottoParam == null || idProdottoParam.isEmpty()) {
            response.getWriter().print("{}");
            return;
        }

        try {
            long idProdotto = Long.parseLong(idProdottoParam);
            VarianteProdottoDAO varianteProdottoDAO = new VarianteProdottoDAO();
            List<VarianteProdotto> varianti = varianteProdottoDAO.doRetrieveByProdotto(idProdotto);

            if (varianti == null || varianti.isEmpty()) {
                response.getWriter().print("{}");
                return;
            }

            String tagliaTarget = (taglia != null) ? taglia.trim() : "";
            String coloreTarget = (colore != null) ? colore.trim() : "";

            for (VarianteProdotto v : varianti) {
                String vTaglia = (v.getTaglia() != null) ? v.getTaglia().trim() : "";
                String vColore = (v.getColore() != null) ? v.getColore().trim() : "";

                boolean matchTaglia = tagliaTarget.equalsIgnoreCase(vTaglia);
                boolean matchColore = coloreTarget.equalsIgnoreCase(vColore);

                if (matchTaglia && matchColore) {
                	JsonObject json = new JsonObject();

                	json.addProperty("id", v.getIdVariante());
                	json.addProperty("prezzo", v.getPrezzo());
                	json.addProperty("disponibilita", v.getDisponibilita());

                	response.getWriter().print(json.toString());
                    return;
                }
            }

            response.getWriter().print("{}");

        } catch (Exception e) {
            e.printStackTrace(); 
            JsonObject jsonErrore = new JsonObject();
            jsonErrore.addProperty("error", "Errore nel recupero della variante: " + e.getMessage());

            response.getWriter().print(jsonErrore.toString());
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}