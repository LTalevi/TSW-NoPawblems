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
					
				<form action="<%=request.getContextPath() %>/user/GestioneProfiloServlet" method="POST">
					<input type="hidden" name="action" value="modificaUtente"/>
					
					<div class="dati">
						<div class="campo_titolo">
							<div class="titolo">
								<h2>Dati Personali</h2>	
							</div>
							
							<button type="button" class="bottone_modifica" id="bottoneModifica" onclick="modificaDati(event)">
								<i class="material-icons">edit</i>
							</button>

							<button class="bottone_salva_modifica" id="bottoneSalva" style="display: none">
								<i class="material-icons">save</i>
							</button>
						</div>
						
						
						<div class="campo_testo">
							<div class="testo">
								<label for="nome">Nome:</label>
								<p class="dati"><%=utente.getNome() %></p>

								<input type="text" name="nome" id="nome" class="nome" placeholder="<%=utente.getNome() %>"
									value="<%=utente.getNome() %>" required style="display: none"/>
							</div>
							
							<div class="testo">
								<label for="cognome">Cognome:</label>
								<p class="dati"><%=utente.getCognome() %></p>

								<input type="text" name="cognome" id="cognome" class="cognome" placeholder="<%=utente.getCognome() %>"
									value="<%=utente.getCognome() %>" required style="display: none"/>
							</div>
							
							<div class="testo">
								<label for="email">E-mail:</label>
								<p class="dati"><%=utente.getEmail() %></p>

								<input type="email" name="email" id="email" class="email" placeholder="<%=utente.getEmail() %>"
									value="<%=utente.getEmail() %>" required style="display: none"/>
							</div>
	
							<div class="testo">
								<label for="telefono">Telefono:</label>
								<p class="dati"><%=utente.getTelefono() %></p>					

								<input type="tel" name="telefono" id="telefono" class="telefono" placeholder="<%=utente.getTelefono() %>"
									value="<%=utente.getTelefono() %>" required style="display: none"/>
							</div>
						</div>
					</div>
				</form>
	
				
					<div class="indirizzi">
						<div class="campo_titolo">
							<div class="titolo">
								<h2>Indirizzi Salvati</h2>	
							</div>
						</div>
					
					<%	
					
						if(indirizzi == null || indirizzi.isEmpty()){
							%>
							<p class="none" style="text-align:center; width:100%; margin:auto;">Non ci sono indirizzi salvati.</p>
							<%
							}
						else{
							int n = 0;
							
							for(Indirizzo i : indirizzi){
								long idIndirizzo = i.getIdIndirizzo();
								String via = i.getVia();
								String provincia = i.getProvincia();
								String citta = i.getCitta();
								String cap = i.getCap();
								String nazione = i.getNazione();
							%>
							
							<div class="indirizzo_rimozione">
								<form action="<%=request.getContextPath() %>/user/GestioneProfiloServlet" method="POST">
									<input type="hidden" name="action" value="rimuoviIndirizzo"/>
									<input type="hidden" name="idIndirizzo" value="<%= idIndirizzo %>"/>
									<button class="bottone_rimuovi">
										<i class="material-icons">remove</i>
									</button>					
								</form>
							
								<div class="campo_testo">
									<div class="testo">
										<p><%=nazione%>, <%=citta%> (<%=provincia %>), <%=cap %>, <%=via %></p>
									</div>
								</div>
							</div>
							
							<%
								}
							}	
							%>	
							
						<form action="<%=request.getContextPath() %>/user/GestioneProfiloServlet" method="POST">
							<input type="hidden" name="action" value="aggiungiIndirizzo"/>
							
								<button type="button" class="bottone_aggiungi" id="bottoneAggiungi" onclick="aggiungiIndirizzo(event)">
									<i class="material-icons">add</i>
								</button>	
							
								<div class="input-errore-container">
									<div class="input" id="input_via" style="display: none">
										<label for="via">Via:</label>
										<input type="text" name="via" id="via" placeholder="Via Roma 1" oninput="valida_via()" required/>
									</div>
										<span id="errore_via" class="errore"></span>
								</div>
								
								<div class="input-errore-container">
									<div class="input" id="input_citta" style="display: none">
										<label for="citta">Città:</label>
										<input type="text" name="citta" id="citta" placeholder="Roma" oninput="valida_citta()" required/>
									</div>
										<span id="errore_citta" class="errore"></span>
								</div>
								
								<div class="input-errore-container">
									<div class="input" id="input_cap" style="display: none">
										<label for="cap">CAP:</label>
										<input type="text" name="cap" id="cap" placeholder="84012" oninput="valida_cap()" required/>
									</div>
										<span id="errore_cap" class="errore"></span>
								</div>
								
								<div class="input-errore-container">
									<div class="input" id="input_provincia" style="display: none">
										<label for="provincia">Provincia:</label>
										<input type="text" name="provincia" id="provincia" placeholder="Roma" oninput="valida_provincia()" required/>
									</div>
										<span id="errore_provincia" class="errore"></span>
								</div>
								
								<div class="input-errore-container">
									<div class="input" id="input_nazione" style="display: none">
										<label for="nazione">Nazione:</label>
										<input type="text" name="nazione" id="nazione" placeholder="Italia" oninput="valida_nazione()" required/>
									</div>
										<span id="errore_nazione" class="errore"></span>
								</div>
								
								<button type="submit" class="bottone_aggiungi" id="bottoneAggiungi1" onclick="aggiungiIndirizzo(event)" style="display: none">
									Aggiungi il nuovo indirizzo!
								</button>
						</form>
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
							<p class="none" style="text-align:center; width:100%; margin:auto;">Non ci sono ordini registrati.</p>
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
									<a href="<%= request.getContextPath() %>/user/DettaglioOrdine?idOrdine=<%= o.getIdOrdine() %>">
										Vedi Dettaglio Ordine
									</a>
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
		<script src="<%=request.getContextPath() %>/scripts/ModificaDati.js"></script>
	</body>
</html>