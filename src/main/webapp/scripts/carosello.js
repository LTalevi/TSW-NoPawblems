document.addEventListener("DOMContentLoaded", function() {
    
    const caroselli = document.querySelectorAll(".blocco-immagine-testo");

    caroselli.forEach(carosello => {
        const track = carosello.querySelector(".slider-prodotti-track");
        const prodotti = carosello.querySelectorAll(".slide-prodotto");
        const arrowPrev = carosello.querySelector(".arrow_button.prev");
        const arrowNext = carosello.querySelector(".arrow_button.next");

        if (!track || prodotti.length === 0) return;

        let numSlide = 0;
        const totalSlides = prodotti.length;

        function aggiornaCarosello() {
            track.style.transform = `translateX(-${numSlide * 100}%)`;
        }

        if (arrowNext) {
            arrowNext.addEventListener("click", () => {
                numSlide++;

                if (numSlide >= totalSlides) {
                    numSlide = 0; 
                }
                aggiornaCarosello();
            });
        }

        if (arrowPrev) {
            arrowPrev.addEventListener("click", () => {
                numSlide--;
				
                if (numSlide < 0) {
                    numSlide = totalSlides - 1; 
                }
                aggiornaCarosello();
            });
        }
    });
});