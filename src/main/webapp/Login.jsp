<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
	<head>
		<link rel="stylesheet" href="stylesheets/StileVariabili.css" type="text/css">
		<link rel="stylesheet" href="stylesheets/StileLogin.css" type="text/css">
		<link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
		<meta charset="UTF-8">
		<title>Login</title>
	</head>
	
	<body>
		<div class="main-wrapper">
			<header>
				<div class="logo">
					<a href="<%=request.getContextPath()%>/HomeServlet"><img src="img/logo/logo_NoPawblems_esteso.jpeg"/></a>
				</div>
			</header>
			
			<div class="campi-dati">
				<form action="LoginServlet" method="POST">
					<div class="input-errore-container">
						<div class="input" id="input_email">
							<label for="email">E-mail:</label>
							<input type="email" name="email" id="email" placeholder="mario.rossi@gmail.com" oninput="valida_email()" required/>
						</div>
							<span id="errore_email" class="errore"></span>
					</div>
					
					<div class="input-errore-container">
						<div class="input" id="input_password">
							<label for="password">Password:</label>
							<input type="password" name="password" id="password" placeholder="password" oninput="valida_password()" required/>
						</div>
							<span id="errore_password" class="errore"></span>
					</div>
					
					<button type="submit" id="accedi" disabled>Accedi</button>
					
					<div class="invito-a-registrarsi">
						<p>Non hai un account?</p>
						<a href="Registrazione.jsp">Registrati!</a>
					</div>
				</form>
			</div>
		</div>
		<script src="scripts/ValidazioneDatiLogin.js"></script>
	</body>
</html>