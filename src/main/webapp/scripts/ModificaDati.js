function modificaDati(event) {
	if (event) {
	        event.preventDefault();
	    }
		
    const testi = document.querySelectorAll('.dati .campo_testo .testo p.dati');
    const inputs = document.querySelectorAll('.dati .campo_testo .testo input');
    
    testi.forEach(p => p.style.display = 'none');
    inputs.forEach(input => input.style.display = 'flex');
    
    document.getElementById('bottoneModifica').style.display = 'none';
    document.getElementById('bottoneSalva').style.display = 'block';
	
	attiva_pulsante_dati(); 
}

function aggiungiIndirizzo(event) {
	const btn = event.currentTarget;
		
	if (btn.id === "bottoneAggiungi") {
		if (event) {
			event.preventDefault();
		}
	}
	
	const inputs = document.querySelectorAll('.indirizzi .input-errore-container .input');
	inputs.forEach(input => input.style.display = 'flex');
	
	document.getElementById("bottoneAggiungi").style.display = 'none';
	document.getElementById("conferma").style.display = 'inline-flex';
	
	attiva_pulsante_indirizzo();
}

