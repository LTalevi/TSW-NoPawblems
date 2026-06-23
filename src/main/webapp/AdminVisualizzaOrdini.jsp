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
	
	String idClienteForm = request.getParameter("idCliente") != null ? request.getParameter("idCliente") : "";
	String dataInizioForm = request.getParameter("dataInizio") != null ? request.getParameter("dataInizio") : "";
	String dataFineForm = request.getParameter("dataFine") != null ? request.getParameter("dataFine") : "";
%>

<!DOCTYPE html>
<html>
	<head>
		<link rel="stylesheet" href="<%= request.getContextPath() %>/stylesheets/main.css" type="text/css">
		<link rel="stylesheet" href="<%= request.getContextPath() %>/stylesheets/StileOrdiniAdmin.css" type="text/css">
		
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
			
				<div class="Filtri">
					<form action="<%=request.getContextPath() %>/admin/GestioneOrdiniAdminServlet" method="POST">
						<div class="categoria">
							<fieldset>
								<legend>Imposta Filtri di Ricerca</legend>
										<div class="input-filtro">
				                            <label for="idCliente" class="grassetto">ID Cliente</label>
				                            <input type="number" name="idCliente" value="<%= idClienteForm %>"/>
				                        </div>
				                        
				                        <div class="input-filtro">
				                            <label for="dataInizio" class="grassetto">Data di Inizio</label>
				                            <input type="date" name="dataInizio" value="<%= dataInizioForm %>"/>
				                        </div>
				                        
				                        <div class="input-filtro">
				                            <label for="dataFine" class="grassetto">Data di Fine</label>
				                            <input type="date" class="radio" name="dataFine" value="<%= dataFineForm %>"/>
				                        </div>
	
								<button type="submit">Applica Filtri</button>
	
							</fieldset>
							
						</div>
				
					</form>
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
								<div class="griglia-ordine">
									<div class="cella">
										<span class="grassetto">ID Utente:</span><br/> <%=u %>
									</div>
									
									<div class="cella">
										<span class="grassetto">Spedito in data:</span><br/> <%=data %>
									</div>
									
									<div class="cella">
										<span class="grassetto">Stato:</span><br/> <%=stato %>
									</div>
									
									<div class="cella">
										<span class="grassetto">Indirizzo:</span><br/> <%=nazioneSpedizione%>,
											 <%=cittaSpedizione%> (<%=provinciaSpedizione %>), <%=capSpedizione %>, <%=viaSpedizione %>
									</div>
									
									<div class="cella">
										<span class="grassetto">Totale:</span><br/> <%=String.format("%.2f", totale) %>€
									</div>
									
									<div class="cella">
										<span class="grassetto">N. Fattura:</span><br/> <%=numeroFattura %>
									</div>
								</div>
							</div>
							
							<a class="dettaglio_ordine" href="<%= request.getContextPath() %>/user/DettaglioOrdine?idOrdine=<%= o.getIdOrdine() %>">
								Vedi Dettaglio Ordine
							</a>
								
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