<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

<head>
	<meta charset="UTF-8">
	<link rel="stylesheet" href="stylesheets/StileHeader.css" type="text/css">
	<link rel="stylesheet" href="stylesheets/StileFooter.css" type="text/css">
	<link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
	
	<title>NoPawblems</title>
</head>

<body>
	
	<header>
		<nav>
			<button class="toggle_menu" aria-label="Apri menu">
				<i class="material-icons">menu</i>	
			</button>
		
			<div class="logo_navbar">
				<img src="<%=urlLogoNavbar //inserire logo navbar dal database%>" alt="Logo NoPawblems">
			</div>
			
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
			<img class="slide" src="img/Header img/Header_img_1.png" alt="Immagine_slideshow_1">	  	
			<img class="slide" src="img/Header img/Header_img_2.png" alt="Immagine_slideshow_2">	  	
			<img class="slide" src="img/Header img/Header_img_3.png" alt="Immagine_slideshow_3">	  	
			<img class="slide" src="img/Header img/Header_img_4.png" alt="Immagine_slideshow_4">	  	
			<img class="slide" src="img/Header img/Header_img_5.png" alt="Immagine_slideshow_5">	  	
			<img class="slide" src="img/Header img/Header_img_6.png" alt="Immagine_slideshow_6">	  	
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
	
	<main class="main-content-wrapper">
	
		<section class="best-sellers">
			<div class="titolo-blocco">
				<h2>Best-Sellers</h2>
			</div>
			
			<div class="blocco-immagine-testo">
			
				<button class="arrow_button prev" aria-label="prodotto precedente">
	    			<i class="material-icons">keyboard_arrow_left</i>
	  			</button>
	  
				<div class="testo-laterale">
					<div class="descrizione-prodotto-homepage">
						<h3><%=titoloProdotto //recuperare titolo dal database %></h3>
						<p><%=descrizioneProdotto //recuperare descrizione dal database %></p>
					</div>
					
					<button class="bottone-homepage">Acquista</button>
				</div>
				
				<div class="immagine-laterale">
					<img src="<%=urlImmagineBestseller //script per prendere immagine da db %>" alt="Immagine prodotto in evidenza">
				</div>
				
				<button class="arrow_button next" aria-label="prodotto successivo">
				    <i class="material-icons">keyboard_arrow_right</i>
				</button>
							
			</div>
		</section>
		
		<section class="promo">
			<div class="titolo-blocco">
				<h2>Promozioni</h2>
			</div>
			
			<div class="blocco-immagine-testo">
			
				<button class="arrow_button prev" aria-label="prodotto precedente">
	    			<i class="material-icons">keyboard_arrow_left</i>
	  			</button>
	  			
				<div class="immagine-laterale">
					<img src="<%=urlImmaginePromo //script per prendere immagine da db %>" alt="Immagine prodotto in promozione">
				</div>
				
				<div class="testo-laterale">
					<div class="descrizione-prodotto-homepage">
						<h3><%=titoloProdotto //recuperare titolo dal database %></h3>
						<p><%=descrizioneProdotto //recuperare descrizione dal database %></p>
					</div>
					
					<button class="bottone-homepage">Acquista</button>
				</div>
				
				<button class="arrow_button next" aria-label="prodotto successivo">
				    <i class="material-icons">keyboard_arrow_right</i>
				</button>
				
			</div>
		</section>
	
	
	</main>
	
	<footer>
		<div class="footer_wrapper">
			<div class="logo_footer">
				<img src="<%=urlLogoFooter //prendere immagine logo footer dal database %>" alt="Logo NoPawblems">
			</div>
				
			<section class="links_footer">
				<h3>Links utili:</h3>
				<ul class="lista_links">
					<li>
						<i class="material-icons icona_zampa">pets</i>
						<a href="https://github.com/LTalevi">Account github Luigi Talevi</a>
					</li>
					<li>
						<i class="material-icons icona_zampa">pets</i>
						<a href="https://github.com/Gianpancrazioy">Account github Vuolo Antonio</a>
					</li>
				</ul>
			</section>
			
			<section class="copyright">
				<div class="logo_copy">
					<i class="material-icons">copyright</i>
				</div>
				
				<div class="testo_copy">
					<em>Tutti i diritti sono riservati.</em>
				</div>
			</section>
		</div>
	</footer>
	<script src="scripts/header.js"></script>
</body>
</html>