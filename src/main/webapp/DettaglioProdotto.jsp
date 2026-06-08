<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="model.prodotto.Prodotto" %>
<%@ page import="model.immagine.Immagine" %>

<%
	Prodotto p = (Prodotto) request.getAttribute("prodotto");
%>

<!DOCTYPE html>
<html>
	<head>
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
	
		<div class="main-wrapper">
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
					%>
			<div class="prodotto">
				<div class="immagini">
					<div class="immagine-principale-prodotto">
						<img src="<%=urlImmagine %>" alt="<%=altImmagine %>"/>
					</div>
					
					<div class="immagini-secondarie-prodotto">
						<%
							if(immagini != null){
								for(int i = 1; i < immagini.size(); i++){
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
					<span class="prezzo-prodotto">
						<%= (p.getVarianti() != null && !p.getVarianti().isEmpty()) ? p.getVarianti().get(0).getPrezzo() + "€" : "Prezzo non disponibile." %>
					</span>
					
					<a class="bottone-aggiunta-carrello" href="<%=request.getContextPath()%>/CarrelloServlet?id=<%=p.getIdProdotto()%>">
						Aggiungi al carrello.
					</a>
				</div>
			</div>
			
			<%
		      	}
			%>
		</div>
	
		<jsp:include page="Footer.jsp"/>
	</body>
</html>