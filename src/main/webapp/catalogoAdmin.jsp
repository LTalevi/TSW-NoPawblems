<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="model.prodotto.Prodotto" %>

<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <title>Test Gestione Prodotti Admin</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 20px; background-color: #f4f4f9; }
        h1, h2, h3 { color: #333; }
        .container { display: flex; gap: 20px; flex-wrap: wrap; }
        .card { background: #fff; padding: 20px; border-radius: 8px; box-shadow: 0 2px 4px rgba(0,0,0,0.1); flex: 1; min-width: 300px; margin-bottom: 20px;}
        .full-width { width: 100%; flex: 100%; }
        form { display: flex; flex-direction: column; gap: 10px; }
        label { font-weight: bold; font-size: 14px; }
        input, select, textarea { padding: 8px; border: 1px solid #ccc; border-radius: 4px; }
        .btn { padding: 10px; background: #007bff; color: #fff; border: none; border-radius: 4px; cursor: pointer; }
        .btn-danger { background: #dc3545; }
        .btn-success { background: #28a745; }
        .btn:hover { opacity: 0.8; }
        .message { padding: 10px; margin-bottom: 20px; border-radius: 4px; font-weight: bold; }
        .success { background-color: #d4edda; color: #155724; border: 1px solid #c3e6cb; }
        .error { background-color: #f8d7da; color: #721c24; border: 1px solid #f5c6cb; }
        table { width: 100%; border-collapse: collapse; margin-top: 10px; }
        th, td { padding: 10px; border: 1px solid #ddd; text-align: left; }
        th { background-color: #eee; }
        .varianti-group { border: 1px dashed #aaa; padding: 10px; margin-bottom: 10px; }
    </style>
</head>
<body>

    <h1>Pannello di Controllo - Prodotti</h1>

    <% 
        String success = (String) session.getAttribute("success");
        if (success != null && !success.trim().isEmpty()) { 
    %>
        <div class="message success"><%= success %></div>
    <% 
            session.removeAttribute("success"); 
        } 

        String error = (String) request.getAttribute("error");
        if (error != null && !error.trim().isEmpty()) { 
    %>
        <div class="message error"><%= error %></div>
    <% 
        } 
    %>

    <div class="container">
        
        <div class="card">
            <h2>Filtra Catalogo (GET)</h2>
            <form action="<%= request.getContextPath() %>/admin/GestioneProdottiAdminServlet" method="get">
                <label>Ricerca per nome:</label>
                <% String ricercaVal = request.getAttribute("ricerca") != null ? (String) request.getAttribute("ricerca") : ""; %>
                <input type="text" name="ricerca" value="<%= ricercaVal %>">

                <label>ID Categoria:</label>
                <input type="number" name="idCategoria">

                <label>Prezzo Min:</label>
                <input type="number" step="0.01" name="prezzoMin">

                <label>Prezzo Max:</label>
                <input type="number" step="0.01" name="prezzoMax">

                <label>Ordinamento:</label>
                <select name="ordinamento">
                    <option value="">Predefinito</option>
                    <option value="prezzoCrescente">Prezzo Crescente</option>
                    <option value="prezzoDecrescente">Prezzo Decrescente</option>
                    <option value="nomeAZ">Nome A-Z</option>
                    <option value="nomeZA">Nome Z-A</option>
                </select>

                <button type="submit" class="btn">Cerca</button>
            </form>
        </div>

        <div class="card">
            <h2>Inserisci Nuovo Prodotto (POST)</h2>
            <form action="<%= request.getContextPath() %>/admin/GestioneProdottiAdminServlet" method="post" enctype="multipart/form-data">
                <input type="hidden" name="action" value="inserisci">
                
                <label>Nome Prodotto:</label>
                <input type="text" name="nome" required>

                <label>Descrizione:</label>
                <textarea name="descrizione" required></textarea>

                <label>ID Categoria (Es. 1):</label>
                <input type="number" name="idCategoria" required>

                <label>Prezzo Base Comune:</label>
                <input type="number" step="0.01" name="prezzo" value="10.00" required>
                
                <label>Disponibilità Comune:</label>
                <input type="number" name="disponibilita" value="50" required>

                <label>IVA (Es. 22):</label>
                <input type="number" name="iva" value="22" required>

                <h3>Varianti (Test con 2 fisse)</h3>
                <div class="varianti-group">
                    <label>Taglia 1:</label> <input type="text" name="taglia" value="S">
                    <label>Colore 1:</label> <input type="text" name="colore" value="Rosso">
                    <label>Colore Hex 1:</label> <input type="color" name="coloreHex" value="#ff0000">
                </div>
                <div class="varianti-group">
                    <label>Taglia 2:</label> <input type="text" name="taglia" value="M">
                    <label>Colore 2:</label> <input type="text" name="colore" value="Blu">
                    <label>Colore Hex 2:</label> <input type="color" name="coloreHex" value="#0000ff">
                </div>

                <label>Carica Immagine (opzionale per test):</label>
                <input type="file" name="immagine">
                <label>Testo Alternativo (Alt):</label>
                <input type="text" name="alt">

                <button type="submit" class="btn btn-success">Salva Prodotto</button>
            </form>
        </div>

        <div class="card full-width">
            <h2>Catalogo Esistente</h2>
            <p>Usa la barra di ricerca in alto per popolare questa tabella (doGet).</p>
            
            <% 
                List<Prodotto> prodotti = (List<Prodotto>) request.getAttribute("prodottiAdmin");
                if (prodotti == null || prodotti.isEmpty()) { 
            %>
                <p>Nessun prodotto trovato. Effettua una ricerca vuota per vedere tutti i prodotti.</p>
            <% 
                } else { 
            %>
                <table>
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Nome</th>
                            <th>ID Categoria</th>
                            <th>Varianti Trovate</th>
                            <th>Modifica Rapida (Senza file per test)</th>
                            <th>Azione Cancella</th>
                        </tr>
                    </thead>
                    <tbody>
                        <% for (Prodotto p : prodotti) { %>
                            <tr>
                                <td><%= p.getIdProdotto() %></td>
                                <td><%= p.getNome() %></td>
                                <td><%= (p.getCategoria() != null) ? p.getCategoria().getIdCategoria() : "" %></td>
                                <td><%= (p.getVarianti() != null) ? p.getVarianti().size() : 0 %> varianti</td>
                                
                                <td>
                                    <form action="<%= request.getContextPath() %>/admin/GestioneProdottiAdminServlet" method="post" style="flex-direction: row; align-items: center;">
                                        <input type="hidden" name="action" value="modifica">
                                        <input type="hidden" name="idProdotto" value="<%= p.getIdProdotto() %>">
                                        
                                        <input type="text" name="nome" value="<%= p.getNome() %>" style="width: 100px;">
                                        <input type="hidden" name="descrizione" value="<%= p.getDescrizione() != null ? p.getDescrizione() : "" %>">
                                        <input type="number" name="idCategoria" value="<%= (p.getCategoria() != null) ? p.getCategoria().getIdCategoria() : "" %>" style="width: 60px;">
                                        
                                        <input type="hidden" name="prezzo" value="15.00">
                                        <input type="hidden" name="disponibilita" value="10">
                                        <input type="hidden" name="iva" value="22">
                                        <input type="hidden" name="taglia" value="Unica">
                                        <input type="hidden" name="colore" value="Base">
                                        <input type="hidden" name="coloreHex" value="#000000">

                                        <button type="submit" class="btn">Modifica</button>
                                    </form>
                                </td>

                                <td>
                                    <form action="<%= request.getContextPath() %>/admin/GestioneProdottiAdminServlet" method="post" onsubmit="return confirm('Sei sicuro di voler eliminare questo prodotto?');">
                                        <input type="hidden" name="action" value="cancella">
                                        <input type="hidden" name="idCancella" value="<%= p.getIdProdotto() %>">
                                        <button type="submit" class="btn btn-danger">Elimina</button>
                                    </form>
                                </td>
                            </tr>
                        <% } %>
                    </tbody>
                </table>
            <% 
                } 
            %>
        </div>
    </div>
</body>
</html>