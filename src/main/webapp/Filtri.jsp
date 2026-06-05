<div class="Filtri" aria-label="Filtri">

	<form action="/CatalogoServlet" method="GET">
		<div class="prezziMinMax">
			<fieldset>
				<label for="prezzoMin">Prezzo Minimo:</label>
				<input type="number" id="prezzoMin" name="prezzoMin" step="0.1" min="0">
			
				<label for="prezzoMax">Prezzo Massimo:</label>
				<input type="number" id="prezzoMax" name="prezzoMax" step="0.1" min="0">
			</fieldset>
		</div>
		
		<div class="ordinamento">
			<fieldset>
				<label for="ordinamento">Ordina per:</label>
				<select id="ordinamento" name="ordinamento">
					<option value="prezzoCrescente">Prezzo Crescente.</option>
					<option value="prezzoDecrescente">Prezzo Decrescente.</option>
					<option value="nomeAZ">Ordine Alfabetico.</option>
					<option value="nomeZA">Ordine Alfabetico Inverso.</option>
				</select>
			</fieldset>
		</div>
		
		<div class="categoria">
			<fieldset>
				<legend>Seleziona Categoria</legend>
					<div class="radio_container">
						<label>
							<input type="radio" class="radio" name="categoria" value="cane" checked>
							<span class="radio_contenuto">Cane</span>
						</label>
						
						<label>
							<input type="radio" class="radio" name="categoria" value="gatto">
							<span class="radio_contenuto">Gatto</span>
						</label>
					</div>
			</fieldset>
		</div>
		
		<button type="submit">Applica Filtri.</button>
	</form>
</div>
