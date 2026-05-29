document.addEventListener("DOMContentLoaded", function() {
    const menu = document.querySelector(".toggle_menu");
    const content = document.getElementById("sidebar_menu");
	
	if (!menu || !content) {
        console.warn("Attenzione: Contenitore o Sidebar non trovati nella pagina.");
        return;
    }
		
    function showMenu() {
        content.classList.add("show");
    }
	
	function hideMenu() {
		setTimeout(() => {
            if (!content.matches(":hover") && !menu.matches(":hover")) {
                content.classList.remove("show");
            }
        }, 100);
	}

	// Gestione del pulsante
	    menu.addEventListener("mouseenter", showMenu);
	    menu.addEventListener("mouseleave", hideMenu);

});