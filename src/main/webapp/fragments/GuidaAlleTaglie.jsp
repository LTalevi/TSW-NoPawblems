<div class="overlay-finestra" id="overlay-taglie" style="display: none">
	<main class="modal-wrapper">
		<div class="modal-chiudi">
			<button class="btn-chiudi" onclick="chiudiFinestra()" aria-label="Chiudi guida taglie">
				<i class="material-icons">close</i>
			</button>
		</div>
		
		<div class="modal-titolo">
			<h2>Guida alle Taglie</h2>
		</div>

		<div class="selettore-animali">
			<button class="btn-tab attivo" onclick="cambiaTab('cani')">
				<i class="material-icons" style="font-size: 18px;">pets</i> Cani
			</button>
			<button class="btn-tab" onclick="cambiaTab('gatti')">
				<i class="material-icons" style="font-size: 18px;">pets</i> Gatti
			</button>
		</div>

		<div class="modal-contenuto">
			<div id="tab-cani" class="sezione-taglie attiva">
				<table class="tabella-taglie">
					<thead>
						<tr>
							<th>TAGLIA</th>
							<th>PETTO</th>
							<th>SCHIENA</th>
						</tr>
					</thead>
					<tbody>
						<tr><td>XS</td><td>32 cm</td><td>20 cm</td></tr>
						<tr><td>S</td><td>38 cm</td><td>26 cm</td></tr>
						<tr><td>M</td><td>46 cm</td><td>30 cm</td></tr>
						<tr><td>L</td><td>54 cm</td><td>36 cm</td></tr>
						<tr><td>XL</td><td>61 cm</td><td>41 cm</td></tr>
					</tbody>
				</table>
			</div>

			<div id="tab-gatti" class="sezione-taglie">
				<table class="tabella-taglie">
					<thead>
						<tr>
							<th>TAGLIA</th>
							<th>PETTO</th>
							<th>SCHIENA</th>
						</tr>
					</thead>
					<tbody>
						<tr><td>XS</td><td>26 cm</td><td>15 cm</td></tr>
						<tr><td>S</td><td>30 cm</td><td>20 cm</td></tr>
						<tr><td>M</td><td>36 cm</td><td>25 cm</td></tr>
						<tr><td>L</td><td>42 cm</td><td>30 cm</td></tr>
						<tr><td>XL</td><td>50 cm</td><td>35 cm</td></tr>
					</tbody>
				</table>
			</div>
		</div>
	</main>
	<script src="<%= request.getContextPath() %>/scripts/GuidaAlleTaglie.js"></script>
</div>