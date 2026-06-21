<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="model.ordine.Ordine" %>
<%@ page import="model.dettaglioordine.DettaglioOrdine" %>
<%@ page import="model.varianteprodotto.VarianteProdotto" %>
<%@ page import="model.utente.Utente" %>
<%@ page import="model.prodotto.Prodotto" %>

<%
    Utente utente = (Utente) session.getAttribute("utente");
    Ordine ordine = (Ordine) request.getAttribute("ordine");
    List<Prodotto> prodottiDettaglio = (List<Prodotto>) request.getAttribute("prodottiDettaglio");
    
    if (ordine == null) {
        response.sendRedirect(request.getContextPath() + "/user/OrdiniServlet"); 
        return;
    }

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    List<DettaglioOrdine> dettagli = ordine.getDettagli();
%>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Dettaglio Ordine #<%= ordine.getIdOrdine() %></title>
        
        <link rel="stylesheet" href="<%= request.getContextPath() %>/stylesheets/main.css" type="text/css">
        <link rel="stylesheet" href="<%= request.getContextPath() %>/stylesheets/StileDettaglioOrdine.css" type="text/css">        
    </head>
    
    <body>
        <jsp:include page="/fragments/Nav.jsp"/>
    
        <main class="main-wrapper">
            
            <div class="info-ordine-header">
                <div class="info-box">
                    <h3>Dati Cliente</h3>
                    <% if (utente != null) { %>
                        <p><label>Nome:</label> <span><%= utente.getNome() %> <%= utente.getCognome() %></span></p>
                        <p><label>Email:</label> <span><%= utente.getEmail() %></span></p>
                        <p><label>Telefono:</label> <span><%= utente.getTelefono() %></span></p>
                    <% } else { %>
                        <p>Dati utente non disponibili.</p>
                    <% } %>
                </div>

                <div class="info-box">
                    <h3>Dettagli Spedizione</h3>
                    <p><%= ordine.getViaSpedizione() %></p>
                    <p><%= ordine.getCapSpedizione() %> - <%= ordine.getCittaSpedizione() %> (<%= ordine.getProvinciaSpedizione() %>)</p>
                    <p><%= ordine.getNazioneSpedizione() %></p>
                </div>
                
                <div class="info-box">
                    <h3>Riepilogo Documento</h3>
                    <p><label>Ordine N°:</label> <span><%= ordine.getIdOrdine() %></span></p>
                    <p><label>Fattura N°:</label> <span><%= ordine.getNumeroFattura() %></span></p>
                    <p><label>Data:</label> <span><%= ordine.getDataOrdine().format(formatter) %></span></p>
                    <p><label>Stato:</label> <span><%= ordine.getStato() %></span></p>
                </div>
            </div>

            <div class="dettagli-ordine">
                <% 
                    if (dettagli == null || dettagli.isEmpty()) { 
                %>
                    <p style="text-align: center; padding: 20px;">Nessun dettaglio articolo trovato.</p>
                <% 
                    } else { 
                        for (int i = 0; i < dettagli.size(); i++) { 
                            DettaglioOrdine item = dettagli.get(i);
                            VarianteProdotto variante = item.getVariante();

                            Prodotto p = prodottiDettaglio.get(i);
                            
                            String urlImmagine = "img/errori/ImmagineMancante.png"; 
                            String nomeProdotto = (p != null) ? p.getNome() : "Prodotto Sconosciuto";
                            
                            if (p != null && p.getImmagini() != null && !p.getImmagini().isEmpty()) {
                                urlImmagine = p.getImmagini().get(0).getUrl();
                            }
                %>      
                
                <div class="dettaglio-ordine">
                    <div class="info_prodotto">
                        <h3><%= nomeProdotto %></h3>
                        
                        <div class="info_prodotto_dati">
                            <img src="<%= request.getContextPath() %>/<%= urlImmagine %>" alt="<%= nomeProdotto %>" class="miniatura-prodotto">
                            
                            <div class="dati-riga">
                                <% if (variante != null) { %>
                                    <p>
                                        <label>Taglia:</label> <%= variante.getTaglia() %> &nbsp;|&nbsp; 
                                        <label>Colore:</label> <%= variante.getColore() %>
                                    </p>
                                <% } %>
                                
                                <p><label>Q.tà:</label> <%= item.getQuantita() %></p>
                                <p><label>Prezzo Unitario:</label> <%= String.format("%.2f", item.getPrezzoAcquisto()) %>€</p>
                                <p><label>IVA:</label> <%= item.getIvaAcquisto() %>%</p>
                                
                                <p class="subtotale-riga">
                                    <label>Subtotale:</label> 
                                    <span style="font-weight: bold;"><%= String.format("%.2f", (item.getPrezzoAcquisto() * item.getQuantita())) %>€</span>
                                </p>
                            </div>
                        </div>
                    </div>
                </div>
                
                <%
                        } 
                    }
                %>
            </div>

            <div class="riepilogo">
                <p class="costo-totale">Totale Corrisposto: <%= String.format("%.2f", ordine.getTotale()) %>€ </p>
                
                <div class="bottoni-ordine">
                    <button class="bottone-stampa" onclick="window.print();">
                        Stampa Fattura
                    </button>
                </div>
            </div>
            
        </main>
        
        <jsp:include page="/fragments/Footer.jsp"/>
    </body>
</html>