let tagliaSelezionata = null;
let coloreSelezionato = null;

function selezionaTaglia(taglia, bottone) {
    tagliaSelezionata = taglia;
    document.querySelectorAll('.btn-taglia').forEach(b => b.classList.remove('attivo'));
    bottone.classList.add('attivo');
    richiediVariante();
}

function selezionaColore(colore, bottone) {
    coloreSelezionato = colore;
    document.querySelectorAll('.btn-colore').forEach(b => b.classList.remove('attivo'));
    bottone.classList.add('attivo');
    richiediVariante();
}

function richiediVariante() {
    const haTaglie = document.querySelectorAll('.btn-taglia').length > 0;
    const haColori = document.querySelectorAll('.btn-colore').length > 0;
    
    if ((haTaglie && !tagliaSelezionata) || (haColori && !coloreSelezionato)) {
        return;
    }

	const container = document.getElementById('prodotto-container');
	const contextPath = container.dataset.contextPath;
	const idProdotto = container.dataset.idProdotto;
	const url = `${contextPath}/SelezionaVariante?idProdotto=${idProdotto}&taglia=${encodeURIComponent(tagliaSelezionata || '')}&colore=${encodeURIComponent(coloreSelezionato || '')}`;
    
	fetch(url)
        .then(response => response.json())
        .then(variante => aggiornaInterfaccia(variante))
        .catch(() => aggiornaInterfaccia(null));
}

function aggiornaInterfaccia(variante) {
    const inputId = document.getElementById('idVarianteInput');
    const btnCarrello = document.getElementById('btnAggiungiCarrello');
    const displayPrezzo = document.getElementById('prezzo-display');
    const infoDisp = document.getElementById('disponibilita-info');
    const inputQuantita = document.getElementById('quantita');

    if (variante && variante.id) {
        inputId.value = variante.id;
        displayPrezzo.textContent = variante.prezzo.toFixed(2) + "€";
        
        if (variante.disponibilita > 0) {
            btnCarrello.disabled = false;
            btnCarrello.textContent = "Aggiungi al carrello";
            btnCarrello.style.background = "";
            infoDisp.textContent = `Disponibile (${variante.disponibilita} pezzi)`;
            infoDisp.style.color = "green";
            inputQuantita.max = variante.disponibilita;
            if (parseInt(inputQuantita.value) > variante.disponibilita) {
                inputQuantita.value = variante.disponibilita;
            }
        } else {
            btnCarrello.disabled = true;
            btnCarrello.textContent = "Esaurito";
            btnCarrello.style.background = "#888";
            infoDisp.textContent = "Prodotto esaurito per questa combinazione.";
            infoDisp.style.color = "red";
            inputId.value = "";
        }
    } else {
        inputId.value = "";
        btnCarrello.disabled = true;
        btnCarrello.textContent = "Non disponibile";
        btnCarrello.style.background = "#888";
        infoDisp.textContent = "Questa combinazione di varianti non è disponibile.";
        infoDisp.style.color = "orange";
    }
}

function validaAggiunta() {
    const inputId = document.getElementById('idVarianteInput').value;
    if (!inputId) {
        alert("Per favore, seleziona una combinazione valida di Taglia e Colore.");
        return false;
    }
    return true;
}

document.addEventListener("DOMContentLoaded", () => {
    const form = document.querySelector('.form-aggiunta-carrello');
    const btnCarrello = document.getElementById('btnAggiungiCarrello');

    if (form) {
        form.addEventListener('submit', function(event) {
            event.preventDefault(); 

            if (!validaAggiunta() || btnCarrello.disabled || btnCarrello.classList.contains('loading')) {
                return; 
            }

            const testoOriginale = btnCarrello.textContent;

            btnCarrello.classList.add('loading');
            btnCarrello.disabled = true;
            btnCarrello.innerHTML = 'Inserimento...';

            const formData = new FormData(form);
            const dataUrlEncoded = new URLSearchParams(formData);

            fetch(form.action, {
                method: 'POST',
                body: dataUrlEncoded,
                headers: { 'Content-Type': 'application/x-www-form-urlencoded' }
            })
            .then(response => {
                if (!response.ok) throw new Error("Errore del server");
                return response.json(); 
            })
            .then(data => {
                if (data.status === "success") {

                    btnCarrello.classList.remove('loading');
                    btnCarrello.style.backgroundColor = "#2ecc71";
                    btnCarrello.innerHTML = 'Aggiunto! ✓';

                    const navCarrelloBtn = document.getElementById('btn-nav-carrello');
                    const badgeCarrello = document.getElementById('badge-carrello');

                    if (navCarrelloBtn) {
  
                        navCarrelloBtn.classList.remove('anim-wobble');
                        void navCarrelloBtn.offsetWidth; 
                        navCarrelloBtn.classList.add('anim-wobble');
                    }

                    if (badgeCarrello) {
                        badgeCarrello.classList.remove('anim-pop');
                        void badgeCarrello.offsetWidth;

                        badgeCarrello.textContent = data.nuovoTotale; 
                        badgeCarrello.classList.add('anim-pop');
                    }

                    setTimeout(() => {
                        btnCarrello.style.backgroundColor = "";
                        btnCarrello.disabled = false;
                        btnCarrello.textContent = testoOriginale;
                    }, 2000);
                }
            })
            .catch(error => {
                console.error("Errore:", error);
                alert("Errore durante l'aggiunta al carrello.");
                btnCarrello.classList.remove('loading');
                btnCarrello.disabled = false;
                btnCarrello.textContent = testoOriginale;
                btnCarrello.style.backgroundColor = "";
            });
        });
    }
});

document.addEventListener("DOMContentLoaded", () => {
    const immaginePrincipale = document.querySelector('.immagine-principale-prodotto img');
    const miniature = document.querySelectorAll('.miniature-immagini img');

    if (immaginePrincipale && miniature.length > 0) {
        miniature.forEach(miniatura => {

            miniatura.addEventListener('click', function() {

                immaginePrincipale.src = this.src;
                immaginePrincipale.alt = this.alt;

                immaginePrincipale.style.opacity = 0.5;
                setTimeout(() => {
                    immaginePrincipale.style.opacity = 1;
                    immaginePrincipale.style.transition = "opacity 0.3s ease-in-out";
                }, 50);
            });
        });
    }
});