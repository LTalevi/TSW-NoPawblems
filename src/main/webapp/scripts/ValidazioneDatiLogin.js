let form = {
	email: false,
	password: false,
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

function attiva_pulsante(){
	const btn = document.getElementById("accedi");
	
	if(form.email && form.password){
		btn.removeAttribute("disabled");
	}
	else{
		btn.setAttribute("disabled", true);
	}
}