let form = null;

function apriFinestra(btn){
	form = btn.closest(".formRimozione");
	
	const finestra = document.getElementById("overlay-finestra");
	if(finestra){
		finestra.style.display = "flex";
	}
}

function chiudiFinestra(){
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
           if(form) {
              form.submit(); 
            }
        });
    }
});