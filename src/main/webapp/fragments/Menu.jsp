<div id="sidebar_menu" class="sidebar">
	<div class="links">
			<a href="<%= request.getContextPath() %>/CatalogoServlet?prezzoMax=50" class="bottone-sidebar">Promozioni</a>
			<a href="javascript:void(0)" class="bottone-sidebar" onclick="apriFinestra('<%= request.getContextPath() %>/fragments/GuidaAlleTaglie.jsp')">Guida alle Taglie</a>
			<a href="<%= request.getContextPath() %>/CatalogoServlet" class="bottone-sidebar">Catalogo</a>
			<a href="<%= request.getContextPath() %>/CatalogoServlet?idPadre=1" class="bottone-sidebar">Vestiti per Cani</a>
			<a href="<%= request.getContextPath() %>/CatalogoServlet?idPadre=2" class="bottone-sidebar">Vestiti per Gatti</a>
			<a href="<%= request.getContextPath() %>/ChiSiamo.jsp" class="bottone-sidebar">Chi Siamo</a>
	</div>
</div>

<script src="<%= request.getContextPath() %>/scripts/ApriFinestra.js"></script>