<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="model.prodotto.Prodotto" %>
<%@ page import="model.prodottocarrello.ProdottoCarrello" %>
<%@ page import="model.varianteprodotto.VarianteProdotto" %>

<%
	List<ProdottoCarrello> carrello = (List<ProdottoCarrello>) request.getAttribute("carrello"); 
	//List<Prodotto> prodottiDettaglio = (List<Prodotto>) request.getAttribute("prodottiDettaglio"); 
%>

<!DOCTYPE html>

<html>
	<head>
		<link rel="stylesheet" href="<%=request.getContextPath() %>/stylesheets/main.css" type="text/css">
		<link rel="stylesheet" href="<%=request.getContextPath() %>/stylesheets/StileCheckout.css" type="text/css">
		<meta charset="UTF-8">
		<title>Checkout</title>
	</head>
	
	<body>
		<jsp:include page="fragments/Nav.jsp"/>
		
		<main class="wrapper">
			<div class="dati">
				<div class="titolo">
					<h2>Inserire i Dati di Spedizione</h2>
				</div>
				
				<div class="dati-spedizione">
					<fieldset>
						<div class="campi-dati">
							<form action="<%= request.getContextPath() %>/user/CheckoutServlet" method="POST">
								<div class="input-errore-container">
									<div class="input" id="input_via">
										<label for="via">Via:</label>
										<input type="text" name="via" id="via" placeholder="Via Roma 1" oninput="valida_via()" required/>
									</div>
										<span id="errore_via" class="errore"></span>
								</div>
								
								<div class="input-errore-container">
									<div class="input" id="input_citta">
										<label for="citta">Città:</label>
										<input type="text" name="citta" id="citta" placeholder="Roma" oninput="valida_citta()" required/>
									</div>
										<span id="errore_citta" class="errore"></span>
								</div>
								
								<div class="input-errore-container">
									<div class="input" id="input_cap">
										<label for="cap">CAP:</label>
										<input type="text" name="cap" id="cap" placeholder="84012" oninput="valida_cap()" required/>
									</div>
										<span id="errore_cap" class="errore"></span>
								</div>
								
								<div class="input-errore-container">
									<div class="input" id="input_provincia">
										<label for="provincia">Provincia:</label>
										<input type="text" name="provincia" id="provincia" placeholder="Roma" oninput="valida_provincia()" required/>
									</div>
										<span id="errore_provincia" class="errore"></span>
								</div>
								
								<div class="input-errore-container">
									<div class="input" id="input_nazione">
										<label for="nazione">Nazione:</label>
										<input type="text" name="nazione" id="nazione" placeholder="Italia" oninput="valida_nazione()" required/>
									</div>
										<span id="errore_nazione" class="errore"></span>
								</div>
								
								<div class="check">
									<p class="via">Salvare indirizzo per prossimi acquisti?</p>
									<input type="checkbox" name="salvaIndirizzo" id="salvaIndirizzo"/>
								</div>
								
								<button type="submit" id="conferma" name="conferma" disabled>Conferma Ordine</button>
							</form>
						</div>
					</fieldset>
				</div>
				
			</div>
			
			<div class="riepilogo">
				<div class="titolo">
					<h2>Riepilogo Ordine</h2>
				</div>
				
				<div class="prodotti">
					<%
					for (int i = 0; i < carrello.size(); i++) { 
	       		 		ProdottoCarrello item = carrello.get(i);
	       		 		VarianteProdotto variante = item.getVariante();
	
	       		 		//Prodotto p = (prodottiDettaglio != null && i < prodottiDettaglio.size()) ? prodottiDettaglio.get(i) : null;
	       		 		
	        			String urlImmagine = "img/errori/ImmagineMancante.png"; 
	   					String altImmagine = "Prodotto";
	            
	            		%>
	            		<div class="prodotto">
							<div class="immagine_prodotto">
								<img src="<%= urlImmagine %>" alt="<%= altImmagine %>">
							</div>
							
							<div class="info_prodotto">
								<h3 class="nome_prodotto">Prodotto</h3>
								<p class="descrizione_prodotto">Dettaglio</p>
								<p class="varianti_scelte">
									Taglia: <%= variante.getTaglia() %> - Colore: <%= variante.getColore() %>
								</p>
								<span class="prezzo_prodotto"><%= String.format("%.2f", variante.getPrezzo()) %>€</span>
								<span class="quantita_prodotto">Q.tà: <%= item.getQuantita() %></span>
							</div>
						</div>
	            	<%
						}
					%>
				
				<%
				double prezzoTotale = 0.0;
				for(ProdottoCarrello item : carrello){
					if(item.getVariante() != null){
						prezzoTotale += (item.getVariante().getPrezzo() * item.getQuantita());
					}
				}
				%>
				
				<div class="costi">
					<p class="costo-totale">Totale: <%=String.format("%.2f", prezzoTotale)+"€" %>   +   </p>
					<p class="costo-spedizione">Costi di spedizione: <span class="gradiente">Spedizione Gratuita</span></p>
				</div>
				
				</div>
			</div>
		</main>
		
		<jsp:include page="fragments/Footer.jsp"/>
		<script src="<%=request.getContextPath() %>/scripts/ValidazioneIndirizzo.js"></script>
	</body>
</html>