package model.categoria;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.ConnectionPool;
import model.InterfaceDAO;

public class CategoriaDAO implements InterfaceDAO<Categoria, Long> {

	@Override
	public Categoria doRetrieveByKey(Long key) throws SQLException {
		String query = "SELECT * FROM categoria WHERE id_categoria = ?";
		Categoria categoria = null;
		
		try(Connection connection = ConnectionPool.getConnection()){
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			
			preparedStatement.setLong(1, key);
			
			try(ResultSet result = preparedStatement.executeQuery()){
				if(result.next()) {
					categoria = new Categoria();
					categoria.setIdCategoria(result.getLong("id_categoria"));
					categoria.setIdPadre(result.getLong("id_padre"));
					categoria.setNome(result.getString("nome"));
					categoria.setDescrizione(result.getString("descrizione"));
				}
			}
		}
		return categoria;
	}

	@Override
	public List<Categoria> doRetrieveAll() throws SQLException {
		String query = "SELECT * FROM categoria";
		List<Categoria> list = new ArrayList<Categoria>();
		Categoria categoria = null;
		
		try(Connection connection = ConnectionPool.getConnection()){
			Statement statement = connection.createStatement();
			
			try(ResultSet result = statement.executeQuery(query)){
				while(result.next()) {
					categoria = new Categoria();
					categoria.setIdCategoria(result.getLong("id_categoria"));
					categoria.setIdPadre(result.getLong("id_padre"));
					categoria.setNome(result.getString("nome"));
					categoria.setDescrizione(result.getString("descrizione"));
					
					list.add(categoria);
				}
			}
		}
		return list;
	}

	@Override
	public void doSave(Categoria item) throws SQLException {
		String query = "INSERT INTO categoria (id_padre, nome, descrizione) VALUES (?, ?, ?)";
		
		try(Connection connection = ConnectionPool.getConnection()){
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			
			if (item.getIdPadre() == 0) {
	            preparedStatement.setNull(1, java.sql.Types.INTEGER);
	        } else {
	            preparedStatement.setLong(1, item.getIdPadre());
	        }
			preparedStatement.setString(2, item.getNome());
			preparedStatement.setString(3, item.getDescrizione());
			
			preparedStatement.executeUpdate();
		}
	}

	@Override
	public void doUpdate(Categoria item) throws SQLException {
		String query = "UPDATE categoria SET id_padre = ?, nome = ?, descrizione = ?";
		
		try(Connection connection = ConnectionPool.getConnection()){
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			
			if (item.getIdPadre() == 0) { 
	            preparedStatement.setNull(1, java.sql.Types.INTEGER);
	        } else {
	            preparedStatement.setLong(1, item.getIdPadre());
	        }
			preparedStatement.setString(2, item.getNome());
			preparedStatement.setString(3, item.getDescrizione());
			
			preparedStatement.executeUpdate();
		}		
	}

	@Override
	public void doDelete(Long key) throws SQLException {
		String query = "DELETE FROM categoria WHERE id_categoria = ?";
		
		try(Connection connection = ConnectionPool.getConnection()){
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			
			preparedStatement.setLong(1, key);
			
			preparedStatement.executeUpdate();
		}
	}
}
