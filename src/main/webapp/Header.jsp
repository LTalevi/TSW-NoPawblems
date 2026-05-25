<body>
	<header>
		<nav>
			<button class="toggle_menu" aria-label="Apri menu">
				<i class="material-icons">menu</i>	
			</button>
		
			<a href="Home.jsp" class="logo_navbar" aria-label="Torna alla homepage">
				<img src="img/logo/logo_NoPawblems_esteso.jpeg" alt="Logo NoPawblems">
			</a>
			
			<div class="sezioni_navbar">
					<button class="Cerca" aria-label="Cerca">
						<i class="material-icons">search</i>
					</button>
					<button class="Profilo" aria-label="Apri profilo">
						<i class="material-icons">account_circle</i>	
					</button>
				
					<button class="Carrello" aria-label="Apri carrello">
						<i class="material-icons">shopping_cart</i>	
					</button>
			</div>
			
		</nav>
		
		<button class="arrow_button_header prev" aria-label="prodotto precedente" onclick="slideChange(-1)">
	    	<i class="material-icons">keyboard_arrow_left</i>
	  	</button>
	  			
	  	<div id="slideshow_id" class="slideshow">
			<img class="slide" src="img/Header_img/Header_img_1.png" alt="Immagine_slideshow_1">	  	
			<img class="slide" src="img/Header_img/Header_img_2.png" alt="Immagine_slideshow_2">	  	
			<img class="slide" src="img/Header_img/Header_img_3.png" alt="Immagine_slideshow_3">	  	
			<img class="slide" src="img/Header_img/Header_img_4.png" alt="Immagine_slideshow_4">	  	
			<img class="slide" src="img/Header_img/Header_img_5.png" alt="Immagine_slideshow_5">	  	
			<img class="slide" src="img/Header_img/Header_img_6.png" alt="Immagine_slideshow_6">	  	
	  	</div>
	
		<button class="arrow_button_header next" aria-label="prodotto successivo" onclick="slideChange(1)">
		    <i class="material-icons">keyboard_arrow_right</i>
		</button>
		
		<div class="slide_dots">
		
		</div>
		
		<div class="loading_bar_container">
			<div id="loading_bar" class="loading_bar"></div>
		</div>
	</header>
	
	<script src="scripts/header.js"></script>
</body>
</html>