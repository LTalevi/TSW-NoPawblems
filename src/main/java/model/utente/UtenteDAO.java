package model.utente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.ConnectionPool;
import model.InterfaceDAO;

public class UtenteDAO implements InterfaceDAO<Utente, Long> {

	@Override
	public Utente doRetrieveByKey(Long key) throws SQLException {
		String query = "SELECT * FROM utente WHERE id_utente = ?";
		Utente utente = null;
		
		try (Connection connection = ConnectionPool.getConnection()){
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			
			preparedStatement.setLong(1, key);
			
			try (ResultSet result = preparedStatement.executeQuery()){
				if(result.next()) {
					utente = new Utente();
					utente.setIdUtente(result.getLong("id_utente"));
					utente.setNome(result.getString("nome"));
					utente.setCognome(result.getString("cognome"));
					utente.setEmail(result.getString("email"));
					utente.setTelefono(result.getString("telefono"));
                    utente.setPassword(result.getString("password"));
                    utente.setAdmin(result.getBoolean("admin"));
				}	
			}
		}
		return utente;
	}

	@Override
	public List<Utente> doRetrieveAll() throws SQLException {
		String query = "SELECT * FROM utente";
		List<Utente> list = new ArrayList<Utente>();
		Utente utente = null;
		
		try(Connection connection = ConnectionPool.getConnection()){
			Statement statement = connection.createStatement();
			
			try (ResultSet result = statement.executeQuery(query)){
				while(result.next()){
					utente = new Utente();
					utente.setIdUtente(result.getLong("id_utente"));
					utente.setNome(result.getString("nome"));
					utente.setCognome(result.getString("cognome"));
					utente.setEmail(result.getString("email"));
					utente.setTelefono(result.getString("telefono"));
                    utente.setPassword(result.getString("password"));
                    utente.setAdmin(result.getBoolean("admin"));
                    
                    list.add(utente);
				}
			}
		}
		
		return list;
	}

	@Override
	public void doSave(Utente item) throws SQLException {
		String query = "INSERT INTO utente (nome, cognome, email, telefono, password, isAdmin) VALUES (?, ?, ?, ?, ?, ?)";
		
		try(Connection connection = ConnectionPool.getConnection()){
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			
			preparedStatement.setString(1, item.getNome());
			preparedStatement.setString(2, item.getCognome());
			preparedStatement.setString(3, item.getEmail());
			preparedStatement.setString(4, item.getTelefono());
			preparedStatement.setString(5, item.getPassword());
			preparedStatement.setBoolean(6, item.isAdmin());
			
			preparedStatement.executeUpdate();
		}
	}

	@Override
	public void doUpdate(Utente item) throws SQLException {
		String query = "UPDATE utente SET nome = ?, cognome = ?, email = ?, telefono = ?, password = ?, isAdmin = ? WHERE id_utente = ?";
		
		try(Connection connection = ConnectionPool.getConnection()){
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			
			preparedStatement.setString(1, item.getNome());
			preparedStatement.setString(2, item.getCognome());
			preparedStatement.setString(3, item.getEmail());
			preparedStatement.setString(4, item.getTelefono());
			preparedStatement.setString(5, item.getPassword());
			preparedStatement.setBoolean(6, item.isAdmin());
			preparedStatement.setLong(7, item.getIdUtente());
			
			preparedStatement.executeUpdate();
		}
	}

	@Override
	public void doDelete(Long key) throws SQLException {
		String query = "DELETE FROM utente WHERE id_utente = ?";
		
		try(Connection connection = ConnectionPool.getConnection()){
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			
			preparedStatement.setLong(1, key);
			
			preparedStatement.executeUpdate();
		}
	}

	public Utente doRetrieveByEmail(String email) throws SQLException {
		String query = "SELECT * FROM utente WHERE email = ?";
		Utente utente = null;
		
		try (Connection connection = ConnectionPool.getConnection()){
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			
			preparedStatement.setString(1, email);
			
			try (ResultSet result = preparedStatement.executeQuery()){
				if(result.next()) {
					utente = new Utente();
					utente.setIdUtente(result.getLong("id_utente"));
					utente.setNome(result.getString("nome"));
					utente.setCognome(result.getString("cognome"));
					utente.setEmail(result.getString("email"));
					utente.setTelefono(result.getString("telefono"));
                    utente.setPassword(result.getString("password"));
                    utente.setAdmin(result.getBoolean("admin"));
				}	
			}
		}
		return utente;
	}
}
