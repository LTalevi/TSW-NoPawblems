<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="model.prodotto.Prodotto" %>
<%@ page import="model.prodottocarrello.ProdottoCarrello" %>
<%@ page import="model.varianteprodotto.VarianteProdotto" %>

<% 
	List<ProdottoCarrello> carrello = (List<ProdottoCarrello>) request.getAttribute("carrello"); 
	List<Prodotto> prodottiDettaglio = (List<Prodotto>) request.getAttribute("prodottiDettaglio"); 
%>

<!DOCTYPE html>
<html>
	<head>
		<link rel="stylesheet" href="<%= request.getContextPath() %>/stylesheets/main.css" type="text/css">
		<link rel="stylesheet" href="<%= request.getContextPath() %>/stylesheets/StileCarrello.css" type="text/css">
		<meta charset="UTF-8">

		<title>Carrello</title>
	</head>
	
	<body>
		<jsp:include page="/fragments/Nav.jsp"/>
		<jsp:include page="/fragments/GuidaAlleTaglie.jsp"/>
	
		<main class="main-wrapper">
					<% if (carrello == null || carrello.isEmpty()) { %>
							<div class="carrello_vuoto">
								<div class="immagine">
									<img src="<%=request.getContextPath() %>/img/errori/CarrelloVuoto.png"/>
								</div>
								
								<div class="testo">
					  				<p>Il carrello è vuoto.</p>
								</div>
							</div>
					<% } else { 
		           		 	for (int i = 0; i < carrello.size(); i++) { 
		           		 		ProdottoCarrello item = carrello.get(i);
		           		 		VarianteProdotto variante = item.getVariante();
	
		           		 		Prodotto p = (prodottiDettaglio != null && i < prodottiDettaglio.size()) ? prodottiDettaglio.get(i) : null;
		           		 		
		            			String urlImmagine = "img/errori/ImmagineMancante.png"; 
		       					String altImmagine = (p != null) ? p.getNome() : "Prodotto";
		                
		                		if (p != null && p.getImmagini() != null && !p.getImmagini().isEmpty()) {
		                    		urlImmagine = p.getImmagini().get(0).getUrl();
		                    		altImmagine = p.getImmagini().get(0).getAlt();
		                		}
						%>	  
						
			<div class="prodotti-carrello">
				<div class="prodotto-carrello" onclick="location.href='DettaglioProdotto?idProdotto=<%=p.getIdProdotto()%>'">
					<div class="immagine_prodotto">
						<img src="<%= urlImmagine %>" alt="<%= altImmagine %>">
					</div>
					
					<div class="info_prodotto">
						<h3 class="nome_prodotto"><%= (p != null) ? p.getNome() : "Prodotto" %></h3>
						<p class="descrizione_prodotto"><%= (p != null) ? p.getDescrizione() : "" %></p>
						<p class="varianti_scelte">
							Taglia: <%= variante.getTaglia() %> - Colore: <%= variante.getColore() %>
						</p>
						<span class="prezzo_prodotto"><%= String.format("%.2f", variante.getPrezzo()) %>€</span>
						<span class="quantita_prodotto">Q.tà: <%= item.getQuantita() %></span>
					</div>
					
					<form action="<%= request.getContextPath() %>/CarrelloServlet" method="post" onclick="event.stopPropagation();" class="formRimozione" style="margin-top: 10px;">
						<input type="hidden" name="azione" value="rimuovi">
						<input type="hidden" name="idVariante" value="<%= variante.getIdVariante() %>">
						<button type="button" onclick="apriConferma(this)">
							Rimuovi
						</button>
					</form>
				</div>
			<%
				} 
			%>
		
		</div>
		
		<%
			double prezzoTotale = 0.0;
			for(ProdottoCarrello item : carrello){
				if(item.getVariante() != null){
					prezzoTotale += (item.getVariante().getPrezzo() * item.getQuantita());
				}
			}
		%>
		
		<div class="riepilogo">
			<p class="costo-totale">Totale: <%=String.format("%.2f", prezzoTotale)+"€" %> </p>
			
			<div class="bottoni-carrello">
				<a class="bottone-svuota-carrello" href="#" onclick="apriConfermaSvuota(event)">
					Svuota Carrello
				</a>
			
				<a class="bottone-checkout" href="<%= request.getContextPath() %>/user/CheckoutServlet">
					Procedi al Checkout
				</a>
			</div>
		</div>
		
		<%
			}
       	%>
		</main>
		
		<jsp:include page="/fragments/ConfermaAzione.jsp"/>		
		<jsp:include page="/fragments/Footer.jsp"/>
		
		<script src="<%= request.getContextPath() %>/scripts/Conferma.js"></script>
	</body>
</html>