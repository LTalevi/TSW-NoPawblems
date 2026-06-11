<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
	<head>
		<link rel="stylesheet" href="stylesheets/StileVariabili.css" type="text/css">
		<link rel="stylesheet" href="stylesheets/StileLogin.css" type="text/css">
		<link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
		<meta charset="UTF-8">
		<title>Registrazione</title>
	</head>
	
	<body>
		<div class="main-wrapper">
			<header>
				<div class="logo">
					<a href="<%=request.getContextPath()%>/HomeServlet"><img src="img/logo/logo_NoPawblems_esteso.jpeg"/></a>
				</div>
			</header>
		
			<div class="campi-dati">
				<form action="RegistrazioneServlet" method="POST">
					<div class="input-errore-container">
						<div class="input" id="input_nome">
							<label for="nome">Nome:</label>
							<input type="text" name="nome" id="nome" placeholder="Mario" oninput="valida_nome()" required/>
						</div>
							<span id="errore_nome" class="errore"></span>
					</div>
					
					<div class="input-errore-container">
						<div class="input" id="input_cognome">
							<label for="cognome">Cognome:</label>
							<input type="text" name="cognome" id="cognome" placeholder="Rossi" oninput="valida_cognome()" required/>
						</div>
							<span id="errore_cognome" class="errore"></span>
					</div>
					
					<div class="input-errore-container">
						<div class="input-telefono" id="input_telefono">
							<label for="telefono">Telefono:</label>
							
							<div class="prefisso">
								<select name="prefisso">
									<option value="+01">US (+1)</option>
									<option value="+39" selected>IT (+39)</option>
									<option value="+33">FR (+33)</option>
									<option value="+44">UK (+44)</option>
									<option value="+49">DE (+49)</option>
								</select>
							</div>
							
							<div class="telefono">
								<input type="tel" name="telefono" id="telefono" placeholder="1234567890" oninput="valida_telefono()" required/>
							</div>
								<span id="errore_telefono" class="errore"></span>
						</div>
					</div>

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

					<div class="input-errore-container">
						<div class="input" id="input_conferma">
							<label for="conferma_password">Conferma Password:</label>
							<input type="password" name="conferma_password" id="conferma_password" placeholder="password" oninput="valida_conferma()" required/>
						</div>
							<span id="errore_conferma_password" class="errore"></span>
					</div>
					
					<button type="submit" id="registrati" disabled>Registrati</button>
					
					<div class="invito-ad-accedere">
						<p>Hai già un account?</p>
						<a href="Login.jsp">Accedi!</a>
					</div>
				</form>
			</div>
		</div>
		<script src="scripts/ValidazioneDatiRegistrazione.js"></script>
	</body>
</html>