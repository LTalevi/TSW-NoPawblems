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
	
	List<Ordine> ordini = (List<Ordine>) request.getAttribute("ordiniAdmin");
	List<Indirizzo> indirizzi = (List<Indirizzo>) request.getAttribute("indirizzi");
	
	String successo = (String) sessione.getAttribute("success");
	String errore = (String) sessione.getAttribute("error");
%>

<!DOCTYPE html>
<html>
	<head>
		<link rel="stylesheet" href="<%= request.getContextPath() %>/stylesheets/main.css" type="text/css">
		<link rel="stylesheet" href="<%= request.getContextPath() %>/stylesheets/StileAreaAdmin.css" type="text/css">
		
		<meta charset="UTF-8">
		<title>Admin Visualizza Ordini</title>
	</head>

	<body>
		<jsp:include page="/fragments/Nav.jsp"/>
		
		<main class="wrapper">
			<%
				if(errore != null && !errore.trim().isEmpty()){
					%>
					<div class="blocco-errore-successo">
						<p class="testoErrore"><%=errore %></p>
					</div>
					<%
				}
				else if(successo != null && !successo.trim().isEmpty()){
					%>
					<div class="blocco-errore-successo">
						<p class="testoSuccesso"><%=successo %></p>
					</div>
					<%
				}
			%>
				
			<div class="titolo">
				<h1><span class="gradiente">Visualizzazione Ordini</span></h1>
			</div>
			
			<div class="contenitore">
				<%
				if(ordini == null || ordini.isEmpty()){
					%>
					<p class="none" style="text-align:center; width:100%; margin:auto;">Non ci sono ordini registrati.</p>
					<%
					}
				else{
					int n = 0;
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
					
					for(Ordine o : ordini){
						long u = o.getUtente();
					    String viaSpedizione = o.getViaSpedizione();
					    String cittaSpedizione = o.getCittaSpedizione();
					    String capSpedizione = o.getCapSpedizione();
					    String provinciaSpedizione = o.getProvinciaSpedizione();
					    String nazioneSpedizione = o.getNazioneSpedizione();
					    
					    String data = (o.getDataOrdine() != null) ? o.getDataOrdine().format(formatter) : "N/D";
					    
					    String stato = o.getStato();
					    float totale = o.getTotale();
					    String numeroFattura = o.getNumeroFattura();
					%>
					<div class="campo_testo">
							<div class="testo">
								<p>
									<span class="grassetto">Utente che ha effettuato l'ordine:</span><br/> <%=u %><br/><br/>
									<span class="grassetto">Ordine spedito in data:</span><br/> <%=data %><br/><br/>
									<span class="grassetto">Stato Ordine:</span><br/> <%=stato %><br/><br/>
									<span class="grassetto">Indirizzo di Spedizione:</span><br/> <%=nazioneSpedizione%>,
									 <%=cittaSpedizione%> (<%=provinciaSpedizione %>), <%=capSpedizione %>, <%=viaSpedizione %><br/><br/>
									<span class="grassetto">Totale:</span><br/> <%=String.format("%.2f", totale) %>€<br/><br/>
									<span class="grassetto">Numero Fattura:</span><br/> <%=numeroFattura %><br/><br/>
								</p>

								<a class="dettaglio_ordine" href="<%= request.getContextPath() %>/user/DettaglioOrdine?idOrdine=<%= o.getIdOrdine() %>">
									Vedi Dettaglio Ordine
								</a>
								
							</div>
						</div>
					<%
					}
				}	
				%>
			</div>
		</main>
		
		<jsp:include page="/fragments/Footer.jsp"/>
	</body>
</html>