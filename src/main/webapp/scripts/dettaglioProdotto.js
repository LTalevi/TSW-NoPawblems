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