document.addEventListener("DOMContentLoaded", function() {
	const wrapper = document.querySelector(".search_wrapper");
	const input = document.querySelector(".input_search");
	const results = document.querySelector(".suggestions");
	
	let timer;
	
	function openSearch(){
		clearTimeout(timer);
		wrapper.classList.add("active");
	}
	
	function closeSearch(){
		timer = setTimeout(()=>{
			if(!wrapper.matches(":hover") && document.activeElement !== (input)){
				wrapper.classList.remove("active");
				results.style.display="none";
			}
		}, 300);
	}
	
	wrapper.addEventListener("mouseenter", openSearch);
	wrapper.addEventListener("mouseleave", closeSearch);
	
	input.addEventListener("focus", openSearch);
	input.addEventListener("blur", closeSearch);
})