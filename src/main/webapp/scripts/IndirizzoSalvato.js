function autocompila() {
	const select = document.getElementById("selezionaIndirizzo");
	if(!select){
		return;
	}
	
	const opzioneScelta = select.options[select.selectedIndex];
	
	const via = document.getElementById("via");
    const citta = document.getElementById("citta");
    const cap = document.getElementById("cap");
    const provincia= document.getElementById("provincia");
    const nazione = document.getElementById("nazione");
    const salva = document.getElementById("box-salva-indirizzo");
    const id = document.getElementById("idIndirizzoSalvato");
    const btn = document.getElementById("conferma");
	
	if (opzioneScelta.value === "nuovo") {
	        via.value = "";
	        citta.value = "";
	        cap.value = "";
	        provincia.value = "";
	        nazione.value = "";
	        id.value = "";
	        
	        salva.style.display = "flex";
	        btn.disabled = true;
		}
	else {
		via.value = opzioneScelta.dataset.via;
		citta.value = opzioneScelta.dataset.citta;
		cap.value = opzioneScelta.dataset.cap;
		provincia.value = opzioneScelta.dataset.provincia;
		nazione.value = opzioneScelta.dataset.nazione;
		id.value = opzioneScelta.value;
		salva.style.display = "none";
		btn.disabled = false;
		}
}