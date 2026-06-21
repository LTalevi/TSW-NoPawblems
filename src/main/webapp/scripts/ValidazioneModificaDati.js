let form_dati = {
	nome: true,
	cognome: true,
	telefono: true,
	email: true,
}

let form_indirizzi = {
	via: false,
	citta: false,
	nazione: false,
	cap: false,
	provincia: false
}

function gestisci_stato(input, errore, valido, messaggio, tipo_form){
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
	
	if (tipo_form === "dati") {
			attiva_pulsante_dati();
	} else if (tipo_form === "indirizzo") {
			attiva_pulsante_indirizzo();
		}
}

function valida_nome(){
	const nome = document.getElementById("nome");
	const container = document.getElementById("input_nome");
	const errore = document.getElementById("errore_nome");
	const regex = /^[A-Za-zÀ-ù ]{2,30}$/;
	
	form_dati.nome = regex.test(nome.value);
	gestisci_stato(container, errore, form_dati.nome, "Il nome deve contenere solo lettere e almeno 2 caratteri.", "dati");
}

function valida_cognome(){
	const cognome = document.getElementById("cognome");
	const container = document.getElementById("input_cognome");
	const errore = document.getElementById("errore_cognome");
	const regex = /^[A-Za-zÀ-ù ]{2,30}$/;
	
	form_dati.cognome = regex.test(cognome.value);
	gestisci_stato(container, errore, form_dati.cognome, "Il cognome deve contenere solo lettere e almeno 2 caratteri.", "dati");
}

function valida_email(){
	const email = document.getElementById("email");
	const container = document.getElementById("input_email");
	const errore = document.getElementById("errore_email");
	const regex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
	
	const emailValue = email.value.trim();
	const contextPath = email.dataset.context;
	
	if (!regex.test(emailValue)) {
		form_dati.email = false;
		gestisci_stato(container, errore, false, "L' email inserita non è valida.", "dati");
		return;
	}

	const url = `${contextPath}/EmailCheck?email=${encodeURIComponent(emailValue)}`;
	
	fetch(url)
		.then(response => response.json())
		.then(data => {
			if (data.esiste) {
				form_dati.email = false;
				gestisci_stato(container, errore, false, "L' email inserita è già in uso.", "dati");
			} else {
				form_dati.email = true;
				gestisci_stato(container, errore, true, "", "dati");
			}
		})
		.catch(err => {
			console.error("Errore nel server durante il controllo email:", err);
			form_dati.email = false;
			gestisci_stato(container, errore, false, "Servizio di verifica momentaneamente non disponibile.", "dati");
		});
}

function valida_telefono(){
	const telefono = document.getElementById("telefono");
	const container = document.getElementById("input_telefono");
	const errore = document.getElementById("errore_telefono");
	const regex = /^[0-9]{10}$/;
	
	form_dati.telefono = regex.test(telefono.value);
	gestisci_stato(container, errore, form_dati.telefono, "Numero di telefono inserito non valido.", "dati")
}

function valida_via(){
	const via = document.getElementById("via");
	const container = document.getElementById("input_via");
	const errore = document.getElementById("errore_via");
	const regex = /^[A-Za-zÀ-ù0-9\s,./]{2,50}$/
	
	form_indirizzi.via = regex.test(via.value);
	gestisci_stato(container, errore, form_indirizzi.via, "La via deve contenere solo lettere e almeno 2 caratteri.", "indirizzo");
}

function valida_cap(){
	const cap = document.getElementById("cap");
	const container = document.getElementById("input_cap");
	const errore = document.getElementById("errore_cap");
	const regex = /^\d{5}$/;
	
	form_indirizzi.cap = regex.test(cap.value);
	gestisci_stato(container, errore, form_indirizzi.cap, "Il CAP deve contenere esattamente 5 cifre.", "indirizzo");
}

function valida_nazione(){
	const nazione = document.getElementById("nazione");
	const container = document.getElementById("input_nazione");
	const errore = document.getElementById("errore_nazione");
	const regex = /^[A-Za-zÀ-ù ]{2,30}$/;
	
	form_indirizzi.nazione = regex.test(nazione.value);
	gestisci_stato(container, errore, form_indirizzi.nazione, "La nazione deve contenere solo lettere e almeno 2 caratteri.", "indirizzo");
}

function valida_citta(){
	const citta = document.getElementById("citta");
	const container = document.getElementById("input_citta");
	const errore = document.getElementById("errore_citta");
	const regex = /^[A-Za-zÀ-ù ]{2,30}$/;
	
	form_indirizzi.citta = regex.test(citta.value);
	gestisci_stato(container, errore, form_indirizzi.citta, "La città deve contenere solo lettere e almeno 2 caratteri.", "indirizzo");
}

function valida_provincia(){
	const provincia = document.getElementById("provincia");
	const container = document.getElementById("input_provincia");
	const errore = document.getElementById("errore_provincia");
	const regex = /^[A-Za-zÀ-ù ]{2,30}$/;
	
	form_indirizzi.provincia = regex.test(provincia.value);
	gestisci_stato(container, errore, form_indirizzi.provincia, "La provincia deve contenere solo lettere e almeno 2 caratteri.", "indirizzo");
}

function attiva_pulsante_dati(){
	const btn = document.getElementById("bottoneSalva");
	
	if(form_dati.nome && form_dati.cognome && form_dati.telefono && form_dati.email){
		btn.removeAttribute("disabled");
	}
	else{
		btn.setAttribute("disabled", true);
	}
}

function attiva_pulsante_indirizzo(){
	const btn = document.getElementById("conferma");
	
	if(form_indirizzi.via && form_indirizzi.citta && form_indirizzi.provincia && form_indirizzi.nazione && form_indirizzi.cap){
		btn.removeAttribute("disabled");
	}
	else{
		btn.setAttribute("disabled", "true");
	}
}