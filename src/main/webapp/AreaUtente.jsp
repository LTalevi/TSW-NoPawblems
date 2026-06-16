<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="model.utente.Utente" %>
<%@ page import="model.indirizzo.Indirizzo" %>
<%@ page import="model.ordine.Ordine" %>

<%
	// --- BLOCCO DI TEST TEMPORANEO ---
	// Creiamo la sessione di test
	HttpSession sessione = request.getSession(true); 

	// Creiamo un utente finto e lo spingiamo in SESSIONE (così lo legge anche la Nav)
	Utente utente = new Utente();
	utente.setNome("Eustachio");
	utente.setCognome("Rossi");
	utente.setEmail("mario.rossi@gmail.com");
	utente.setTelefono("1234567890");
	sessione.setAttribute("utente", utente);
	
	// Creiamo degli indirizzi finti per testare la sezione Indirizzi
	List<Indirizzo> indirizzi = new ArrayList<>();
	Indirizzo ind1 = new Indirizzo();
	ind1.setVia("Via Roma 10");
	ind1.setCitta("Milano");
	ind1.setProvincia("MI");
	ind1.setCap("20100");
	ind1.setNazione("Italia");
	indirizzi.add(ind1);
	request.setAttribute("indirizzi", indirizzi);

	// Creiamo una lista vuota di ordini per evitare il crash del toString()
	List<Ordine> ordini = new ArrayList<>();
	request.setAttribute("ordini", ordini);
	// ---------------------------------
%>

<!DOCTYPE html>
<html>
	<head>
		<link rel="stylesheet" href="stylesheets/main.css" type="text/css">
		<link rel="stylesheet" href="stylesheets/StileAreaUtente.css" type="text/css">
		
		<meta charset="UTF-8">
		<title>Profilo Utente</title>
	</head>
	
	<body>
		<%
			if(utente == null || sessione == null){
				response.sendRedirect(request.getContextPath() + "/RegistrazioneServlet");
				return;
			}
			else{
		%>
		
		<jsp:include page="fragments/Nav.jsp"/>
			
			<main class="wrapper">
				<div class="saluto">
					<h1>Ciao, <span class="gradiente"><%=utente.getNome() %></span>!</h1>
				</div>
				
				<div class="contenitore">
					
					
					<div class="dati">
						<div class="campo_titolo">
							<div class="titolo">
								<h2>Dati Personali</h2>	
							</div>
							
							<button class="bottone_modifica">
								<i class="material-icons">edit</i>
							</button>
						</div>
						
						<div class="campo_testo">
							<div class="testo">
								<label for="nome">Nome:</label>
								<p><%=utente.getNome() %></p>
							</div>
							
							<div class="testo">
								<label for="cognome">Cognome:</label>
								<p><%=utente.getCognome() %></p>
							</div>
							
							<div class="testo">
								<label for="email">E-mail:</label>
								<p><%=utente.getEmail() %></p>
							</div>
	
							<div class="testo">
								<label for="telefono">Telefono:</label>
								<p><%=utente.getTelefono() %></p>					
							</div>
						</div>
					</div>
	
					<div class="indirizzi">
						<div class="campo_titolo">
							<div class="titolo">
								<h2>Indirizzi Salvati</h2>	
							</div>
							
							<button class="bottone_modifica">
								<i class="material-icons">edit</i>
							</button>
						</div>
					
					<%	List<Indirizzo> indirizzib = (List<Indirizzo>) request.getAttribute("indirizzi");
					
						if(indirizzi == null || indirizzi.isEmpty()){
							%>
							<p class="n/a" style="text-align:center; width:100%; margin:auto;">Non ci sono indirizzi salvati.</p>
							<%
							}
						else{
							int n = 0;
							
							for(Indirizzo i : indirizzi){
								String via = i.getVia();
								String provincia = i.getProvincia();
								String citta = i.getCitta();
								String cap = i.getCap();
								String nazione = i.getNazione();
								n++;
							%>
							
							<div class="campo_testo">
								<div class="testo">
									<label for="indirizzo">Indirizzo <%=n %>:</label>
									<p><%=nazione%>, <%=citta%> (<%=provincia %>), <%=cap %>, <%=via %></p>
								</div>
							</div>
							
							<%
								}
							}	
							%>	
					</div>		
					
					<div class="ordini">
						<div class="campo_titolo">
							<div class="titolo">
								<h2>Ordini Effettuati</h2>	
							</div>
						</div>
						
						<%	List<Ordine> ordinic = (List<Ordine>) request.getAttribute("ordini");
					
						if(ordini == null || ordini.isEmpty()){
							%>
							<p class="n/a" style="text-align:center; width:100%; margin:auto;">Non ci sono ordini registrati.</p>
							<%
							}
						else{
							int n = 0;
							
							for(Ordine o : ordini){
								/*String via = o.getViaSpedizione();
								String provincia = i.getProvincia();
								String citta = i.getCitta();
								String cap = i.getCap();
								String nazione = i.getNazione();*/
								n++;
							%>
							
							<div class="campo_testo">
								<div class="testo">
									<label for="ordine">Ordine <%=n %>:</label>
									<p>pene</p>
								</div>
							</div>
							
							<%
								}
							}	
							%>	
					</div>
				</div>
			</main>
		
			<%
			}
			%>
		<jsp:include page="fragments/Footer.jsp"/>
	</body>
</html>