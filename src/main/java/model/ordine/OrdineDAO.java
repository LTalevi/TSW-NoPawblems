package model.ordine;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import model.ConnectionPool;
import model.InterfaceDAO;
import model.dettaglioordine.DettaglioOrdine;
import model.prodotto.Prodotto;

public class OrdineDAO implements InterfaceDAO<Ordine, Long> {

	@Override
	public Ordine doRetrieveByKey(Long key) throws SQLException {
		String query = "SELECT o.*, d.id_dettaglio_ordine, d.prodotto AS id_prodotto, d.quantita, d.prezzo_acquisto, d.iva_acquisto, "
				+ "p.nome AS nome_prodotto, p.descrizione AS descrizione_prodotto, p.taglia, p.colore "
				+ "FROM ordine o "
				+ "LEFT JOIN dettaglio_ordine d ON o.id_ordine = d.ordine "
				+ "LEFT JOIN prodotto p ON d.prodotto = p.id_prodotto "
				+ "WHERE o.id_ordine = ?";
		Ordine ordine = null;
		
		try(Connection connection = ConnectionPool.getConnection()){
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			
			preparedStatement.setLong(1, key);
			
			try(ResultSet result = preparedStatement.executeQuery()){
				while(result.next()) {
					if (ordine == null) {
						ordine = new Ordine();
						ordine.setIdOrdine(result.getLong("id_ordine"));
						ordine.setUtente(result.getLong("utente"));
						ordine.setViaSpedizione(result.getString("via_spedizione"));
						ordine.setCittaSpedizione(result.getString("citta_spedizione"));
						ordine.setCapSpedizione(result.getString("cap_spedizione"));
						ordine.setProvinciaSpedizione(result.getString("provincia_spedizione"));
						ordine.setNazioneSpedizione(result.getString("nazione_spedizione"));
						java.sql.Timestamp timestamp = result.getTimestamp("data_ordine");
						if (timestamp != null) {
							ordine.setDataOrdine(timestamp.toLocalDateTime());
						}
						ordine.setStato(result.getString("stato"));
						ordine.setTotale(result.getFloat("totale"));
						ordine.setNumeroFattura(result.getString("numero_fattura"));
					}
					
					long idDettaglio = result.getLong("id_dettaglio_ordine");
					if (idDettaglio != 0 && !result.wasNull()) {
						DettaglioOrdine det = new DettaglioOrdine();
						det.setIdDettaglioOrdine(idDettaglio);
						det.setOrdine(ordine.getIdOrdine());
						det.setQuantita(result.getInt("quantita"));
						det.setPrezzoAcquisto(result.getFloat("prezzo_acquisto"));
						det.setIvaAcquisto(result.getInt("iva_acquisto"));
						
						Prodotto prod = new Prodotto();
						prod.setIdProdotto(result.getLong("id_prodotto"));
						prod.setNome(result.getString("nome_prodotto"));
						prod.setDescrizione(result.getString("descrizione_prodotto"));
						prod.setTaglia(result.getString("taglia"));
						prod.setColore(result.getString("colore"));
						
						det.setProdotto(prod);
						ordine.getDettagli().add(det); 
					}
				}
			}
		}
		return ordine;
	}

	@Override
	public List<Ordine> doRetrieveAll() throws SQLException {
		String query = "SELECT o.*, d.id_dettaglio_ordine, d.prodotto AS id_prodotto, d.quantita, d.prezzo_acquisto, d.iva_acquisto, "
				+ "p.nome AS nome_prodotto, p.descrizione AS descrizione_prodotto, p.taglia, p.colore "
				+ "FROM ordine o "
				+ "LEFT JOIN dettaglio_ordine d ON o.id_ordine = d.ordine "
				+ "LEFT JOIN prodotto p ON d.prodotto = p.id_prodotto "
				+ "ORDER BY o.id_ordine DESC";
		Map<Long, Ordine> mappaOrdini = new LinkedHashMap<>();
		Ordine ordine = null;
		
		try(Connection connection = ConnectionPool.getConnection()){
			Statement statement = connection.createStatement();
			
			try(ResultSet result = statement.executeQuery(query)){
				while(result.next()) {
					long idOrdine = result.getLong("id_ordine");
					ordine = mappaOrdini.get(idOrdine);

					if (ordine == null) {
						ordine = new Ordine();
						ordine.setIdOrdine(idOrdine);
						ordine.setUtente(result.getLong("utente"));
						ordine.setViaSpedizione(result.getString("via_spedizione"));
						ordine.setCittaSpedizione(result.getString("citta_spedizione"));
						ordine.setCapSpedizione(result.getString("cap_spedizione"));
						ordine.setProvinciaSpedizione(result.getString("provincia_spedizione"));
						ordine.setNazioneSpedizione(result.getString("nazione_spedizione"));
						java.sql.Timestamp timestamp = result.getTimestamp("data_ordine");
						if (timestamp != null) {
							ordine.setDataOrdine(timestamp.toLocalDateTime());
						}
						ordine.setStato(result.getString("stato"));
						ordine.setTotale(result.getFloat("totale"));
						ordine.setNumeroFattura(result.getString("numero_fattura"));
						
						mappaOrdini.put(idOrdine, ordine);
					}
					
					long idDettaglio = result.getLong("id_dettaglio_ordine");
					if (idDettaglio != 0 && !result.wasNull()) {
						DettaglioOrdine det = new DettaglioOrdine();
						det.setIdDettaglioOrdine(idDettaglio);
						det.setOrdine(idOrdine);
						det.setQuantita(result.getInt("quantita"));
						det.setPrezzoAcquisto(result.getFloat("prezzo_acquisto"));
						det.setIvaAcquisto(result.getInt("iva_acquisto"));
						
						Prodotto prod = new Prodotto();
						prod.setIdProdotto(result.getLong("id_prodotto"));
						prod.setNome(result.getString("nome_prodotto"));
						prod.setDescrizione(result.getString("descrizione_prodotto"));
						prod.setTaglia(result.getString("taglia"));
						prod.setColore(result.getString("colore"));
						
						det.setProdotto(prod);
						ordine.getDettagli().add(det);
					}
				}
			}
			return new ArrayList<>(mappaOrdini.values());
		}
	}

	@Override
	public void doSave(Ordine item) throws SQLException {
		String query = "INSERT INTO ordine (utente, via_spedizione, citta_spedizione, cap_spedizione, provincia_spedizione, nazione_spedizione, data_ordine, stato, totale, numero_fattura) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		
		try (Connection connection = ConnectionPool.getConnection()){
			PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			
			preparedStatement.setLong(1, item.getUtente());
			preparedStatement.setString(2, item.getViaSpedizione());
			preparedStatement.setString(3, item.getCittaSpedizione());
			preparedStatement.setString(4, item.getCapSpedizione());
			preparedStatement.setString(5, item.getProvinciaSpedizione());
			preparedStatement.setString(6, item.getNazioneSpedizione());
			if (item.getDataOrdine() != null) {
			    preparedStatement.setTimestamp(7, java.sql.Timestamp.valueOf(item.getDataOrdine()));
			} else {
			    preparedStatement.setNull(7, java.sql.Types.TIMESTAMP);
			}
			preparedStatement.setString(8, item.getStato());
			preparedStatement.setFloat(9, item.getTotale());
			preparedStatement.setString(10, item.getNumeroFattura());
			
			preparedStatement.executeUpdate();
			
			try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
				if (generatedKeys.next()) {
					item.setIdOrdine(generatedKeys.getLong(1));
				} else {
					throw new SQLException("Errore: ID dell'ordine non generato.");
				}
			}
		}
	}

	@Override
	public void doUpdate(Ordine item) throws SQLException {
		String query = "UPDATE ordine SET utente = ?, via_spedizione = ?, citta_spedizione = ?, cap_spedizione = ?, provincia_spedizione = ?, nazione_spedizione = ?, data_ordine = ?, stato = ?, totale = ?, numero_fattura = ? WHERE id_ordine = ?";
		
		try (Connection connection = ConnectionPool.getConnection()){
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			
			preparedStatement.setLong(1, item.getUtente());
			preparedStatement.setString(2, item.getViaSpedizione());
			preparedStatement.setString(3, item.getCittaSpedizione());
			preparedStatement.setString(4, item.getCapSpedizione());
			preparedStatement.setString(5, item.getProvinciaSpedizione());
			preparedStatement.setString(6, item.getNazioneSpedizione());
			if (item.getDataOrdine() != null) {
			    preparedStatement.setTimestamp(7, java.sql.Timestamp.valueOf(item.getDataOrdine()));
			} else {
			    preparedStatement.setNull(7, java.sql.Types.TIMESTAMP);
			}
			preparedStatement.setString(8, item.getStato());
			preparedStatement.setFloat(9, item.getTotale());
			preparedStatement.setString(10, item.getNumeroFattura());
			preparedStatement.setLong(11, item.getIdOrdine());
			
			preparedStatement.executeUpdate();
		}
	}

	@Override
	public void doDelete(Long key) throws SQLException {
		String query = "DELETE FROM ordine WHERE id_ordine = ?";
		
		try (Connection connection = ConnectionPool.getConnection()){
			PreparedStatement preparedStatement = connection.prepareStatement(query);

			preparedStatement.setLong(1, key);
			
			preparedStatement.executeUpdate();
		}
	}
	
	public List<Ordine> doRetrieveByUtente(Long utente) throws SQLException {
		String query = "SELECT o.*, d.id_dettaglio_ordine, d.prodotto AS id_prodotto, d.quantita, d.prezzo_acquisto, d.iva_acquisto, "
				+ "p.nome AS nome_prodotto, p.descrizione AS descrizione_prodotto, p.taglia, p.colore "
				+ "FROM ordine o "
				+ "LEFT JOIN dettaglio_ordine d ON o.id_ordine = d.ordine "
				+ "LEFT JOIN prodotto p ON d.prodotto = p.id_prodotto "
				+ "WHERE o.utente = ? "
				+ "ORDER BY o.id_ordine DESC";
		Map<Long, Ordine> mappaOrdini = new LinkedHashMap<>();
		Ordine ordine = null;
		
		try (Connection connection = ConnectionPool.getConnection()) {
			 PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setLong(1, utente);
			
			try (ResultSet result = preparedStatement.executeQuery()) {
				while (result.next()) {
					long idOrdine = result.getLong("id_ordine");
					ordine = mappaOrdini.get(idOrdine);

					if (ordine == null) {
						ordine = new Ordine();
						ordine.setIdOrdine(idOrdine);
						ordine.setUtente(utente);
						ordine.setViaSpedizione(result.getString("via_spedizione"));
						ordine.setCittaSpedizione(result.getString("citta_spedizione"));
						ordine.setCapSpedizione(result.getString("cap_spedizione"));
						ordine.setProvinciaSpedizione(result.getString("provincia_spedizione"));
						ordine.setNazioneSpedizione(result.getString("nazione_spedizione"));
						java.sql.Timestamp timestamp = result.getTimestamp("data_ordine");
						if (timestamp != null) {
							ordine.setDataOrdine(timestamp.toLocalDateTime());
						}
						ordine.setStato(result.getString("stato"));
						ordine.setTotale(result.getFloat("totale"));
						ordine.setNumeroFattura(result.getString("numero_fattura"));
						
						mappaOrdini.put(idOrdine, ordine); 
					}
					
					long idDettaglio = result.getLong("id_dettaglio_ordine");
					if (idDettaglio != 0 && !result.wasNull()) {
						DettaglioOrdine det = new DettaglioOrdine();
						det.setIdDettaglioOrdine(idDettaglio);
						det.setOrdine(idOrdine);
						det.setQuantita(result.getInt("quantita"));
						det.setPrezzoAcquisto(result.getFloat("prezzo_acquisto"));
						det.setIvaAcquisto(result.getInt("iva_acquisto"));
						
						Prodotto prod = new Prodotto();
						prod.setIdProdotto(result.getLong("id_prodotto"));
						prod.setNome(result.getString("nome_prodotto"));
						prod.setDescrizione(result.getString("descrizione_prodotto"));
						prod.setTaglia(result.getString("taglia"));
						prod.setColore(result.getString("colore"));
						
						det.setProdotto(prod);
						ordine.getDettagli().add(det); 
					}
				}
			}
		}
		return new ArrayList<>(mappaOrdini.values());
	}
}
