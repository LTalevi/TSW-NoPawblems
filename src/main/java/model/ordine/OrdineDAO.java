package model.ordine;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.ConnectionPool;
import model.InterfaceDAO;

public class OrdineDAO implements InterfaceDAO<Ordine, Long> {

	@Override
	public Ordine doRetrieveByKey(Long key) throws SQLException {
		String query = "SELECT * FROM ordine WHERE id_ordine = ?";
		Ordine ordine = null;
		
		try(Connection connection = ConnectionPool.getConnection()){
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			
			preparedStatement.setLong(1, key);
			
			try(ResultSet result = preparedStatement.executeQuery()){
				if(result.next()) {
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
			}
		}
		return ordine;
	}

	@Override
	public List<Ordine> doRetrieveAll() throws SQLException {
		String query = "SELECT * FROM ordine";
		List<Ordine> list = new ArrayList<Ordine>();
		Ordine ordine = null;
		
		try(Connection connection = ConnectionPool.getConnection()){
			Statement statement = connection.createStatement();
			
			try(ResultSet result = statement.executeQuery(query)){
				while(result.next()) {
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
					
					list.add(ordine);
				}
			}
		}
		return list;
	}

	@Override
	public void doSave(Ordine item) throws SQLException {
		String query = "INSERT INTO ordine (utente, via_spedizione, citta_spedizione, cap_spedizione, provincia_spedizione, nazione_spedizione, data_ordine, stato, totale, numero_fattura) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		
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
			
			preparedStatement.executeUpdate();
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
}
