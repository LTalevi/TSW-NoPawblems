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
	<jsp:include page="Header.jsp"/>
	
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
						<h3></h3>
						<p></p>
					</div>
					
					<button class="bottone-homepage">Acquista</button>
				</div>
				
				<div class="immagine-laterale">
					<img src="img/Header_img/Header_img_1.png" alt="Immagine prodotto in evidenza">
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
					<img src="img/Header_img/Header_img_2.png" alt="Immagine prodotto in promozione">
				</div>
				
				<div class="testo-laterale">
					<div class="descrizione-prodotto-homepage">
						<h3></h3>
						<p></p>
					</div>
					
					<button class="bottone-homepage">Acquista</button>
				</div>
				
				<button class="arrow_button next" aria-label="prodotto successivo">
				    <i class="material-icons">keyboard_arrow_right</i>
				</button>
				
			</div>
		</section>
	</main>
		
	<jsp:include page="Footer.jsp"/>
</body>
</html>