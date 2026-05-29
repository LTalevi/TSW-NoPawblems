let numSlide = 0;
let timer = null;
const track = document.getElementById("slideshow_id");
const slide = document.getElementsByClassName("slide");
const loadingBar = document.getElementById("loading_bar");


function trackUpdate(){
	track.style.transform = `translateX(-${(numSlide*100)/6}%)`;
}

function nextSlide(){
	if (timer) {
			clearTimeout(timer);
	}
	
	numSlide += 1
	
	if(numSlide >= slide.length){ 
		numSlide = 0
	}
	
	trackUpdate();
	startTimer();
}

function startTimer() {
		timer = setTimeout(nextSlide, 8000); 
		resetLoadingBar();
}

function slideChange(n){
	if (timer) {
			clearTimeout(timer);
	}
		
	numSlide += n
	
	if(numSlide >= slide.length){ 
		numSlide = 0
	}
	if (numSlide < 0) { 
		numSlide = slide.length - 1; 
	}
	
	trackUpdate();
	startTimer();
}

function resetLoadingBar(){
	loadingBar.style.transition = "none";
	loadingBar.style.width = "0%";
	loadingBar.offsetHeight;
	loadingBar.style.transition = "width 8s linear";
	loadingBar.style.width = "100%";
}

startTimer();
