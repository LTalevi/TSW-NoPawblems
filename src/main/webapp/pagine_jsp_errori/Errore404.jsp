<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<link rel="stylesheet" href="../stylesheets/Error.css" type="text/css">
	<link rel="stylesheet" href="../stylesheets/StileHeader.css" type="text/css">
	<link rel="stylesheet" href="../stylesheets/StileFooter.css" type="text/css">
	<link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
	
	<title>Pagina non trovata</title>
</head>

<body>
	<nav>
		<button class="toggle_menu" aria-label="Apri menu">
			<i class="material-icons">menu</i>	
		</button>
	
		<a href="../Home.jsp" class="logo_navbar" aria-label="Torna alla homepage">
			<img src="../img/logo/logo_NoPawblems_esteso.jpeg" alt="Logo NoPawblems">
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

	<div class="main_content_wrapper">
		<div class="immagine_errore">
			<img src="../img/errori/errore_404.png" alt="Immagine errore 404">
		</div>
		
		<div class="testo_bottone_home">
			<div class="testo_errore">
				<h1>Errore 404 (Page not Found) - Ops! sembra che la pagina non sia stata trovata :(</h1>
				<p>
					La pagina potrebbe essere stata spostata o non esistere più.
				</p>
			</div>
			
			<button class="bottone_home">Torna alla HomePage</button>
		</div>
	</div>
	
	<footer></footer>
</body>
</html>