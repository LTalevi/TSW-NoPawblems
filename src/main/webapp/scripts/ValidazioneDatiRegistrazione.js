let form = {
	nome: false,
	cognome: false,
	telefono: false,
	email: false,
	password: false,
	conferma: false
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

function valida_nome(){
	const nome = document.getElementById("nome");
	const container = document.getElementById("input_nome");
	const errore = document.getElementById("errore_nome");
	const regex = /^[A-Za-zÀ-ù ]{2,30}$/;
	
	form.nome = regex.test(nome.value);
	gestisci_stato(container, errore, form.nome, "Il nome deve contenere solo lettere e almeno 2 caratteri.");
}

function valida_cognome(){
	const cognome = document.getElementById("cognome");
	const container = document.getElementById("input_cognome");
	const errore = document.getElementById("errore_cognome");
	const regex = /^[A-Za-zÀ-ù ]{2,30}$/;
	
	form.cognome = regex.test(cognome.value);
	gestisci_stato(container, errore, form.cognome, "Il cognome deve contenere solo lettere e almeno 2 caratteri.");
}

function valida_email(){
	const email = document.getElementById("email");
	const container = document.getElementById("input_email");
	const errore = document.getElementById("errore_email");
	const regex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
	
	form.email = regex.test(email.value);
	gestisci_stato(container, errore, form.email, "La mail inserita non è valida.");
}

function valida_password(){
	const password = document.getElementById("password");
	const container = document.getElementById("input_password");
	const errore = document.getElementById("errore_password");
	const regex =/^(?=(?:.*[0-9]){2,})(?=(?:.*[a-z]){2,})(?=(?:.*[A-Z]){2,})(?=(?:.*[_\-.:,;&%$()?]){2,}).{8,}$/;
	
	form.password = regex.test(password.value);
	gestisci_stato(container, errore, form.password, "La password deve contenere almeno 8 caratteri tra cui\n"
		+" 2 Lettere Maiuscole, 2 Minuscole, 2 Cifre e 2 Caratteri Speciali tra questi _ - . : , ; & % $ ( ) ?");
}

function valida_conferma(){
	const password = document.getElementById("password");
	const conferma = document.getElementById("conferma_password");
	const container = document.getElementById("input_conferma");
	const errore = document.getElementById("errore_conferma_password");
	
	form.conferma = ((password.value === conferma.value)&&(conferma.value !== ""));
	gestisci_stato(container, errore, form.conferma, "Le password inserite non corrispondono.");
}

function valida_telefono(){
	const telefono = document.getElementById("telefono");
	const container = document.getElementById("input_telefono");
	const errore = document.getElementById("errore_telefono");
	const regex = /^[0-9]{10}$/;
	//const regex = /^[0-9]{8,11}$/;
	
	form.telefono = regex.test(telefono.value);
	gestisci_stato(container, errore, form.telefono, "Numero di telefono inserito non valido.")
}

function attiva_pulsante(){
	const btn = document.getElementById("registrati");
	
	if(form.nome && form.cognome && form.telefono && form.email && form.password && form.conferma){
		btn.removeAttribute("disabled");
	}
	else{
		btn.setAttribute("disabled", true);
	}
}