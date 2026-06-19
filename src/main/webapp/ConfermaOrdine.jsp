<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>
	<head>
		<link rel="stylesheet" href="<%= request.getContextPath() %>/stylesheets/main.css" type="text/css">
		<link rel="stylesheet" href="<%= request.getContextPath() %>/stylesheets/StileSuccesso.css" type="text/css">
		<meta charset="UTF-8">
		<title>Ordine Completato</title>
	</head>
	
	<body>
		<jsp:include page="/fragments/Nav.jsp"/>
		<jsp:include page="/fragments/GuidaAlleTaglie.jsp"/>
	
		<div class="main_content_wrapper">
			<div class="immagine_successo">
				<img src="<%= request.getContextPath() %>/img/TransazioneCompletata.png" alt="TransazioneCompletata">
			</div>
			
			<div class="testo_bottone_home">
				<div class="testo_successo">
					<h1>L'ordine è andato a buon fine! :D</h1>
					<p>
						Il tuo ordine si trova in elaborazione e sarà spedito a breve!
					</p>
				</div>
				
				<a href="<%= request.getContextPath() %>/HomeServlet">
					<button class="bottone_home">Torna alla HomePage</button>
				</a>
			</div>
		</div>
	
	<jsp:include page="/fragments/Footer.jsp"/>
	</body>
</html>