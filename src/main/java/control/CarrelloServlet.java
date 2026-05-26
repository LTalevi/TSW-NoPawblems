package control;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
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
import model.varianteprodotto.VarianteProdotto;

@WebServlet("/CarrelloServlet")
public class CarrelloServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public CarrelloServlet() {
        super();
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		Utente utente = (Utente)session.getAttribute("utente");
		List<ProdottoCarrello> carrello = null;
		
		if (utente != null) {
			try{
				ProdottoCarrelloDAO prodottoCarrelloDAO = new ProdottoCarrelloDAO();
				carrello = prodottoCarrelloDAO.doRetrieveByUtente(utente.getIdUtente());
				
			}catch(SQLException s) {
				s.printStackTrace();
				request.setAttribute("error", "Errore nel caricamento del carrello: " + s.getMessage());
	            request.getRequestDispatcher("/500.jsp").forward(request, response);
	            return;
			}		
		} else {
			carrello = (List<ProdottoCarrello>) session.getAttribute("carrello");
			
			if (carrello == null) {
	            carrello = new ArrayList<ProdottoCarrello>();
	            session.setAttribute("carrello", carrello);
	        }
		}
		
		int numeroPezziCarrello = 0;
	    for (ProdottoCarrello item : carrello) {
	        numeroPezziCarrello += item.getQuantita();
	    }
		
	    request.setAttribute("carrello", carrello);
		session.setAttribute("numeroPezziCarrello", numeroPezziCarrello);
		
		request.getRequestDispatcher("/carrello.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		Utente utente = (Utente)session.getAttribute("utente");
		String action = request.getParameter("azione");
		
		if (action == null || action.trim().isEmpty()) {
	        response.sendRedirect(request.getContextPath() + "/CarrelloServlet");
	        return;
	    }
		
		ProdottoCarrelloDAO prodottoCarrelloDAO = new ProdottoCarrelloDAO();
		Long idVariante = Long.parseLong(request.getParameter("idVariante"));
		try {
	        switch (action) {
	            case "aggiungi":
                    int quantita = Integer.parseInt(request.getParameter("quantita"));
                	
                    VarianteProdotto var = new VarianteProdotto();
                    var.setIdVariante(idVariante);
                    
                    ProdottoCarrello prodottoCarrello = new ProdottoCarrello();
                    prodottoCarrello.setVariante(var);
                    prodottoCarrello.setQuantita(quantita);
                    
	                if (utente != null) {
	                	prodottoCarrello.setUtente(utente.getIdUtente());
	                	prodottoCarrelloDAO.doSave(prodottoCarrello);	
	                } else {
	                	List<ProdottoCarrello> carrello = (List<ProdottoCarrello>) session.getAttribute("carrello");
	                	if (carrello == null) {
	                        carrello = new ArrayList<>();
	                    }
	                	
	                	boolean trovato = false;
	                    for (ProdottoCarrello item : carrello) {
	                        if (item.getVariante().getIdVariante() == idVariante) {
	                            item.setQuantita(item.getQuantita() + quantita);
	                            trovato = true;
	                            break;
	                        }
	                    }
	                    
	                    if (!trovato) {
	                        carrello.add(prodottoCarrello);
	                    }
	                    
	                    session.setAttribute("carrello", carrello);
	                }
	                break;
	                
	            case "rimuovi":
	            	if (utente != null) {
	            		prodottoCarrelloDAO.doDelete(utente.getIdUtente(), idVariante);
	                } else {
	                	List<ProdottoCarrello> carrello = (List<ProdottoCarrello>) session.getAttribute("carrello");
	                	if (carrello == null) {
	                        carrello = new ArrayList<>();
	                    }
	                	
	                	
	                	ProdottoCarrello daRimuovere = null;
	                	for (ProdottoCarrello item : carrello) {
	                		if(item.getVariante().getIdVariante() == idVariante) {
	                			daRimuovere = item;
	                			break;
	                		}
	                	}
	                	carrello.remove(daRimuovere);
	                	
	                	session.setAttribute("carrello", carrello);
	                }
	                break;
	                
	            case "modifica":
	            	int nuovaQuantita = Integer.parseInt(request.getParameter("nuovaQuantita"));
	            	if (utente != null) {
	                	prodottoCarrelloDAO.doUpdate(utente.getIdUtente(), idVariante, nuovaQuantita);
	                } else {
	                	List<ProdottoCarrello> carrello = (List<ProdottoCarrello>) session.getAttribute("carrello");
	                	if (carrello == null) {
	                        carrello = new ArrayList<>();
	                    }

	                	for (ProdottoCarrello item : carrello) {
	                		if(item.getVariante().getIdVariante() == idVariante) {
	                			item.setQuantita(nuovaQuantita);
	                			break;
	                		}
	                	}
	                	
	                	session.setAttribute("carrello", carrello);
	                }
	                break;
	                
	            default:
	                break;
	        }
	    } catch (SQLException s) {
	    	s.printStackTrace();
			request.setAttribute("error", "Errore nell'aggiornamento dal carrello: " + s.getMessage());
            request.getRequestDispatcher("/500.jsp").forward(request, response);
            return;
	    }
		
		response.sendRedirect(request.getContextPath() + "/CarrelloServlet");
	}

}
