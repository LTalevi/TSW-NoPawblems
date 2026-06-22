<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="model.prodotto.Prodotto" %>
<%@ page import="model.varianteprodotto.VarianteProdotto" %>

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
		<link rel="stylesheet" href="<%= request.getContextPath() %>/stylesheets/StileCatalogo.css" type="text/css">
		<link rel="stylesheet" href="<%= request.getContextPath() %>/stylesheets/StileAreaAdmin.css" type="text/css">
		<meta charset="UTF-8">
		<title>Pannello Amministrazione - Catalogo Prodotti</title>
	</head>

	<body>
		<jsp:include page="/fragments/Nav.jsp"/>
		
		<main class="main-wrapper">
			
			<div class="titolo">
				<h1>Gestione <span class="gradiente">Catalogo Prodotti</span></h1>
			</div>

			<% if (errore != null && !errore.trim().isEmpty()) { %>
				<div class="blocco-errore-successo">
					<p class="testoErrore"><%= errore %></p>
				</div>
			<% } else if (successo != null && !successo.trim().isEmpty()) { %>
				<div class="blocco-errore-successo">
					<p class="testoSuccesso"><%= successo %></p>
				</div>
			<% } %>

			<%-- 1. FILTRI DI RICERCA --%>
			<div class="Filtri" style="margin-bottom: 30px;">
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
						<button type="submit" style="margin-top: 15px;">Applica Filtri</button>
						<a href="<%= request.getContextPath() %>/admin/GestioneProdottiAdminServlet" class="bottone-reset" style="margin-left: 10px; text-decoration: none;">Reset</a>
					</fieldset>
				</form>
			</div>

			<%-- 2. FORM DINAMICO: INSERIMENTO / MODIFICA --%>
			<div class="Filtri" id="boxFormPrincipale" style="margin-bottom: 40px; transition: 0.3s;">
				<form id="formProdotto" action="<%= request.getContextPath() %>/admin/GestioneProdottiAdminServlet" method="POST" enctype="multipart/form-data">
					
					<input type="hidden" name="action" id="azioneForm" value="inserisci">
					<input type="hidden" name="idProdotto" id="idProdottoForm" value="">

					<fieldset>
						<legend id="titoloForm">Aggiungi Nuovo Prodotto Base + Varianti</legend>
						
						<div style="display: grid; grid-template-columns: 1fr 1fr; gap: 15px;">
							<div>
								<label class="grassetto">Nome Prodotto</label>
								<input type="text" name="nome" id="nomeForm" required>
								
								<label class="grassetto">Descrizione</label>
								<textarea name="descrizione" id="descrizioneForm" rows="3" style="width: 100%;" required></textarea>
								
								<label class="grassetto">ID Categoria</label>
								<input type="number" name="idCategoria" id="idCategoriaForm" required>
							</div>
							
							<div>
								<label class="grassetto">Prezzo (€)</label>
								<input type="number" step="0.01" name="prezzo" id="prezzoForm" required>
								
								<label class="grassetto">Disponibilità</label>
								<input type="number" name="disponibilita" id="disponibilitaForm" required>
								
								<label class="grassetto">Aliquota IVA (%)</label>
								<input type="number" name="iva" id="ivaForm" value="22" required>
							</div>
						</div>

						<hr style="margin: 15px 0; border: 0; border-top: 1px solid #ccc;">
						
						<div id="bloccoVariantiInserimento" style="display: grid; grid-template-columns: 1fr 1fr; gap: 15px;">
							<div>
								<label class="grassetto">Taglie Generabili</label>
								<input type="text" name="taglia" id="insTaglia1" placeholder="Es: M" required>
								<input type="text" name="taglia" id="insTaglia2" placeholder="Es: L (Opzionale)">
							</div>
							<div>
								<label class="grassetto">Colori (Nome e Codice Hex)</label>
								<div style="display: flex; gap: 5px; margin-bottom: 5px;">
									<input type="text" name="colore" id="insColore" placeholder="Es: Rosso" required>
									<input type="text" name="coloreHex" id="insHex" placeholder="Es: #FF0000" required>
								</div>
							</div>
						</div>

						<div id="bloccoVariantiModifica" style="display: none; flex-direction: column; gap: 10px;">
							</div>

						<hr style="margin: 15px 0; border: 0; border-top: 1px solid #ccc;">

						<div id="bloccoImmagine">
							<label class="grassetto">Immagine Prodotto (ignorata in modifica dal doPost attuale)</label>
							<input type="file" name="immagine" id="immagineForm" accept="image/*" required>
							
							<label class="grassetto">Testo Alternativo Immagine (Alt)</label>
							<input type="text" name="alt" id="altForm" placeholder="Descrizione dell'immagine">
						</div>

						<div style="margin-top: 15px; display: flex; gap: 10px;">
							<button type="submit" id="btnSubmit" style="background: #2ecc71; color: white;">Salva e Genera Prodotto</button>
							<button type="button" id="btnAnnulla" onclick="annullaModifica()" style="display: none; background: #e74c3c; color: white; padding: 10px 15px; border: none; border-radius: 5px; cursor: pointer;">Annulla Modifica</button>
						</div>
					</fieldset>
				</form>
			</div>

			<%-- 3. GRIGLIA PRODOTTI --%>
			<div class="product-grid">
				<% if (prodotti == null || prodotti.isEmpty()) { %>
		  				<p style="text-align:center; width:100%; margin:auto;">Nessun prodotto corrisponde ai criteri di ricerca cercati.</p>
				<% } else { 
	           		 	for (Prodotto p : prodotti) { 
	            			String urlImmagine = "img/errori/ImmagineMancante.png"; 
	       					String altImmagine = p.getNome();
	                
	                		if (p.getImmagini() != null && !p.getImmagini().isEmpty()) {
	                    		urlImmagine = p.getImmagini().get(0).getUrl();
	                    		altImmagine = p.getImmagini().get(0).getAlt();
	                		}
	                		
	                		float prezzoVisualizzato = 0.0f;
	                		int dispVisualizzata = 0;
	                		int ivaVisualizzata = 22;
	                		
	                		// Creiamo il JSON delle varianti per questo prodotto
	                		StringBuilder jsonVarianti = new StringBuilder("[");
	                		if (p.getVarianti() != null && !p.getVarianti().isEmpty()) {
	                			prezzoVisualizzato = p.getVarianti().get(0).getPrezzo();
	                			dispVisualizzata = p.getVarianti().get(0).getDisponibilita();
	                			ivaVisualizzata = p.getVarianti().get(0).getIva();
	                			
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
					%>	  
					<div class="prodotto" style="cursor: default; position: relative;">
						<div class="immagine_prodotto">
							<img src="<%= request.getContextPath() %>/<%= urlImmagine %>" alt="<%= altImmagine %>">
						</div>
						
						<div class="info_prodotto">
							<span class="grassetto" style="color: #7f8c8d; font-size: 0.85rem;">ID: <%= p.getIdProdotto() %></span>
							<h3 class="nome_prodotto"><%= p.getNome() %></h3>
							<p class="descrizione_prodotto"><%= p.getDescrizione() %></p>
							<span class="prezzo_prodotto"><%= String.format("%.2f", prezzoVisualizzato) %>€</span>
						</div>
						
						<div style="padding: 10px; display: flex; gap: 5px; background: #f9f9f9; border-top: 1px solid #eee;">
							
							<form action="<%= request.getContextPath() %>/admin/GestioneProdottiAdminServlet" method="POST" style="width: 33%;" onsubmit="return confirm('Vuoi davvero cancellare questo prodotto e tutte le sue varianti?');">
								<input type="hidden" name="action" value="cancella">
								<input type="hidden" name="idCancella" value="<%= p.getIdProdotto() %>">
								<button type="submit" style="width: 100%; background: #e74c3c; color: white; padding: 5px; font-size: 0.85rem; border:none; border-radius:3px; cursor:pointer;">Elimina</button>
							</form>
							
							<button type="button" 
									style="width: 33%; background: #3498db; color: white; padding: 5px; font-size: 0.85rem; border:none; border-radius:3px; cursor:pointer;"
									onclick='avviaModifica(
										"<%= p.getIdProdotto() %>", 
										"<%= p.getNome().replace("\"", "&quot;") %>", 
										"<%= p.getDescrizione().replace("\"", "&quot;").replace("\n", "\\n") %>", 
										"<%= p.getCategoria() != null ? p.getCategoria().getIdCategoria() : "" %>", 
										"<%= prezzoVisualizzato %>", 
										"<%= dispVisualizzata %>", 
										"<%= ivaVisualizzata %>", 
										<%= jsonVarianti.toString() %>
									)'>
								Modifica
							</button>
							
							<a class="bottone-acquista" href="<%= request.getContextPath() %>/DettaglioProdotto?idProdotto=<%= p.getIdProdotto() %>" style="width: 33%; padding: 5px; font-size: 0.85rem; text-align: center; margin: 0; box-sizing: border-box; border-radius:3px;">
								Scheda
							</a>
						</div>
					</div>
					<%
						}
        			}
        			%>
			</div>
		</main>

		<jsp:include page="/fragments/Footer.jsp"/>
		
		<script>
			function avviaModifica(id, nome, desc, idCat, prezzo, disp, iva, varianti) {
				// 1. Scroll fluido verso il form
				document.getElementById('boxFormPrincipale').scrollIntoView({ behavior: 'smooth', block: 'start' });
				
				// 2. Cambio lo stile e i testi del form per renderlo "Modifica"
				document.getElementById("boxFormPrincipale").style.border = "2px solid #3498db";
				document.getElementById("titoloForm").innerText = "Modifica Prodotto #" + id;
				document.getElementById("btnSubmit").innerText = "Salva Modifiche";
				document.getElementById("btnSubmit").style.background = "#3498db";
				document.getElementById("btnAnnulla").style.display = "inline-block";

				// 3. Imposto l'azione e i campi nascosti per la Servlet
				document.getElementById("azioneForm").value = "modifica";
				document.getElementById("idProdottoForm").value = id;
				
				// 4. Popolo i campi anagrafici
				document.getElementById("nomeForm").value = nome;
				document.getElementById("descrizioneForm").value = desc;
				document.getElementById("idCategoriaForm").value = idCat;
				document.getElementById("prezzoForm").value = prezzo;
				document.getElementById("disponibilitaForm").value = disp;
				document.getElementById("ivaForm").value = iva;
				
				// 5. Disabilito l'obbligo dell'immagine (non gestita dal doPost in modifica)
				document.getElementById("immagineForm").removeAttribute("required");

				// 6. GESTIONE VARIANTI
				// Disattivo gli input dell'inserimento così non vengono inviati col POST
				document.getElementById("insTaglia1").disabled = true;
				document.getElementById("insTaglia2").disabled = true;
				document.getElementById("insColore").disabled = true;
				document.getElementById("insHex").disabled = true;
				document.getElementById("bloccoVariantiInserimento").style.display = "none";
				
				// Costruisco gli input per le varianti esistenti
				let bloccoModifica = document.getElementById("bloccoVariantiModifica");
				bloccoModifica.style.display = "flex";
				bloccoModifica.innerHTML = '<label class="grassetto" style="color: #3498db;">Modifica Varianti Esistenti</label>';
				
				varianti.forEach(function(v) {
					bloccoModifica.innerHTML += `
						<div style="display: flex; gap: 10px; background: #f4f4f4; padding: 10px; border-radius: 5px;">
							<input type="hidden" name="idVariante" value="` + v.id + `">
							<input type="text" name="taglia" value="` + v.taglia + `" placeholder="Taglia" style="flex:1" required>
							<input type="text" name="colore" value="` + v.colore + `" placeholder="Colore" style="flex:1" required>
							<input type="text" name="coloreHex" value="` + v.hex + `" placeholder="Hex" style="flex:1" required>
						</div>
					`;
				});
			}

			function annullaModifica() {
				// 1. Ripristino l'azione e pulisco l'id
				document.getElementById("azioneForm").value = "inserisci";
				document.getElementById("idProdottoForm").value = "";
				
				// 2. Svuoto tutto il form
				document.getElementById("formProdotto").reset();
				
				// 3. Ripristino testi, stili e required dell'immagine
				document.getElementById("boxFormPrincipale").style.border = "none";
				document.getElementById("titoloForm").innerText = "Aggiungi Nuovo Prodotto Base + Varianti";
				document.getElementById("btnSubmit").innerText = "Salva e Genera Prodotto";
				document.getElementById("btnSubmit").style.background = "#2ecc71";
				document.getElementById("btnAnnulla").style.display = "none";
				document.getElementById("immagineForm").setAttribute("required", "required");

				// 4. Ripristino le varianti
				document.getElementById("insTaglia1").disabled = false;
				document.getElementById("insTaglia2").disabled = false;
				document.getElementById("insColore").disabled = false;
				document.getElementById("insHex").disabled = false;
				document.getElementById("bloccoVariantiInserimento").style.display = "grid";
				
				let bloccoModifica = document.getElementById("bloccoVariantiModifica");
				bloccoModifica.style.display = "none";
				bloccoModifica.innerHTML = "";
			}
		</script>
	</body>
</html>