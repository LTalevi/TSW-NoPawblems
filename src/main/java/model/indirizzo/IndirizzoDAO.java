package model.indirizzo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.ConnectionPool;
import model.InterfaceDAO;

public class IndirizzoDAO implements InterfaceDAO<Indirizzo, Long> {

	@Override
	public Indirizzo doRetrieveByKey(Long key) throws SQLException {
		String query = "SELECT * FROM indirizzo WHERE id_indirizzo = ?";
		Indirizzo indirizzo = null;
		
		try (Connection connection = ConnectionPool.getConnection()){
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			
			preparedStatement.setLong(1, key);
			
			try (ResultSet result = preparedStatement.executeQuery()){
				if(result.next()) {
					indirizzo = new Indirizzo();
					indirizzo.setIdIndirizzo(result.getLong("id_indirizzo"));
					indirizzo.setUtente(result.getLong("utente"));
					indirizzo.setVia(result.getString("via"));
					indirizzo.setCitta(result.getString("citta"));
					indirizzo.setCap(result.getString("cap"));
                    indirizzo.setProvincia(result.getString("provincia"));
                    indirizzo.setNazione(result.getString("nazione"));
				}	
			}
		}
		return indirizzo;
	}

	@Override
	public List<Indirizzo> doRetrieveAll() throws SQLException {
		String query = "SELECT * FROM indirizzo";
		List<Indirizzo> list = new ArrayList<Indirizzo>();
		Indirizzo indirizzo = null;
		
		try(Connection connection = ConnectionPool.getConnection()){
			Statement statement = connection.createStatement();
			
			try(ResultSet result = statement.executeQuery(query)){
				while (result.next()) {
					indirizzo = new Indirizzo();
					indirizzo.setIdIndirizzo(result.getLong("id_indirizzo"));
					indirizzo.setUtente(result.getLong("utente"));
					indirizzo.setVia(result.getString("via"));
					indirizzo.setCitta(result.getString("citta"));
					indirizzo.setCap(result.getString("cap"));
					indirizzo.setProvincia(result.getString("provincia"));
					indirizzo.setNazione(result.getString("nazione"));
                
					list.add(indirizzo);
				}
			}
		}
		return list;
	}

	@Override
	public void doSave(Indirizzo item) throws SQLException {
		String query = "INSERT INTO indirizzo (utente, via, citta, cap, provincia, nazione) VALUES (?, ?, ?, ?, ?, ?)";
		
		try(Connection connection = ConnectionPool.getConnection()){
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			
			preparedStatement.setLong(1, item.getUtente());
			preparedStatement.setString(2, item.getVia());
			preparedStatement.setString(3, item.getCitta());
			preparedStatement.setString(4, item.getCap());
			preparedStatement.setString(5, item.getProvincia());
			preparedStatement.setString(6, item.getNazione());
			
			preparedStatement.executeUpdate();
		}
	}

	@Override
	public void doUpdate(Indirizzo item) throws SQLException {
		String query = "UPDATE indirizzo SET utente = ?, via = ?, citta = ?, cap = ?, provincia = ?, nazione = ? WHERE id_indirizzo = ?";
		
		try(Connection connection = ConnectionPool.getConnection()){
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			
			preparedStatement.setLong(1, item.getUtente());
			preparedStatement.setString(2, item.getVia());
			preparedStatement.setString(3, item.getCitta());
			preparedStatement.setString(4, item.getCap());
			preparedStatement.setString(5, item.getProvincia());
			preparedStatement.setString(6, item.getNazione());
			preparedStatement.setLong(7, item.getIdIndirizzo());
			
			preparedStatement.executeUpdate();
		}
	}

	@Override
	public void doDelete(Long key) throws SQLException {
		String query = "DELETE FROM indirizzo WHERE id_indirizzo = ?";
		
		try(Connection connection = ConnectionPool.getConnection()){
			PreparedStatement preparedStatement = connection.prepareStatement(query);

			preparedStatement.setLong(1, key);
			
			preparedStatement.executeUpdate();
		}
	}
	
	public List<Indirizzo> doRetrieveByUtente(Long idUtente) throws SQLException {
		String query = "SELECT * FROM indirizzo WHERE utente = ?";
		List<Indirizzo> list = new ArrayList<Indirizzo>();
		
		try (Connection connection = ConnectionPool.getConnection()) {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			
			preparedStatement.setLong(1, idUtente);
			
			try (ResultSet result = preparedStatement.executeQuery()) {
				while (result.next()) {
					Indirizzo indirizzo = new Indirizzo();
					indirizzo.setIdIndirizzo(result.getLong("id_indirizzo"));
					indirizzo.setUtente(result.getLong("utente"));
					indirizzo.setVia(result.getString("via"));
					indirizzo.setCitta(result.getString("citta"));
					indirizzo.setCap(result.getString("cap"));
					indirizzo.setProvincia(result.getString("provincia"));
					indirizzo.setNazione(result.getString("nazione"));
					
					list.add(indirizzo);
				}
			}
		}
		return list;
	}
}
