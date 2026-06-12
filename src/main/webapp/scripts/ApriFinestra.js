function apriFinestra() {
	const finestra = document.getElementById("overlay-taglie");
	if (finestra) {
		finestra.style.display = "flex";
	}
}

function chiudiFinestra() {
	const finestra = document.getElementById("overlay-taglie");
	if (finestra) {
		finestra.style.display = "none";
	}
}