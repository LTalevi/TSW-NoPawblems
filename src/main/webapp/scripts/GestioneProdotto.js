document.addEventListener("DOMContentLoaded", function () {
    const checkboxesColore = document.querySelectorAll('.griglia-checkbox.colori input[type="checkbox"]');
    checkboxesColore.forEach(checkbox => {
        checkbox.addEventListener('change', aggiornaHexNascosti);
    });
	
	const bottoniModifica = document.querySelectorAll('.bottone-modifica');
	bottoniModifica.forEach(bottone => {
		bottone.addEventListener('click', function() {

	   		const id = this.getAttribute('data-id');
	        const nome = this.getAttribute('data-nome');
	        const desc = this.getAttribute('data-descrizione');
	        const idCat = this.getAttribute('data-categoria');
         	const prezzo = this.getAttribute('data-prezzo');
	       	const disp = this.getAttribute('data-disponibilita');
	        const iva = this.getAttribute('data-iva');
	            
	        let varianti = [];
	        try {
	        	varianti = JSON.parse(this.getAttribute('data-varianti'));
	        } catch(e) {
                console.error("Errore nel parsing delle varianti JSON:", e);
	   		}

	    	avviaModifica(id, nome, desc, idCat, prezzo, disp, iva, varianti);
		});
	});		
});

function aggiornaHexNascosti() {
    const container = document.getElementById('hidden-hex-container');
    if (!container) return;

    container.innerHTML = '';
    const coloriSelezionati = document.querySelectorAll('.griglia-checkbox.colori input[type="checkbox"]:checked');

    coloriSelezionati.forEach(cb => {
        const hexValue = cb.getAttribute('data-hex');

        const hiddenInput = document.createElement('input');
        hiddenInput.type = 'hidden';
        hiddenInput.name = 'coloreHex';
        hiddenInput.value = hexValue;
        
        container.appendChild(hiddenInput);
    });
}

function apriModificaProdotto(event) {
    event.preventDefault();
    const bottoneCliccato = event.currentTarget;
    const cardProdotto = bottoneCliccato.closest('.prodotto');
    const pannelloModifiche = cardProdotto.querySelector('.modifiche');
    
    if (pannelloModifiche.classList.contains('attivo')) {
        pannelloModifiche.classList.remove('attivo');
        pannelloModifiche.classList.add('nonAttivo');
        pannelloModifiche.style.display = 'none'; 
    } else {
        pannelloModifiche.classList.remove('nonAttivo');
        pannelloModifiche.classList.add('attivo');
        pannelloModifiche.style.display = 'block'; 
    }
}

function avviaModifica(id, nome, desc, idCat, prezzo, disp, iva, varianti) {
    document.getElementById('boxFormPrincipale').scrollIntoView({ behavior: 'smooth', block: 'start' });
	
    document.getElementById("titoloForm").innerText = "Modifica Prodotto #" + id;
    document.getElementById("btnSubmit").innerText = "Salva Modifiche";
    document.getElementById("btnSubmit").style.background = "#3498db";
    document.getElementById("btnAnnulla").style.display = "inline-block";

    document.getElementById("azioneForm").value = "modifica";
    document.getElementById("idProdottoForm").value = id;

    document.getElementById("nomeForm").value = nome;
    document.getElementById("descrizioneForm").value = desc;
	const radioCane = document.getElementById("catCane");
    const radioGatto = document.getElementById("catGatto");
    
    if (idCat === "1" && radioCane) {
        radioCane.checked = true;
    } else if (idCat === "2" && radioGatto) {
        radioGatto.checked = true;
    }
    document.getElementById("prezzoForm").value = prezzo;
    document.getElementById("disponibilitaForm").value = disp;
    document.getElementById("ivaForm").value = iva;

    document.getElementById("immagineForm").removeAttribute("required");
    document.getElementById("blocco-inserimento-immagine").style.setProperty("display", "none", "important");

    document.getElementById("blocco-inserimento-varianti").style.display = "none";
    document.getElementById('hidden-hex-container').innerHTML = '';
    document.querySelectorAll('#blocco-inserimento-varianti input[type="checkbox"]').forEach(cb => {
        cb.disabled = true;
        cb.checked = false;
    });
		
    let bloccoModifica = document.getElementById("blocco-modifica-varianti");
    bloccoModifica.style.display = "flex";
    bloccoModifica.style.flexDirection = "column";
    bloccoModifica.style.gap = "10px";
    bloccoModifica.innerHTML = '<label class="grassetto" style="color: #3498db; margin-bottom: 5px;">Modifica Varianti Esistenti</label>';
	    
    varianti.forEach(function(v) {
        bloccoModifica.innerHTML += `
            <div style="display: flex; gap: 10px; background: rgba(0,0,0,0.03); padding: 10px; border-radius: 5px; align-items: center;">
                <input type="hidden" name="idVariante" value="${v.id}">
                <input type="text" name="taglia" value="${v.taglia}" placeholder="Taglia" style="flex:1;" required>
                <input type="text" name="colore" value="${v.colore}" placeholder="Colore" style="flex:1;" required>
                <input type="text" name="coloreHex" value="${v.hex}" placeholder="Hex" style="flex:1;" required>
           </div>
        `;
    });
}

function annullaModifica() {
    document.getElementById("azioneForm").value = "inserisci";
    document.getElementById("idProdottoForm").value = "";
    
    document.getElementById("formProdotto").reset();
    
    document.getElementById("titoloForm").innerText = "Aggiungi Nuovo Prodotto";
    document.getElementById("btnSubmit").innerText = "Salva e Genera Prodotto";
    document.getElementById("btnSubmit").style.background = "#2ecc71";
    document.getElementById("btnAnnulla").style.display = "none";

    document.getElementById("immagineForm").setAttribute("required", "required");
    document.getElementById("blocco-inserimento-immagine").style.removeProperty("display");

    document.querySelectorAll('#blocco-inserimento-varianti input[type="checkbox"]').forEach(cb => {
        cb.disabled = false;
        cb.checked = false;
    });
    document.getElementById("blocco-inserimento-varianti").style.display = "block";
    
    let bloccoModifica = document.getElementById("blocco-modifica-varianti");
    bloccoModifica.style.display = "none";
    bloccoModifica.innerHTML = "";
    document.getElementById('hidden-hex-container').innerHTML = '';
}