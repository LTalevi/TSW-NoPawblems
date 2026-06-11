<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="model.prodotto.Prodotto" %>
<%@ page import="model.immagine.Immagine" %>
<%@ page import="model.varianteprodotto.VarianteProdotto" %>

<%
	Prodotto p = (Prodotto) request.getAttribute("prodotto");
%>

<!DOCTYPE html>
<html>
	<head>
		<link rel="stylesheet" href="stylesheets/StileVariabili.css" type="text/css">
		<link rel="stylesheet" href="stylesheets/StileHeader.css" type="text/css">
		<link rel="stylesheet" href="stylesheets/StileFooter.css" type="text/css">
		<link rel="stylesheet" href="stylesheets/StileMenu.css" type="text/css">
		<link rel="stylesheet" href="stylesheets/StileDettaglioProdotto.css" type="text/css">
		<link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
		<meta charset="UTF-8">
		<title>Dettaglio Prodotto</title>
	</head>
	
	<body>
		<jsp:include page="Nav.jsp"/>
	
		<main class="main-wrapper">
			<% if (p == null) { %>
		  				<p style="text-align:center; width:100%; margin:auto;">C'è stato un errore con il caricamento del prodotto.</p>
				<% } else { 
							String urlImmagine = "img/Header_img/Header_img_1.png"; 
	       					String altImmagine = p.getNome();

	       					List<Immagine> immagini = p.getImmagini();
	                
	                		if (p.getImmagini() != null && !p.getImmagini().isEmpty()) {
	                    		urlImmagine = p.getImmagini().get(0).getUrl();
	                    		altImmagine = p.getImmagini().get(0).getAlt();
	                		}
	                		
	                		List<String> taglieDisponibili = new ArrayList<>();
	                		List<String> coloriDisponibili = new ArrayList<>();
	                		List<String> hexColoriDisponibili = new ArrayList<>();
	                		
	                		if(p.getVarianti() != null) {
	                			for(VarianteProdotto v : p.getVarianti()) {
	                				if(!taglieDisponibili.contains(v.getTaglia())) {
	                					taglieDisponibili.add(v.getTaglia());
	                				}
	                				if(!coloriDisponibili.contains(v.getColore())) {
	                					coloriDisponibili.add(v.getColore());
	                					coloriDisponibili.add(v.getColoreHex()); // Coppia nome-hex sequenziale
	                				}
	                			}
	                		}
					%>
			<div class="prodotto" id="prodotto-container" data-id-prodotto="<%= p.getIdProdotto() %>" data-context-path="<%= request.getContextPath() %>">
				<div class="immagini">
					<div class="immagine-principale-prodotto">
						<img src="<%=urlImmagine %>" alt="<%=altImmagine %>"/>
					</div>
					
					<div class="immagini-secondarie-prodotto">
						<%
							if(immagini != null){
								for(int i = 0; i < immagini.size(); i++){
									Immagine immagine_secondaria = immagini.get(i);								
						%>
							
							<div class="miniature-immagini">
								<img src="<%=immagine_secondaria.getUrl() %>" alt="<%=immagine_secondaria.getAlt() %>"/>
							</div>
						
						<%
								}
							}
						%>
					</div>		
				</div>
				
				<div class="dettagli">
					<h3 class="nome-prodotto"><%=p.getNome() %></h3>
					<p class="descrizione-prodotto"><%=p.getDescrizione() %></p>
					<span class="prezzo-prodotto" id="prezzo-display">
   						<%= (p.getVarianti() != null && !p.getVarianti().isEmpty()) ? String.format("%.2f", p.getVarianti().get(0).getPrezzo()) + "€" : "Prezzo non disponibile." %>
					</span>
					
					<div class="selettore-varianti" style="margin: 20px 0;">
		        	<% if(!taglieDisponibili.isEmpty() && taglieDisponibili.get(0) != null) { %>
		        		<div class="selettore-gruppo">
		            		<h4>Taglia:</h4>
		            		<div class="opzioni">
		                		<% for(String taglia : taglieDisponibili) { 
		                    		if(taglia != null && !taglia.isEmpty()) { %>
		                  		  	<button type="button" class="btn-taglia" onclick="selezionaTaglia('<%= taglia %>', this)">
		                    	    	<%= taglia %>
		                    		</button>
		                		<%  } 
		                		} %>
		            		</div>
		       			</div>
		  	     	<% } %>
		
					<% if(!coloriDisponibili.isEmpty() && coloriDisponibili.get(0) != null) { %>
		       		<div class="selettore-gruppo">
		            	<h4>Colore:</h4>
		            	<div class="opzioni">
		               	 	<% for(int i = 0; i < coloriDisponibili.size(); i += 2) { 
		                    	String nomeColore = coloriDisponibili.get(i);
		                    	String hexColore = coloriDisponibili.get(i+1);
		                    	if(nomeColore != null && !nomeColore.isEmpty()) { %>
		                    	<button type="button" class="btn-colore" style="background-color: <%= hexColore %>;" title="<%= nomeColore %>" onclick="selezionaColore('<%= nomeColore %>', this)">
		                    	</button>
		                	<%  } 
		                	} %>
		            	</div>
		        	</div>
		        	<% } %>
		        
		        	<div id="disponibilita-info" class="msg-disponibilita"></div>
		    	</div>
		    
		    	<form action="<%= request.getContextPath() %>/CarrelloServlet" method="post" class="form-aggiunta-carrello" onsubmit="return validaAggiunta()">
		        	<input type="hidden" name="azione" value="aggiungi">
		        	<input type="hidden" id="idVarianteInput" name="idVariante" value="">
		
		        	<div class="selettore-quantita">
		            	<label for="quantita">Quantità:</label>
		            	<input type="number" id="quantita" name="quantita" value="1" min="1" required>
		        	</div>
		
		        	<button type="submit" id="btnAggiungiCarrello" class="bottone-aggiunta-carrello" disabled>
		            	Aggiungi al carrello
		        	</button>
		    	</form>
			</div>
		</div>
			
			<%
		      	}
			%>
		</main>
	
		<jsp:include page="Footer.jsp"/>
		<script src="<%= request.getContextPath() %>/scripts/dettaglioProdotto.js"></script>
	</body>
</html>