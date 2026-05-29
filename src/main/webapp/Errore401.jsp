<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<link rel="stylesheet" href="stylesheets/Error.css" type="text/css">
	<link rel="stylesheet" href="stylesheets/StileHeader.css" type="text/css">
	<link rel="stylesheet" href="stylesheets/StileFooter.css" type="text/css">
	<link rel="stylesheet" href="stylesheets/StileMenu.css" type="text/css">
	<link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
	
	<title>Pagina non trovata</title>
</head>

<body>
	<jsp:include page="Nav.jsp"/>

	<div class="main_content_wrapper">
		<div class="immagine_errore">
			<img src="img/errori/errore_401.png" alt="Immagine errore 401">
		</div>
		
		<div class="testo_bottone_home">
			<div class="testo_errore">
				<h1>Errore 401 (Unauthorized) - Ci dispiace, ma non puoi accedere alla risorsa :(</h1>
				<p>
					Sembra che le credenziali inserite non siano valide o la sessione potrebbe essere scaduta.
				</p>
			</div>
			
			<a href="Home.jsp">
				<button class="bottone_home">Torna alla HomePage</button>
			</a>
		</div>
	</div>
	
	<footer></footer>
</body>
</html>