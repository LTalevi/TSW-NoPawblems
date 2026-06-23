<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="model.prodotto.Prodotto" %>
<%@ page import="model.prodottocarrello.ProdottoCarrello" %>
<%@ page import="model.varianteprodotto.VarianteProdotto" %>
<%@ page import="model.utente.Utente" %>
<%@ page import="model.indirizzo.Indirizzo" %>
<%@ page import="model.ordine.Ordine" %>

<%@ page import="java.time.LocalDateTime" %>
<%@ page import="java.time.format.DateTimeFormatter" %>

<% 
	List<Prodotto> prodotti = (List<Prodotto>) request.getAttribute("prodottiAdmin"); 

	HttpSession sessione = request.getSession(false);
	String successo = null;
	String errore = null;
	
	if (sessione != null) {
		successo = (String) sessione.getAttribute("success");
		errore = (String) sessione.getAttribute("error");
		sessione.removeAttribute("success");
		sessione.removeAttribute("error");
	}
	
	String idCategoriaForm = request.getParameter("idCategoria") != null ? request.getParameter("idCategoria") : "";
	String idPadreForm = request.getParameter("idPadre") != null ? request.getParameter("idPadre") : "";
	String prezzoMinForm = request.getParameter("prezzoMin") != null ? request.getParameter("prezzoMin") : "";
	String prezzoMaxForm = request.getParameter("prezzoMax") != null ? request.getParameter("prezzoMax") : "";
	String ricercaForm = request.getParameter("ricerca") != null ? request.getParameter("ricerca") : "";
	String ordinamentoForm = request.getParameter("ordinamento") != null ? request.getParameter("ordinamento") : "";
%>

<!DOCTYPE html>
<html>
	<head>
		<link rel="stylesheet" href="<%= request.getContextPath() %>/stylesheets/main.css" type="text/css">
		<link rel="stylesheet" href="<%= request.getContextPath() %>/stylesheets/StileProdottiAdmin.css" type="text/css">
	
		<meta charset="UTF-8">
		<title>Gestione Prodotti Admin</title>
	</head>
		
	<body>
		<jsp:include page="/fragments/Nav.jsp"/>
		<jsp:include page="/fragments/GuidaAlleTaglie.jsp"/>

		<main class="main-wrapper">
			<div class="titolo">
				<h1><span class="gradiente">Catalogo Prodotti</span></h1>
			</div>

			<div class="filtri-modifica">
				<div class="filtri">
					<form action="<%= request.getContextPath() %>/admin/GestioneProdottiAdminServlet" method="GET">
						<fieldset>
							<legend>Filtra Catalogo</legend>
							<div class="griglia-filtri" style="display: grid; grid-template-columns: repeat(auto-fit, minmax(150px, 1fr)); gap: 15px;">
								<div class="input-filtro">
									<label for="ricerca" class="grassetto">Cerca per Nome</label>
									<input type="text" name="ricerca" id="ricerca" value="<%= ricercaForm %>" placeholder="Es: Collare"/>
								</div>
								<div class="input-filtro">
									<label for="idCategoria" class="grassetto">Id Categoria</label>
									<input type="number" name="idCategoria" id="idCategoria" value="<%= idCategoriaForm %>"/>
								</div>
								<div class="input-filtro">
									<label for="prezzoMin" class="grassetto">Prezzo Min (€)</label>
									<input type="number" step="0.01" name="prezzoMin" id="prezzoMin" value="<%= prezzoMinForm %>"/>
								</div>
								<div class="input-filtro">
									<label for="prezzoMax" class="grassetto">Prezzo Max (€)</label>
									<input type="number" step="0.01" name="prezzoMax" id="prezzoMax" value="<%= prezzoMaxForm %>"/>
								</div>
								<div class="input-filtro">
									<label for="ordinamento" class="grassetto">Ordina per</label>
									<select name="ordinamento" id="ordinamento">
										<option value="" <%= ordinamentoForm.isEmpty() ? "selected" : "" %>>Nessuno</option>
										<option value="PREZZO_ASC" <%= ordinamentoForm.equals("PREZZO_ASC") ? "selected" : "" %>>Prezzo: Crescente</option>
										<option value="PREZZO_DESC" <%= ordinamentoForm.equals("PREZZO_DESC") ? "selected" : "" %>>Prezzo: Decrescente</option>
									</select>
								</div>
							</div>
							
							<div class="bottoni">
								<button type="submit" class="bottone-scheda">Applica Filtri</button>
								<button type="button" onclick="location.href='<%= request.getContextPath() %>/admin/GestioneProdottiAdminServlet'" class="bottone-cancella">Reset</button>
							</div>
						</fieldset>
					</form>
				</div>
				
				<div class="filtri" id="boxFormPrincipale">
					<form action="<%= request.getContextPath() %>/admin/GestioneProdottiAdminServlet" method="POST" id="formProdotto" enctype="multipart/form-data">
					    <input type="hidden" id="azioneForm" name="action" value="inserisci">
					    <input type="hidden" id="idProdottoForm" name="idProdotto" value="">
					
						<fieldset>
							<legend id="titoloForm">Aggiungi Nuovo Prodotto</legend>
							
							<div class="blocco-dati-prodotto">
								<div class="dati-testo">
									<label class="grassetto">Nome Prodotto</label>
									<input type="text" name="nome" id="nomeForm" required>
									
									<br/><br/>
									
									<label class="grassetto">Descrizione</label>
									<textarea name="descrizione" id="descrizioneForm" rows="3" required></textarea>
									
									<br/><br/>
									
									<label class="grassetto">ID Categoria</label>
									<input type="number" name="idCategoria" id="idCategoriaForm" required>
								</div>
								
								<div class="dati-numerici">
									<label class="grassetto">Prezzo (€)</label>
									<input type="number" step="0.01" name="prezzo" id="prezzoForm" required>
									
									<label class="grassetto">Disponibilità</label>
									<input type="number" name="disponibilita" id="disponibilitaForm" required>
									
									<label class="grassetto">Aliquota IVA (%)</label>
									<input type="number" name="iva" id="ivaForm" value="22" required>
								</div>
							</div>
							
							<div id="blocco-inserimento-varianti">
								<div class="gruppo-variante">
									<span class="grassetto titolo-variante">Taglie Disponibili</span>
									<div class="griglia-checkbox">
										<label class="opzione-checkbox"><input type="checkbox" name="taglia" value="XS"> XS</label>
										<label class="opzione-checkbox"><input type="checkbox" name="taglia" value="S"> S</label>
										<label class="opzione-checkbox"><input type="checkbox" name="taglia" value="M"> M</label>
										<label class="opzione-checkbox"><input type="checkbox" name="taglia" value="L"> L</label>
										<label class="opzione-checkbox"><input type="checkbox" name="taglia" value="XL"> XL</label>
									</div>
								</div>
							
								<div class="gruppo-variante">
									<span class="grassetto titolo-variante">Colori Disponibili</span>
									<div class="griglia-checkbox colori">
										<label class="opzione-checkbox"><input type="checkbox" name="colore" value="Nero" data-hex="#000000"> <span class="badge-colore" style="background-color: #000000;"></span></label>
								        <label class="opzione-checkbox"><input type="checkbox" name="colore" value="Bianco" data-hex="#FFFFFF"> <span class="badge-colore" style="background-color: #FFFFFF; border: 1px solid #ccc;"></span></label>
								        <label class="opzione-checkbox"><input type="checkbox" name="colore" value="Rosso" data-hex="#FF0000"> <span class="badge-colore" style="background-color: #FF0000;"></span></label>
								        <label class="opzione-checkbox"><input type="checkbox" name="colore" value="Blu" data-hex="#0000FF"> <span class="badge-colore" style="background-color: #0000FF;"></span></label>
								        <label class="opzione-checkbox"><input type="checkbox" name="colore" value="Verde" data-hex="#008000"> <span class="badge-colore" style="background-color: #008000;"></span></label>
								        <label class="opzione-checkbox"><input type="checkbox" name="colore" value="Grigio" data-hex="#808080"> <span class="badge-colore" style="background-color: #808080;"></span></label>
								        <label class="opzione-checkbox"><input type="checkbox" name="colore" value="Giallo" data-hex="#FFFF00"> <span class="badge-colore" style="background-color: #FFFF00;"></span></label>
									</div>
								</div>
								
								<div id="hidden-hex-container"></div>
							</div>
	
							<div id="blocco-modifica-varianti" style="display: none;"></div>
	
							<div id="blocco-inserimento-immagine">
								<label class="grassetto">Immagine Prodotto</label>
								<input type="file" name="immagine" id="immagineForm" accept="image/*" required>
								
								<label class="grassetto">Testo Alternativo Immagine</label>
								<input type="text" name="alt" id="altForm" placeholder="Descrizione dell'immagine">
							</div>
	
							<div class="bottoni">
								<button type="submit" id="btnSubmit" class="bottone-scheda">Salva e Genera Prodotto</button>
								<button type="button" id="btnAnnulla" class="bottone-cancella" onclick="annullaModifica()" style="display: none;">Annulla Modifica</button>
							</div>
						</fieldset>
					</form>
				</div>
			</div>
			
			<div class="product-grid">
				<% if (prodotti == null || prodotti.isEmpty()) { %>
		  				<p style="text-align:center; width:100%; margin:auto;">Nessun prodotto disponibile al momento.</p>
				<% } else { 
	           		 	for (Prodotto p : prodotti) { 
	            			String urlImmagine = "img/errori/ImmagineMancante.png"; 
	       					String altImmagine = p.getNome();
	       					
	                		if (p.getImmagini() != null && !p.getImmagini().isEmpty()) {
	                    		urlImmagine = p.getImmagini().get(0).getUrl();
	                    		altImmagine = p.getImmagini().get(0).getAlt();
	                		}
	                		
	                		float prezzo = 0.0f;
	                		int disponibilita = 0;
	                		int iva = 22;
	                		
	                		StringBuilder jsonVarianti = new StringBuilder("[");
	                		if (p.getVarianti() != null && !p.getVarianti().isEmpty()) {
	                			prezzo = p.getVarianti().get(0).getPrezzo();
	                			disponibilita = p.getVarianti().get(0).getDisponibilita();
	                			iva = p.getVarianti().get(0).getIva();
	                			
	                			for(int i=0; i < p.getVarianti().size(); i++) {
	                				VarianteProdotto v = p.getVarianti().get(i);
	                				jsonVarianti.append("{")
	                					.append("\"id\":").append(v.getIdVariante()).append(",")
	                					.append("\"taglia\":\"").append(v.getTaglia() != null ? v.getTaglia().replace("\"", "\\\"").replace("'", "\\'") : "").append("\",")
	                					.append("\"colore\":\"").append(v.getColore() != null ? v.getColore().replace("\"", "\\\"").replace("'", "\\'") : "").append("\",")
	                					.append("\"hex\":\"").append(v.getColoreHex() != null ? v.getColoreHex().replace("\"", "\\\"").replace("'", "\\'") : "").append("\"")
	                				.append("}");
	                				if(i < p.getVarianti().size() - 1) jsonVarianti.append(",");
	                			}
	                		}
	                		jsonVarianti.append("]");
	                		
	                		boolean esaurito = !p.isActive() || (p.getVarianti() != null && !p.getVarianti().isEmpty() && p.getVarianti().get(0).getDisponibilita() == 0);
					%>	  
				<div class="prodotto <%=esaurito  ? "esaurito" : ""%>">
					<%
						if(esaurito){
							%>
								<span class="prodotto-non-disponibile">Non Disponibile</span>
							<%
						}
					%>
					<div class="immagine_prodotto">
						<img src="<%= request.getContextPath() %>/<%= urlImmagine %>" alt="<%= altImmagine %>">
					</div>
					
					<div class="info_prodotto">
						<h3 class="nome_prodotto"><%= p.getNome() %></h3>
						<p class="descrizione_prodotto"><%= p.getDescrizione() %></p>
						
						<span class="prezzo_prodotto">
							<%= (p.getVarianti() != null && !p.getVarianti().isEmpty()) ? p.getVarianti().get(0).getPrezzo() + "€" : "N.D." %>
						</span>
					</div>
					
					<div class="bottoni">
						
						<button class="bottone-modifica" onclick='avviaModifica(
							"<%= p.getIdProdotto() %>", 
							"<%= p.getNome().replace("\"", "&quot;") %>", 
							"<%= p.getDescrizione().replace("\"", "&quot;").replace("\n", "\\n") %>", 
							"<%= p.getCategoria() != null ? p.getCategoria().getIdCategoria() : "" %>", 
							"<%= prezzo %>", 
							"<%= disponibilita %>", 
							"<%= iva %>", 
							<%= jsonVarianti.toString() %>
						)'>
							Modifica Articolo
						</button>
						
						<form action="<%= request.getContextPath() %>/admin/GestioneProdottiAdminServlet" method="POST" enctype="multipart/form-data">
							<input type="hidden" name="action" value="cancella">
							<input type="hidden" name="idCancella" value="<%= p.getIdProdotto() %>"> 
							<button type="submit" class="bottone-cancella">
								Rimuovi Articolo
							</button>
						</form>
						
						<button class="bottone-scheda" onclick="location.href='<%= request.getContextPath() %>/DettaglioProdotto?idProdotto=<%=p.getIdProdotto()%>'">
							Scheda Articolo
						</button>
					</div>
				</div>
			<%
				}
        	}
        	%>
		</div>
	</main>
		<jsp:include page="/fragments/Footer.jsp"/>
		<script src="<%=request.getContextPath() %>/scripts/GestioneProdotto.js"></script>
	</body>
</html>