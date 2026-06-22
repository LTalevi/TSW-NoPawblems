package control.admin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import model.categoria.Categoria;
import model.prodotto.Prodotto;
import model.prodotto.ProdottoDAO;
import model.varianteprodotto.VarianteProdotto;
import model.varianteprodotto.VarianteProdottoDAO;
import model.immagine.Immagine;
import model.immagine.ImmagineDAO;

@WebServlet("/admin/GestioneProdottiAdminServlet")
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024 * 2,
    maxFileSize = 1024 * 1024 * 10,
    maxRequestSize = 1024 * 1024 * 50
)
public class GestioneProdottiAdminServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final String UPLOAD_DIR = "img" + File.separator + "prodotti";

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	
    	ProdottoDAO prodottoDAO = new ProdottoDAO();

        try {
            Long idCategoria = null;
            Long idPadre = null;
            Float prezzoMin = null;
            Float prezzoMax = null;

            if (request.getParameter("idCategoria") != null && !request.getParameter("idCategoria").trim().isEmpty()) {
                try { idCategoria = Long.parseLong(request.getParameter("idCategoria").trim()); } catch (NumberFormatException e) {}
            }
            if (request.getParameter("idPadre") != null && !request.getParameter("idPadre").trim().isEmpty()) {
                try { idPadre = Long.parseLong(request.getParameter("idPadre").trim()); } catch (NumberFormatException e) {}
            }
            if (request.getParameter("prezzoMin") != null && !request.getParameter("prezzoMin").trim().isEmpty()) {
                try { prezzoMin = Float.parseFloat(request.getParameter("prezzoMin").trim()); } catch (NumberFormatException e) {}
            }
            if (request.getParameter("prezzoMax") != null && !request.getParameter("prezzoMax").trim().isEmpty()) {
                try { prezzoMax = Float.parseFloat(request.getParameter("prezzoMax").trim()); } catch (NumberFormatException e) {}
            }
            
            String ricerca = request.getParameter("ricerca");
            String ordinamento = request.getParameter("ordinamento");

            List<Prodotto> prodotti = prodottoDAO.doRetrieveByFilter(idCategoria, idPadre, prezzoMin, prezzoMax, ricerca, ordinamento, null);
            
            request.setAttribute("prodottiAdmin", prodotti);
            request.setAttribute("ricerca", ricerca != null ? ricerca.trim() : ""); 
            request.getRequestDispatcher("/GestioneProdottiAdmin.jsp").forward(request, response);
            
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "Errore nel caricamento dei dati: " + e.getMessage());
            request.getRequestDispatcher("/Errore500.jsp").forward(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String action = request.getParameter("action");
        
        if (action == null || action.trim().isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/admin/GestioneProdottiAdminServlet");
            return;
        }

        ProdottoDAO prodottoDAO = new ProdottoDAO();
        VarianteProdottoDAO varianteProdottoDAO = new VarianteProdottoDAO();
        ImmagineDAO immagineDAO = new ImmagineDAO();

        try {
            switch (action.trim()) {
                case "inserisci": {
                    String nome = request.getParameter("nome");
                    String descrizione = request.getParameter("descrizione");
                    String idCategoriaStr = request.getParameter("idCategoria");

                    Prodotto nuovoProdotto = new Prodotto();
                    nuovoProdotto.setNome(nome != null ? nome : "");
                    nuovoProdotto.setDescrizione(descrizione != null ? descrizione : "");
                    nuovoProdotto.setActive(true); 

                    long idCat = 0L;
                    if (idCategoriaStr != null && !idCategoriaStr.trim().isEmpty()) {
                        try { idCat = Long.parseLong(idCategoriaStr.trim()); } catch (NumberFormatException e) {}
                    }
                    Categoria categoria = new Categoria();
                    categoria.setIdCategoria(idCat); 
                    nuovoProdotto.setCategoria(categoria);

                    prodottoDAO.doSave(nuovoProdotto); 

                    String prezzoStr = request.getParameter("prezzo");
                    String dispStr = request.getParameter("disponibilita");
                    String ivaStr = request.getParameter("iva");

                    float prezzoComune = 0.0f;
                    int disponibilitaComune = 0;
                    int ivaComune = 22;

                    if (prezzoStr != null && !prezzoStr.trim().isEmpty()) {
                        try { prezzoComune = Float.parseFloat(prezzoStr.trim()); } catch (NumberFormatException e) {}
                    }
                    if (dispStr != null && !dispStr.trim().isEmpty()) {
                        try { disponibilitaComune = Integer.parseInt(dispStr.trim()); } catch (NumberFormatException e) {}
                    }
                    if (ivaStr != null && !ivaStr.trim().isEmpty()) {
                        try { ivaComune = Integer.parseInt(ivaStr.trim()); } catch (NumberFormatException e) {}
                    }

                    String[] taglie = request.getParameterValues("taglia");
                    String[] colori = request.getParameterValues("colore");
                    String[] coloriHex = request.getParameterValues("coloreHex");

                    if (taglie != null && colori != null) {
                        for (int i = 0; i < taglie.length; i++) {
                            String taglia = taglie[i] != null ? taglie[i].trim() : "";
                            
                            for (int j = 0; j < colori.length; j++) {
                                String colore = colori[j] != null ? colori[j].trim() : "";

                                VarianteProdotto nuovaVariante = new VarianteProdotto();
                                nuovaVariante.setProdottoPadre(nuovoProdotto.getIdProdotto()); 
                                nuovaVariante.setPrezzo(prezzoComune);
                                nuovaVariante.setDisponibilita(disponibilitaComune);
                                nuovaVariante.setIva(ivaComune);
                                nuovaVariante.setTaglia(taglia);
                                nuovaVariante.setColore(colore);
                                
                                String hex = coloriHex[j].trim();
                                nuovaVariante.setColoreHex(hex);

                                varianteProdottoDAO.doSave(nuovaVariante);
                            }
                        }
                    }

                    String altText = request.getParameter("alt");
                    String alt = (altText != null) ? altText.trim() : "";
                    
                    String appPath = request.getServletContext().getRealPath("");
                    File uploadDir = new File(appPath + File.separator + UPLOAD_DIR);
                    if (!uploadDir.exists()) uploadDir.mkdirs();

                    for (Part part : request.getParts()) {
                        if (part.getName().equals("immagine") && part.getSize() > 0) {
                            String originalName = Paths.get(part.getSubmittedFileName()).getFileName().toString();
                            String ext = originalName.lastIndexOf('.') > 0 ? originalName.substring(originalName.lastIndexOf('.')) : "";
                            String uniqueFileName = UUID.randomUUID().toString() + ext;
                            
                            File fileTarget = new File(uploadDir, uniqueFileName);
                            try (InputStream input = part.getInputStream()) {
                                Files.copy(input, fileTarget.toPath(), StandardCopyOption.REPLACE_EXISTING);
                            }

                            Immagine nuovaImmagine = new Immagine();
                            nuovaImmagine.setProdotto(nuovoProdotto.getIdProdotto());
                            nuovaImmagine.setUrl("img/prodotti/" + uniqueFileName);  
                            nuovaImmagine.setAlt(alt);
                            immagineDAO.doSave(nuovaImmagine);
                        }
                    }

                    session.setAttribute("success", "Prodotto e varianti generati con successo!");
                    response.sendRedirect(request.getContextPath() + "/admin/GestioneProdottiAdminServlet");
                    break;
                }

                case "modifica": {
                    String idProdottoStr = request.getParameter("idProdotto");
                    long idProdotto = 0L;
                    if (idProdottoStr != null && !idProdottoStr.trim().isEmpty()) {
                        try { idProdotto = Long.parseLong(idProdottoStr.trim()); } catch (NumberFormatException e) {}
                    }
                    
                    String nome = request.getParameter("nome");
                    String descrizione = request.getParameter("descrizione");
                    String idCategoriaStr = request.getParameter("idCategoria");

                    Prodotto daModificare = new Prodotto();
                    daModificare.setIdProdotto(idProdotto);
                    daModificare.setNome(nome != null ? nome : "");
                    daModificare.setDescrizione(descrizione != null ? descrizione : "");
                    daModificare.setActive(true);
                    
                    long idCatMod = 0L;
                    if (idCategoriaStr != null && !idCategoriaStr.trim().isEmpty()) {
                        try { idCatMod = Long.parseLong(idCategoriaStr.trim()); } catch (NumberFormatException e) {}
                    }
                    Categoria catModificata = new Categoria();
                    catModificata.setIdCategoria(idCatMod);
                    daModificare.setCategoria(catModificata);
                    
                    prodottoDAO.doUpdate(daModificare);

                    String prezzoStr = request.getParameter("prezzo");
                    String dispStr = request.getParameter("disponibilita");
                    String ivaStr = request.getParameter("iva");

                    float prezzo = 0.0f;
                    int disponibilita = 0;
                    int iva = 22;

                    if (prezzoStr != null && !prezzoStr.trim().isEmpty()) {
                        try { prezzo = Float.parseFloat(prezzoStr.trim()); } catch (NumberFormatException e) {}
                    }
                    if (dispStr != null && !dispStr.trim().isEmpty()) {
                        try { disponibilita = Integer.parseInt(dispStr.trim()); } catch (NumberFormatException e) {}
                    }
                    if (ivaStr != null && !ivaStr.trim().isEmpty()) {
                        try { iva = Integer.parseInt(ivaStr.trim()); } catch (NumberFormatException e) {}
                    }

                    String[] idVarianti = request.getParameterValues("idVariante"); 
                    String[] taglie = request.getParameterValues("taglia");
                    String[] colori = request.getParameterValues("colore");
                    String[] coloriHex = request.getParameterValues("coloreHex");

                    int indexId = 0; 
                    if (taglie != null && colori != null) {
                        for (int i = 0; i < taglie.length; i++) {
                            String taglia = (taglie[i] != null) ? taglie[i].trim() : "";
                            for (int j = 0; j < colori.length; j++) {
                                String colore = (colori[j] != null) ? colori[j].trim() : "";

                                VarianteProdotto var = new VarianteProdotto();
                                var.setProdottoPadre(idProdotto);
                                var.setPrezzo(prezzo);
                                var.setDisponibilita(disponibilita);
                                var.setIva(iva);
                                var.setTaglia(taglia);
                                var.setColore(colore);
                                
                                String hex = coloriHex[j].trim();
                                var.setColoreHex(hex);

                                if (idVarianti != null && indexId < idVarianti.length && idVarianti[indexId] != null && !idVarianti[indexId].trim().isEmpty()) {
                                    try {
                                        var.setIdVariante(Long.parseLong(idVarianti[indexId].trim()));
                                        varianteProdottoDAO.doUpdate(var); 
                                    } catch (NumberFormatException e) {
                                        varianteProdottoDAO.doSave(var);
                                    }
                                } else {
                                    varianteProdottoDAO.doSave(var);
                                }
                                indexId++;
                            }
                        }
                    }

                    session.setAttribute("success", "Catalogo modificato con successo!");
                    response.sendRedirect(request.getContextPath() + "/admin/GestioneProdottiAdminServlet");
                    break;
                }

                case "cancella": {
                    String idStr = request.getParameter("idCancella");
                    if (idStr != null && !idStr.trim().isEmpty()) {
                        try {
                            long idCancella = Long.parseLong(idStr.trim());
                            prodottoDAO.doDelete(idCancella);
                        } catch (NumberFormatException e) {}
                    }
                    
                    session.setAttribute("success", "Prodotto e varianti rimossi con successo.");
                    response.sendRedirect(request.getContextPath() + "/admin/GestioneProdottiAdminServlet");
                    break;
                }
                
                default:
                    response.sendRedirect(request.getContextPath() + "/admin/GestioneProdottiAdminServlet");
                    break;
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "Errore nel database durante l'operazione: " + e.getMessage());
            request.getRequestDispatcher("/Errore500.jsp").forward(request, response);
        }
    }
}