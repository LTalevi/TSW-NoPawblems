<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="model.prodotto.Prodotto" %>

<%
    List<Prodotto> prodottiOfferta = (List<Prodotto>) request.getAttribute("prodottiOfferta");
    if (prodottiOfferta == null) {
        prodottiOfferta = new ArrayList<>();
    }
%>
<header>
	
	<button class="arrow_button_header prev" aria-label="prodotto precedente" onclick="slideChange(-1)">
	   	<i class="material-icons">keyboard_arrow_left</i>
	</button>
	
	<div id="slideshow_id" class="slideshow">
	<% if (prodottiOfferta.isEmpty()) { %>
	  
	<% } else { 
            for (Prodotto p : prodottiOfferta) { 
            	String urlImmagine = "img/Header_img/Header_img_1.png"; 
       			String altImmagine = p.getNome();
                
                if (p.getImmagini() != null && !p.getImmagini().isEmpty()) {
                    urlImmagine = p.getImmagini().get(0).getUrl();
                    altImmagine = p.getImmagini().get(0).getAlt();
                }
	%>	
		<a class="slide" href="<%= request.getContextPath() %>/DettaglioProdottoServlet?idProdotto=<%= p.getIdProdotto() %>">
                <img src="<%= urlImmagine %>" alt="<%= altImmagine %>">
        </a>	  		
	<% 
    		} 
   		} 
    %>
    </div>
    
	<button class="arrow_button_header next" aria-label="prodotto successivo" onclick="slideChange(1)">
	    <i class="material-icons">keyboard_arrow_right</i>
	</button>
		
	<div class="slide_dots">
		
	</div>
		
	<div class="loading_bar_container">
		<div id="loading_bar" class="loading_bar"></div>
	</div>
</header>
	
<script src="scripts/header.js"></script>