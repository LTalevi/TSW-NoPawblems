<%@page import="model.utente.Utente"%>
<%
    HttpSession sessione = request.getSession(false);
    Utente utente = (sessione != null) ? (Utente) sessione.getAttribute("utente") : null;
    
    String profilo;
    if (utente == null) {
        profilo = request.getContextPath() + "/Login.jsp";
    } else {
        profilo = request.getContextPath() + "/user/AreaUtente";
    }
%>

<nav>
	<div class="toggle_menu">
		<button id="toggle_button" class="toggle_menu_button" aria-label="Apri menu">
			<i class="material-icons menu">menu</i>
		</button>

		<jsp:include page="Menu.jsp"/>	
	</div>

	<a href="<%= request.getContextPath() %>/HomeServlet" class="logo_navbar" aria-label="Torna alla homepage">
		<img src="<%= request.getContextPath() %>/img/logo/logo_NoPawblems_esteso.jpeg" alt="Logo NoPawblems">
	</a>
	
	<div class="sezioni_navbar">
	
		<div class="search_wrapper">
			<input type="text" class="search_text input_search" placeholder="Cerca prodotti...">
			
			<button class="Cerca" aria-label="Cerca">
				<i class="material-icons">search</i>
			</button>
			
			<div class="suggestions"></div>
	</div>
			
		<button class="Profilo" aria-label="Apri profilo" onclick="window.location.href='<%=profilo%>'">
			<i class="material-icons">account_circle</i>	
		</button>
	
		<button class="Carrello" aria-label="Apri carrello" onclick="window.location.href='<%=request.getContextPath() %>/CarrelloServlet'">
			<i class="material-icons">shopping_cart</i>	
		</button>
	</div>
	
</nav>

<script src="<%=request.getContextPath() %>/scripts/ToggleMenu.js"></script>
<script src="<%=request.getContextPath() %>/scripts/search.js"></script>