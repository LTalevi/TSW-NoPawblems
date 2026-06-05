<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="model.prodotto.Prodotto" %>

<% 
	List<Prodotto> prodotti = (List<Prodotto>) request.getAttribute("prodotti"); 
%>

<!DOCTYPE html>
<html>
	<head>
		<link rel="stylesheet" href="stylesheets/StileHeader.css" type="text/css">
		<link rel="stylesheet" href="stylesheets/StileFooter.css" type="text/css">
		<link rel="stylesheet" href="stylesheets/StileMenu.css" type="text/css">
		<link rel="stylesheet" href="stylesheets/StileCatalogo.css" type="text/css">
		<link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
		<meta charset="UTF-8">

		<title>Catalogo</title>
	</head>

	<body>
		<jsp:include page="Nav.jsp"/>
		<jsp:include page="HeaderCatalogo.jsp"/>

		<div class="main-wrapper">
			<div class="product-grid">
				<% if (prodotti == null || prodotti.isEmpty()) { %>
		  				<p style="text-align:center; width:100%; margin:auto;">Nessun prodotto disponibile al momento.</p>
				<% } else { 
	           		 	for (Prodotto p : prodotti) { 
	            			String urlImmagine = "img/Header_img/Header_img_1.png"; 
	       					String altImmagine = p.getNome();
	                
	                		if (p.getImmagini() != null && !p.getImmagini().isEmpty()) {
	                    		urlImmagine = p.getImmagini().get(0).getUrl();
	                    		altImmagine = p.getImmagini().get(0).getAlt();
	                		}
					%>	  
			<div class="prodotto">
				<a href="DettaglioProdottoServlet?id=<%=p.getIdProdotto()%>">
					<div class="immagine_prodotto">
						<img src="<%= urlImmagine %>" alt="<%= altImmagine %>">
					</div>
					
					<div class="info_prodotto">
						<h3 class="nome_prodotto"><%= p.getNome() %></h3>
						<p class="descrizione_prodotto"><%= p.getDescrizione() %></p>
						<span class="prezzo_prodotto"><%=p.getVarianti().get(0).getPrezzo()+"€"%></span>
					</div>
				</a>
				
				<a class="bottone-acquista" href="<%= request.getContextPath() %>/DettaglioProdottoServlet?idProdotto=<%= p.getIdProdotto() %>">
 					Acquista ora
				</a>
			</div>
			<%
				}
        	}
        	%>
			</div>
		</div>
	



		<jsp:include page="Footer.jsp"/>
	</body>
</html>