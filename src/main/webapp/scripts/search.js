document.addEventListener("DOMContentLoaded", function() {
	const wrapper = document.querySelector(".search_wrapper");
	const input = document.querySelector(".input_search");
	const results = document.querySelector(".suggestions");
	
	let timer;
	
	function openSearch(){
		clearTimeout(timer);
		wrapper.classList.add("active");

		if (results.innerHTML !== "") {
			results.style.display = "block";
		}
	}
	
	function closeSearch(){
		timer = setTimeout(()=>{
			if(!wrapper.matches(":hover") && document.activeElement !== (input)){
				wrapper.classList.remove("active");
				results.style.display="none";
			}
		}, 300);
	}
	
	function suggestions() {
		const query = input.value.trim();
		const contextPath = input.dataset.context; 
			
		if (query.length < 2) {
			results.innerHTML = "";
			results.style.display = "none";
			return;
		}

		const url = `${contextPath}/SuggerimentiRicerca?query=${encodeURIComponent(query)}`;

		fetch(url)
			.then(response => response.json())
			.then(prodotti => {
				results.innerHTML = ""; 
					
				if (prodotti.length === 0 || prodotti.error) {
					results.style.display = "none";
					return;
				}


				prodotti.forEach(prod => {

					const itemLink = document.createElement("a");
					itemLink.classList.add("suggestion_item");

					itemLink.href = `${contextPath}/DettaglioProdotto?idProdotto=${prod.id}`; 

					const img = document.createElement("img");
					img.classList.add("suggestion_img");
					img.src = `${contextPath}/${prod.immagine}`;
					img.alt = prod.nome;

					const textSpan = document.createElement("span");
					textSpan.classList.add("suggestion_text");
					textSpan.textContent = prod.nome;

					itemLink.appendChild(img);
					itemLink.appendChild(textSpan);

					results.appendChild(itemLink);
				});

				results.style.display = "block";
			})
			.catch(error => console.error("Errore nel recupero dei suggerimenti:", error));
	}
	
	function sendSearch() {
		const query = input.value.trim();
		const contextPath = input.dataset.context;

		if (query.length > 0) {
			window.location.href = `${contextPath}/CatalogoServlet?ricerca=${encodeURIComponent(query)}`;
		}
	}
	
	wrapper.addEventListener("mouseenter", openSearch);
	wrapper.addEventListener("mouseleave", closeSearch);
	
	input.addEventListener("focus", openSearch);
	input.addEventListener("blur", closeSearch);

	input.addEventListener("input", suggestions);
	input.addEventListener("keydown", function(event) {
		if (event.key === "Enter") {
			sendSearch();
		}
	});
	
});