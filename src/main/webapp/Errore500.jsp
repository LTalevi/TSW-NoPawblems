<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<link rel="stylesheet" href="stylesheets/main.css" type="text/css">
	<link rel="stylesheet" href="stylesheets/Error.css" type="text/css">
	
	<title>Pagina non trovata</title>
</head>

<body>
	<jsp:include page="/fragments/Nav.jsp"/>
	<jsp:include page="/fragments/GuidaAlleTaglie.jsp"/>

	<div class="main_content_wrapper">
		<div class="immagine_errore">
			<img src="img/errori/errore_500.png" alt="Immagine errore 500">
		</div>
		
		<div class="testo_bottone_home">
			<div class="testo_errore">
				<h1>Errore 500 (Internal Server Error) - Oh no! Sembra che ci sia un problema sul server :(</h1>
				<p>
					Non è stato possibile elaborare la richiesta.
				</p>
			</div>
			
			<a href="<%= request.getContextPath() %>/HomeServlet">
				<button class="bottone_home">Torna alla HomePage</button>
			</a>
		</div>
	</div>
	
	<footer></footer>
</body>
</html>