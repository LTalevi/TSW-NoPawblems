<nav>
	<div class="toggle_menu">
		<button id="toggle_button" class="toggle_menu_button" aria-label="Apri menu">
			<i class="material-icons menu">menu</i>
		</button>

		<jsp:include page="Menu.jsp"/>	
	</div>

	<a href="<%= request.getContextPath() %>/HomeServlet" class="logo_navbar" aria-label="Torna alla homepage">
		<img src="img/logo/logo_NoPawblems_esteso.jpeg" alt="Logo NoPawblems">
	</a>
	
	<div class="sezioni_navbar">
	
		<div class="search_wrapper">
			<input type="text" class="search_text input_search" placeholder="Cerca prodotti...">
			
			<button class="Cerca" aria-label="Cerca">
				<i class="material-icons">search</i>
			</button>
			
			<div class="suggestions"></div>
	</div>
			
		<button class="Profilo" aria-label="Apri profilo" onclick="window.location.href='profiloServlet'">
			<i class="material-icons">account_circle</i>	
		</button>
	
		<button class="Carrello" aria-label="Apri carrello" onclick="window.location.href='carrelloServlet'">
			<i class="material-icons">shopping_cart</i>	
		</button>
	</div>
	
</nav>

<script src="scripts/ToggleMenu.js"></script>
<script src="scripts/search.js"></script>