<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<jsp:include page="fragments/head.jsp"/>
	<link rel="stylesheet" href="<%= request.getContextPath() %>/stylesheets/Error.css" type="text/css">
	
	<title>Page not Found</title>
</head>

<body>
	<jsp:include page="/fragments/Nav.jsp"/>
	<jsp:include page="/fragments/GuidaAlleTaglie.jsp"/>

	<div class="main_content_wrapper">
		<div class="immagine_errore">
			<img src="<%= request.getContextPath() %>/img/errori/errore_404.png" alt="Immagine errore 404">
		</div>
		
		<div class="testo_bottone_home">
			<div class="testo_errore">
				<h1>Errore 404 (Page not Found)</h1>
				<h2>Ops! sembra che la pagina non sia stata trovata :(</h2>
				<p>
					La pagina potrebbe essere stata spostata o non esistere più.
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