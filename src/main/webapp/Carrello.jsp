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
		<link rel="stylesheet" href="stylesheets/StileCarrello.css" type="text/css">
		<link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
		<meta charset="UTF-8">

		<title>Carrello</title>
	</head>
	
	<body>
		<jsp:include page="Nav.jsp"/>
	
		<div class="main-wrapper">
			<div class="prodotti-carrello">
				<% if (prodotti == null || prodotti.isEmpty()) { %>
		  				<p style="text-align:center; width:100%; margin:auto;">Il carrello è vuoto.</p>
				<% } else { 
	           		 	for (Prodotto p : prodotti) { 
	            			String urlImmagine = "img/Header_img/Header_img_1.png"; 
	       					String altImmagine = p.getNome();
	                
	                		if (p.getImmagini() != null && !p.getImmagini().isEmpty()) {
	                    		urlImmagine = p.getImmagini().get(0).getUrl();
	                    		altImmagine = p.getImmagini().get(0).getAlt();
	                		}
					%>	  
			<div class="prodotto-carrello">
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
				
			</div>
		<%
			} 
		%>
		
		</div>
		
		<%
			double prezzoTotale = 0.0;
			if(prodotti != null){
				for(Prodotto pt : prodotti){
					if(pt.getVarianti() != null && !pt.getVarianti().isEmpty()){
						prezzoTotale += pt.getVarianti().get(0).getPrezzo();
					}
				}
			}
		%>
		
		<div class="riepilogo">
			<p class="costo-totale">Totale: <%=String.format("%.2f", prezzoTotale)+"€" %> </p>
			
			<a class="bottone-checkout" href="<%= request.getContextPath() %>/CheckoutServlet">
				Procedi al Checkout
			</a>
		</div>
		
		<%
			}
       	%>
		
		 </div>
		
		<jsp:include page="Footer.jsp"/>
	</body>
</html>