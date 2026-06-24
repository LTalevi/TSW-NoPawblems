<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<jsp:include page="fragments/head.jsp"/>
		<link rel="stylesheet" href="<%= request.getContextPath() %>/stylesheets/StileChiSiamo.css" type="text/css">
		
		<title>Chi Siamo</title>
	</head>
	
	<body>
		<jsp:include page="/fragments/Nav.jsp"/>
		<jsp:include page="/fragments/GuidaAlleTaglie.jsp"/>
	
		<main class="wrapper">
			<div class="Titolo">
				<h2>Team VuoLevi</h2>
				<h3>Due Anime, Un Solo Destino</h3>
			</div>
			
			<div class="Testo">
				<blockquote>
					Nelle ere in cui il codice era oscuro e i server vacillavano sotto il peso del caos, 
					dalle nebbie dell’incertezza accademica sorse una stirpe di visionari. Il loro nome riecheggia 
					nei corridoi del silicio: VuoLevi.
				</blockquote>
				
				<p>
					Non eravamo una legione. Non eravamo una corporazione. Eravamo solo in due.
					Due menti forgiate nel fuoco degli esami più spietati, uniti da un patto indissolubile sigillato col sangue e con la caffeina. Quando il mondo accademico chiedeva un semplice progetto universitario, il Team VuoLevi ha risposto scatenando una rivoluzione digitale.
				</p>
				
				<p>
					Mentre uno brandiva la spada del Front-End, plasmando pixel e domando fogli di stile ribelli, l'altro governava l'oscuro regno del Back-End, architettando database impenetrabili e invocando Servlet dal profondo dei server Tomcat.
					Siamo i navigatori dell'ignoto, i risolutori di bug impossibili, gli architetti del domani. Questo sito non è un semplice elaborato d'esame. È il nostro monumento. È l'eredità del Team VuoLevi.
				</p>
				
				<q>
					Noi siamo in due. Il codice è infinito. La lode è l'unica via.
				</q>
			</div>
		</main>
		
		<jsp:include page="/fragments/Footer.jsp"/>
	</body>
</html>