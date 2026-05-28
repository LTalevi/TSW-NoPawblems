package control.admin;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.categoria.Categoria;
import model.prodotto.Prodotto;
import model.prodotto.ProdottoDAO;
import model.varianteprodotto.VarianteProdotto;
import model.varianteprodotto.VarianteProdottoDAO;

@WebServlet("/admin/GestioneProdottiAdminServlet")
public class GestioneProdottiAdminServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public GestioneProdottiAdminServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
	    if (action == null || action.trim().isEmpty()) {
	        action = "visualizza";
	    }
	    
	    ProdottoDAO prodottoDAO = new ProdottoDAO();
	    
	    try {
	        switch (action) {
	            case "inserisciForm":
	                request.getRequestDispatcher("/admin/formProdotto.jsp").forward(request, response);
	                break;
	                
	            case "modificaForm":
	                long idModifica = Long.parseLong(request.getParameter("id"));
	                Prodotto prodottoEsistente = prodottoDAO.doRetrieveByKey(idModifica);
	                request.setAttribute("prodotto", prodottoEsistente);
	                request.getRequestDispatcher("/admin/formProdotto.jsp").forward(request, response);
	                break;
	                
	            case "visualizza":
	            default:
	                String idCategoriaParam = request.getParameter("idCategoria");
	                String idPadreParam = request.getParameter("idPadre");
	                String prezzoMinParam = request.getParameter("prezzoMin");
	                String prezzoMaxParam = request.getParameter("prezzoMax");
	                String ricerca = request.getParameter("ricerca");
	                String ordinamento = request.getParameter("ordinamento"); 

	                Long idCategoria = null;
	        		Long idPadre = null;
	        		Float prezzoMin = null;
	        		Float prezzoMax = null;
	        		
	        		if (idCategoriaParam != null && !idCategoriaParam.trim().isEmpty()) {
	        			try {
	        				idCategoria = Long.parseLong(idCategoriaParam);
	        			} catch (NumberFormatException e) {
	        				idCategoria = null; 
	        			}
	        		}
	        		
	        		if (idPadreParam != null && !idPadreParam.trim().isEmpty()) {
	        			try {
	        				idPadre = Long.parseLong(idPadreParam);
	        			} catch (NumberFormatException e) {
	        				idPadre = null; 
	        			}
	        		}
	        		
	        		if (prezzoMinParam != null && !prezzoMinParam.trim().isEmpty()) {
	        			try {
	        				prezzoMin = Float.parseFloat(prezzoMinParam);
	        			} catch (NumberFormatException e) {
	        				prezzoMin = null;
	        			}
	        		}
	        		
	        		if (prezzoMaxParam != null && !prezzoMaxParam.trim().isEmpty()) {
	        			try {
	        				prezzoMax = Float.parseFloat(prezzoMaxParam);
	        			} catch (NumberFormatException e) {
	        				prezzoMax = null;
	        			}
	        		}
	                List<Prodotto> prodotti = prodottoDAO.doRetrieveByFilter(idCategoria, idPadre, prezzoMin, prezzoMax, ricerca, ordinamento);
	                
	                request.setAttribute("prodottiAdmin", prodotti);
	                request.setAttribute("ricerca", ricerca); 
	                
	                request.getRequestDispatcher("catalogoAdmin.jsp").forward(request, response);
	                break;
	        }
	    } catch (SQLException | NumberFormatException e) {
	        e.printStackTrace();
	        request.setAttribute("error", "Errore nel caricamento dei dati: " + e.getMessage());
	        request.getRequestDispatcher("/500.jsp").forward(request, response);
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
        
        try {
            switch (action) {
            case "inserisci":
                Prodotto nuovoProdotto = new Prodotto();
                nuovoProdotto.setNome(request.getParameter("nome"));
                nuovoProdotto.setDescrizione(request.getParameter("descrizione"));
                nuovoProdotto.setActive(true); 

                Categoria categoria = new Categoria();
                categoria.setIdCategoria(Long.parseLong(request.getParameter("idCategoria"))); 
                nuovoProdotto.setCategoria(categoria);

                prodottoDAO.doSave(nuovoProdotto); 

                VarianteProdotto nuovaVariante = new VarianteProdotto();
                
                nuovaVariante.setProdottoPadre(nuovoProdotto.getIdProdotto()); 
                
                nuovaVariante.setPrezzo(Float.parseFloat(request.getParameter("prezzo")));
                nuovaVariante.setDisponibilita(Integer.parseInt(request.getParameter("disponibilita")));
                nuovaVariante.setIva(Integer.parseInt(request.getParameter("iva")));
                nuovaVariante.setTaglia(request.getParameter("taglia"));
                nuovaVariante.setColore(request.getParameter("colore"));
                nuovaVariante.setColoreHex(request.getParameter("coloreHex"));

                varianteProdottoDAO.doSave(nuovaVariante);

                session.setAttribute("success", "Prodotto creato con successo! Ora carica le immagini.");
                
                response.sendRedirect(request.getContextPath() + "/admin/GestioneImmaginiServlet?idProdotto=" + nuovoProdotto.getIdProdotto());
                break;

                case "modifica":
                	long idProdotto = Long.parseLong(request.getParameter("idProdotto"));
	            	
	            	Prodotto daModificare = new Prodotto();
	            	daModificare.setIdProdotto(idProdotto);
	            	daModificare.setNome(request.getParameter("nome"));
	            	daModificare.setDescrizione(request.getParameter("descrizione"));
	            	daModificare.setActive(true);
	            	
	            	Categoria catModificata = new Categoria();
	            	catModificata.setIdCategoria(Long.parseLong(request.getParameter("idCategoria")));
	            	daModificare.setCategoria(catModificata);
	            	
	            	prodottoDAO.doUpdate(daModificare);
	            	
	                session.setAttribute("success", "Informazioni del prodotto aggiornate con successo!");
	                
	                response.sendRedirect(request.getContextPath() + "/admin/GestioneProdottiAdminServlet?action=visualizza");
	                break;

                case "cancella":
                	long idCancella = Long.parseLong(request.getParameter("id"));
                    
                    Prodotto daCancellare = prodottoDAO.doRetrieveByKey(idCancella);
                    
                    prodottoDAO.doDelete(daCancellare.getIdProdotto());
                    
                    request.getSession().setAttribute("success", "Prodotto eliminato correttamente dal catalogo.");
                    
                    response.sendRedirect(request.getContextPath() + "/admin/GestioneProdottiAdminServlet?action=visualizza");
                    break;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "Errore nel database durante l'operazione: " + e.getMessage());
            request.getRequestDispatcher("/500.jsp").forward(request, response);
        }
    }
}
