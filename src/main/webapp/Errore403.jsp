<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<jsp:include page="fragments/head.jsp"/>
	<link rel="stylesheet" href="<%= request.getContextPath() %>/stylesheets/Error.css" type="text/css">
	
	<title>Forbidden</title>
</head>

<body>
	<jsp:include page="/fragments/Nav.jsp"/>
	<jsp:include page="/fragments/GuidaAlleTaglie.jsp"/>

	<div class="main_content_wrapper">
		<div class="immagine_errore">
			<img src="<%= request.getContextPath() %>/img/errori/errore_403.png" alt="Immagine errore 403">
		</div>
		
		<div class="testo_bottone_home">
			<div class="testo_errore">
				<h1>Errore 403 (Forbidden) - Ci dispiace, ma non puoi accedere a questa pagina :(</h1>
				<p>
					Sembra tu non possegga i permessi specifici per accedere a questa risorsa.
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