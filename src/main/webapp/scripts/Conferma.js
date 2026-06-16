let form = null;
let azioneCorrente = "";

function apriConferma(btn){
	form = btn.closest(".formRimozione");
	
	const finestra = document.getElementById("overlay-finestra");
	if(finestra){
		finestra.style.display = "flex";
	}
}

function chiudiConferma(){
	const finestra = document.getElementById("overlay-finestra");
	
	if(finestra){
		finestra.style.display = "none";
	}
	
	form = null;
}

document.addEventListener("DOMContentLoaded", function() {
    const btn = document.getElementById('Si');
    if(btn) {
       btn.addEventListener('click', function() {
           if(form && azioneCorrente === "rimuovi") {
              form.submit(); 
			  chiudiConferma();
            }
			else if(azioneCorrente === "svuota"){
				chiudiConferma(); 
                fetch('CarrelloServlet', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/x-www-form-urlencoded',
                    },
                    body: 'azione=svuota'
                })
                .then(response => {
                    if (response.ok) {
                        window.location.reload(); // Ricarica la pagina aggiornata
                    } else {
                        alert("Errore durante lo svuotamento del carrello.");
                    }
                })
                .catch(error => {
                    console.error("Errore:", error);
                    alert("Errore di connessione.");
			});
			}
        });
    }
});

function apriConfermaSvuota(event) {
    event.preventDefault(); 
    azioneCorrente = "svuota";
    form = null;
    
    const finestra = document.getElementById("overlay-finestra");
    if (finestra) {
        finestra.style.display = "flex";
    }
}