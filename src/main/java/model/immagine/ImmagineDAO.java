package model.immagine;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.ConnectionPool;
import model.InterfaceDAO;


public class ImmagineDAO implements InterfaceDAO<Immagine, Long> {

	@Override
	public Immagine doRetrieveByKey(Long key) throws SQLException {
		String query = "SELECT * FROM immagine WHERE id_immagine = ?";
		Immagine immagine = null;
		
		try(Connection connection = ConnectionPool.getConnection()){
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			
			preparedStatement.setLong(1, key);
			
			try(ResultSet result = preparedStatement.executeQuery()){
				if(result.next()) {
					immagine = new Immagine();
					immagine.setIdImmagine(result.getLong("id_immagine"));
					immagine.setProdotto(result.getLong("prodotto"));
					immagine.setUrl(result.getString("url"));
					immagine.setAlt(result.getString("alt"));
				}
			}
		}
		return immagine;
	}

	@Override
	public List<Immagine> doRetrieveAll() throws SQLException {
		String query = "SELECT * FROM immagine";
		List<Immagine> list = new ArrayList<Immagine>();
		Immagine immagine = null;
		
		try(Connection connection = ConnectionPool.getConnection()){
			Statement statement = connection.createStatement();
			
			try(ResultSet result = statement.executeQuery(query)){
				while(result.next()) {
					immagine = new Immagine();
					immagine.setIdImmagine(result.getLong("id_immagine"));
					immagine.setProdotto(result.getLong("prodotto"));
					immagine.setUrl(result.getString("url"));
					immagine.setAlt(result.getString("alt"));
					
					list.add(immagine);
				}
			}
		}
		return list;
	}

	@Override
	public void doSave(Immagine item) throws SQLException {
		String query = "INSERT INTO immagine (prodotto, url, alt) VALUES (?, ?, ?)";
		
		try (Connection connection = ConnectionPool.getConnection()){
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			
			preparedStatement.setLong(1, item.getProdotto());
			preparedStatement.setString(2, item.getUrl());
			preparedStatement.setString(3, item.getAlt());
			
			preparedStatement.executeUpdate();
		}
	}

	@Override
	public void doUpdate(Immagine item) throws SQLException {
		String query = "UPDATE immagine SET prodotto = ?, url = ?, alt = ? WHERE id_immagine = ?";
		
		try (Connection connection = ConnectionPool.getConnection()){
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			
			preparedStatement.setLong(1, item.getProdotto());
			preparedStatement.setString(2, item.getUrl());
			preparedStatement.setString(3, item.getAlt());
			preparedStatement.setLong(4, item.getIdImmagine());
			
			preparedStatement.executeUpdate();
		}
	}

	@Override
	public void doDelete(Long key) throws SQLException {
		String query = "DELETE FROM immagine WHERE id_immagine = ?";
		
		try (Connection connection = ConnectionPool.getConnection()){
			PreparedStatement preparedStatement = connection.prepareStatement(query);

			preparedStatement.setLong(1, key);
			
			preparedStatement.executeUpdate();
		}
	}	
}
