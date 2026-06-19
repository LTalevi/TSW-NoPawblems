let form = {
	via: false,
	citta: false,
	nazione: false,
	cap: false,
	provincia: false
}

function gestisci_stato(input, errore, valido, messaggio){
	if(valido){
		input.classList.remove("non_valido");
		input.classList.add("valido");
		errore.style.display = "none";
	}
	else{
		input.classList.remove("valido");
		input.classList.add("non_valido");
		errore.textContent = messaggio;
		errore.style.display = "block";
	}
	
	attiva_pulsante();
}

function valida_via(){
	const via = document.getElementById("via");
	const container = document.getElementById("input_via");
	const errore = document.getElementById("errore_via");
	const regex = /^[A-Za-zÀ-ù0-9\s,./]{2,50}$/
	
	form.via = regex.test(via.value);
	gestisci_stato(container, errore, form.via, "La via deve contenere solo lettere e almeno 2 caratteri.");
}

function valida_cap(){
	const cap = document.getElementById("cap");
	const container = document.getElementById("input_cap");
	const errore = document.getElementById("errore_cap");
	const regex = /^\d{5}$/;
	
	form.cap = regex.test(cap.value);
	gestisci_stato(container, errore, form.cap, "Il CAP deve contenere esattamente 5 cifre.");
}

function valida_nazione(){
	const nazione = document.getElementById("nazione");
	const container = document.getElementById("input_nazione");
	const errore = document.getElementById("errore_nazione");
	const regex = /^[A-Za-zÀ-ù ]{2,30}$/;
	
	form.nazione = regex.test(nazione.value);
	gestisci_stato(container, errore, form.nazione, "La nazione deve contenere solo lettere e almeno 2 caratteri.");
}

function valida_citta(){
	const citta = document.getElementById("citta");
	const container = document.getElementById("input_citta");
	const errore = document.getElementById("errore_citta");
	const regex = /^[A-Za-zÀ-ù ]{2,30}$/;
	
	form.citta = regex.test(citta.value);
	gestisci_stato(container, errore, form.citta, "La città deve contenere solo lettere e almeno 2 caratteri.");
}

function valida_provincia(){
	const provincia = document.getElementById("provincia");
	const container = document.getElementById("input_provincia");
	const errore = document.getElementById("errore_provincia");
	const regex = /^[A-Za-zÀ-ù ]{2,30}$/;
	
	form.provincia = regex.test(provincia.value);
	gestisci_stato(container, errore, form.provincia, "La provincia deve contenere solo lettere e almeno 2 caratteri.");
}

function attiva_pulsante(){
	const btn = document.getElementById("conferma");
	
	if(form.via && form.citta && form.provincia && form.nazione && form.cap){
		btn.removeAttribute("disabled");
	}
	else{
		btn.setAttribute("disabled", "true");
	}
}