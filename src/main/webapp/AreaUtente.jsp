<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="model.utente.Utente" %>
<%@ page import="model.indirizzo.Indirizzo" %>
<%@ page import="model.ordine.Ordine" %>

<%@ page import="java.time.LocalDateTime" %>
<%@ page import="java.time.format.DateTimeFormatter" %>

<%
	HttpSession sessione = request.getSession(false);
	Utente utente = null;

	if(sessione != null){	
		utente = (Utente) sessione.getAttribute("utente");
	}
		
	if(utente == null){
		response.sendRedirect(request.getContextPath() + "/RegistrazioneServlet");
		return;
	}
	
	List<Indirizzo> indirizzi = (List<Indirizzo>) request.getAttribute("indirizzi");
	List<Ordine> ordini = (List<Ordine>) request.getAttribute("ordini");
%>

<!DOCTYPE html>
<html>
	<head>
		<link rel="stylesheet" href="<%= request.getContextPath() %>/stylesheets/main.css" type="text/css">
		<link rel="stylesheet" href="<%= request.getContextPath() %>/stylesheets/StileAreaUtente.css" type="text/css">
		
		<meta charset="UTF-8">
		<title>Profilo Utente</title>
	</head>
	
	<body>
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
					
					<%	
					
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
						
						<%	
					
						if(ordini == null || ordini.isEmpty()){
							%>
							<p class="n/a" style="text-align:center; width:100%; margin:auto;">Non ci sono ordini registrati.</p>
							<%
							}
						else{
							int n = 0;
							DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
							
							for(Ordine o : ordini){
							    String viaSpedizione = o.getViaSpedizione();
							    String cittaSpedizione = o.getCittaSpedizione();
							    String capSpedizione = o.getCapSpedizione();
							    String provinciaSpedizione = o.getProvinciaSpedizione();
							    String nazioneSpedizione = o.getNazioneSpedizione();
							    
							    String data = (o.getDataOrdine() != null) ? o.getDataOrdine().format(formatter) : "N/D";
							    
							    String stato = o.getStato();
							    float totale = o.getTotale();
							    String numeroFattura = o.getNumeroFattura();
							    n++;
							%>
							
							<div class="campo_testo">
								<div class="testo">
									<label for="ordine">Ordine <%=n %>:</label>
									<p><span class="grassetto">Ordine spedito in data:</span><br/> <%=data %><br/><br/>
									<span class="grassetto">Indirizzo di Spedizione:</span><br/> <%=nazioneSpedizione%>,
									 <%=cittaSpedizione%> (<%=provinciaSpedizione %>), <%=capSpedizione %>, <%=viaSpedizione %></p>
								</div>
							</div>
							
							<%
								}
							}	
							%>	
					</div>
				</div>
				
				<div class="logout">
					<button class="bottone_logout" onclick="window.location.href='<%=request.getContextPath() %>/LogoutServlet'">
						Logout
					</button>
				</div>
			</main>
		<jsp:include page="fragments/Footer.jsp"/>
	</body>
</html>