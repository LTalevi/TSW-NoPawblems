<%@page import="model.utente.Utente"%>
<%
    HttpSession sessione = request.getSession(false);
    Utente utente = (sessione != null) ? (Utente) sessione.getAttribute("utente") : null;
%>

<div id="sidebar_menu" class="sidebar">
	<div class="links">
			<a href="<%= request.getContextPath() %>/CatalogoServlet?prezzoMax=50" class="bottone-sidebar">Promozioni</a>
			<a href="javascript:void(0)" class="bottone-sidebar" onclick="apriFinestra('<%= request.getContextPath() %>/fragments/GuidaAlleTaglie.jsp')">Guida alle Taglie</a>
			<a href="<%= request.getContextPath() %>/CatalogoServlet" class="bottone-sidebar">Catalogo</a>
			<%
				if(utente == null || !utente.isAdmin()){
					%>
						<a href="<%= request.getContextPath() %>/CatalogoServlet?idPadre=1" class="bottone-sidebar">Vestiti per Cani</a>
					<%
					}
				else{
					%>
						<a href="<%= request.getContextPath() %>/admin/GestioneOrdiniAdminServlet" class="bottone-sidebar">Visualizzazione Ordini Admin</a>
					<%
					}
				%>
				
				<%
					if(utente == null || !utente.isAdmin()){
						%>	
				<a href="<%= request.getContextPath() %>/CatalogoServlet?idPadre=2" class="bottone-sidebar">Vestiti per Gatti</a>
				<%
						}
					else{
						%>
							<a href="<%= request.getContextPath() %>/admin/GestioneProdottiAdminServlet" class="bottone-sidebar">Modifica Catalogo Admin</a>
						<%
						}
					%>
			<a href="<%= request.getContextPath() %>/ChiSiamo" class="bottone-sidebar">Chi Siamo</a>
	</div>
</div>

<script src="<%= request.getContextPath() %>/scripts/ApriFinestra.js"></script>