document.addEventListener("DOMContentLoaded", function() {
    const menu = document.querySelector(".toggle_filter_button");
    const content = document.getElementById("toggle_filtri");
	
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

    menu.addEventListener("mouseenter", showMenu);
    menu.addEventListener("mouseleave", hideMenu);
    content.addEventListener("mouseenter", showMenu);
    content.addEventListener("mouseleave", hideMenu);

});