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
	
	document.getElementById("boxFormPrincipale").style.border = "2px solid #3498db";
	document.getElementById("titoloForm").innerText = "Modifica Prodotto #" + id;
	document.getElementById("btnSubmit").innerText = "Salva Modifiche";
	document.getElementById("btnSubmit").style.background = "#3498db";
	document.getElementById("btnAnnulla").style.display = "inline-block";

	document.getElementById("azioneForm").value = "modifica";
	document.getElementById("idProdottoForm").value = id;
	
	document.getElementById("nomeForm").value = nome;
	document.getElementById("descrizioneForm").value = desc;
	document.getElementById("idCategoriaForm").value = idCat;
	document.getElementById("prezzoForm").value = prezzo;
	document.getElementById("disponibilitaForm").value = disp;
	document.getElementById("ivaForm").value = iva;
	
	document.getElementById("immagineForm").removeAttribute("required");
	
	document.getElementById("blocco-inserimento-varianti").style.display = "none";

	document.querySelectorAll('#blocco-inserimento-varianti input[type="checkbox"]').forEach(cb => {
	        cb.disabled = true;
	    });
	
	let bloccoModifica = document.getElementById("blocco-modifica-varianti"); // Corretto ID come da tua JSP
	    bloccoModifica.style.display = "flex";
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
	
	document.getElementById("boxFormPrincipale").style.border = "none";
	document.getElementById("titoloForm").innerText = "Aggiungi Nuovo Prodotto Base + Varianti";
	document.getElementById("btnSubmit").innerText = "Salva e Genera Prodotto";
	document.getElementById("btnSubmit").style.background = "#2ecc71";
	document.getElementById("btnAnnulla").style.display = "none";
	document.getElementById("immagineForm").setAttribute("required", "required");

	document.getElementById("insTaglia1").disabled = false;
	document.getElementById("insTaglia2").disabled = false;
	document.getElementById("insColore").disabled = false;
	document.getElementById("insHex").disabled = false;
	document.getElementById("bloccoVariantiInserimento").style.display = "grid";
	
	let bloccoModifica = document.getElementById("bloccoVariantiModifica");
	bloccoModifica.style.display = "none";
	bloccoModifica.innerHTML = "";
}