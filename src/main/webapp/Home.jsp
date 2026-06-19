<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="model.prodotto.Prodotto" %>

<%
	List<Prodotto> prodottiCane = (List<Prodotto>) request.getAttribute("prodottiCane");
	List<Prodotto> prodottiGatto = (List<Prodotto>) request.getAttribute("prodottiGatto");
%>
<!DOCTYPE html>
<html>

<head>
	<meta charset="UTF-8">
	<link rel="stylesheet" href="<%= request.getContextPath() %>/stylesheets/main.css" type="text/css">
	
	<title>NoPawblems</title>
</head>

<body>
	<jsp:include page="/fragments/Nav.jsp"/>
	<jsp:include page="/fragments/Header.jsp"/>
	<jsp:include page="/fragments/GuidaAlleTaglie.jsp"/>
	
	<main class="main-content-wrapper">
	
		<section class="prodotti-cane">
			<div class="titolo-blocco">
				<h2>Prodotti Cane</h2>
			</div>
			
			<div class="blocco-immagine-testo">
				<button class="arrow_button prev" aria-label="prodotto precedente">
	    			<i class="material-icons">keyboard_arrow_left</i>
	  			</button>
	  			
	  			<div class="slider-prodotti-track">
					<% if (prodottiCane == null || prodottiCane.isEmpty()) { %>
		  				<p style="text-align:center; width:100%; margin:auto;">Nessun prodotto disponibile al momento.</p>
					<% } else { 
	           		 	for (Prodotto p : prodottiCane) { 
	            			String urlImmagine = "img/Header_img/Header_img_1.png"; 
	       					String altImmagine = p.getNome();
	                
	                		if (p.getImmagini() != null && !p.getImmagini().isEmpty()) {
	                    		urlImmagine = p.getImmagini().get(0).getUrl();
	                    		altImmagine = p.getImmagini().get(0).getAlt();
	                		}
					%>	  	
		  			
		  			<div class="slide-prodotto">
						<div class="testo-laterale">
							<div class="descrizione-prodotto-homepage">
								<h3><%= p.getNome() %></h3>
								<p><%= p.getDescrizione() %></p>
							</div>
							
							<a class="bottone-homepage" href="<%= request.getContextPath() %>/DettaglioProdottoServlet?idProdotto=<%= p.getIdProdotto() %>">
           						Acquista ora
        					</a>
						</div>
						
						<div class="immagine-laterale">
							<img src="<%= urlImmagine %>" alt="<%= altImmagine %>">
						</div>
					</div>
					
					<% 
	    					} 
	   					} 
	    			%>
    			</div> 
    			
    			<button class="arrow_button next" aria-label="prodotto successivo">
				    <i class="material-icons">keyboard_arrow_right</i>
				</button>		
			</div>
		</section>
		
		
		<section class="prodotti-gatto">
			<div class="titolo-blocco">
				<h2>Prodotti Gatto</h2>
			</div>
			
			<div class="blocco-immagine-testo">
				<button class="arrow_button prev" aria-label="prodotto precedente">
	    			<i class="material-icons">keyboard_arrow_left</i>
	  			</button>
	  			
	  			<div class="slider-prodotti-track">
					<% if (prodottiGatto == null || prodottiGatto.isEmpty()) { %>
		  				<p style="text-align:center; width:100%; margin:auto;">Nessun prodotto disponibile al momento.</p>
					<% } else { 
	           		 	for (Prodotto p : prodottiGatto) { 
	            			String urlImmagine = "img/Header_img/Header_img_2.png"; // Placeholder Gatto
	       					String altImmagine = p.getNome();
	                
	                		if (p.getImmagini() != null && !p.getImmagini().isEmpty()) {
	                    		urlImmagine = p.getImmagini().get(0).getUrl();
	                    		altImmagine = p.getImmagini().get(0).getAlt();
	                		}
					%>	
					
					<div class="slide-prodotto">
						<div class="immagine-laterale">
							<img src="<%= urlImmagine %>" alt="<%= altImmagine %>">
						</div>
						
						<div class="testo-laterale">
							<div class="descrizione-prodotto-homepage">
								<h3><%= p.getNome() %></h3>
								<p><%= p.getDescrizione() %></p>
							</div>
							
							<a class="bottone-homepage" href="<%= request.getContextPath() %>/DettaglioProdottoServlet?idProdotto=<%= p.getIdProdotto() %>">
           						Acquista ora
        					</a>
						</div>
					</div>
					
					<% 
	    					} 
	   					} 
	    			%>
				</div> <button class="arrow_button next" aria-label="prodotto successivo">
				    <i class="material-icons">keyboard_arrow_right</i>
				</button>
			</div>
		</section>
		
	</main>
		
	<jsp:include page="/fragments/Footer.jsp"/>
	
	<script src="scripts/carosello.js"></script>
</body>
</html>