<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    Long idPadreForm = (Long) request.getAttribute("idPadre");
    Float pMinForm = (Float) request.getAttribute("prezzoMin");
    Float pMaxForm = (Float) request.getAttribute("prezzoMax");
    String ordForm = (String) request.getAttribute("ordinamento");
%>

<div class="Filtri" aria-label="Filtri">

	<form action="<%= request.getContextPath() %>/CatalogoServlet" method="GET">
		<div class="prezziMinMax">
			<fieldset>
				<label for="prezzoMin">Prezzo Minimo:</label>
				<input type="number" id="prezzoMin" name="prezzoMin" step="0.01" min="0" value="<%= pMinForm != null ? pMinForm : "" %>">
			
				<label for="prezzoMax">Prezzo Massimo:</label>
				<input type="number" id="prezzoMax" name="prezzoMax" step="0.01" min="0" value="<%= pMaxForm != null ? pMaxForm : "" %>">
			</fieldset>
		</div>
		
		<div class="ordinamento">
			<fieldset>
				<label for="ordinamento">Ordina per:</label>
				<select id="ordinamento" name="ordinamento">
					<option value="prezzoCrescente" <%= "prezzoCrescente".equals(ordForm) ? "selected" : "" %>>Prezzo Crescente</option>
                    <option value="prezzoDecrescente" <%= "prezzoDecrescente".equals(ordForm) ? "selected" : "" %>>Prezzo Decrescente</option>
                    <option value="nomeAZ" <%= "nomeAZ".equals(ordForm) ? "selected" : "" %>>Ordine Alfabetico</option>
                    <option value="nomeZA" <%= "nomeZA".equals(ordForm) ? "selected" : "" %>>Ordine Alfabetico Inverso</option>
				</select>
			</fieldset>
		</div>
		
		<div class="categoria">
			<fieldset>
				<legend>Seleziona Categoria</legend>
					<div class="radio_container">
						<label>
                            <input type="radio" class="radio" name="idPadre" value="" <%= (idPadreForm == null) ? "checked" : "" %>>
                            <span class="radio_contenuto">Tutti</span>
                        </label>
                        
                        <label>
                            <input type="radio" class="radio" name="idPadre" value="1" <%= (idPadreForm != null && idPadreForm == 1) ? "checked" : "" %>>
                            <span class="radio_contenuto">Cane</span>
                        </label>
                        
                        <label>
                            <input type="radio" class="radio" name="idPadre" value="2" <%= (idPadreForm != null && idPadreForm == 2) ? "checked" : "" %>>
                            <span class="radio_contenuto">Gatto</span>
                        </label>
					</div>
			</fieldset>
		</div>
		
		<button type="submit">Applica Filtri.</button>
	</form>
</div>
