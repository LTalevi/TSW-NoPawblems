package model.prodotto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.ConnectionPool;
import model.InterfaceDAO;

public class ProdottoDAO implements InterfaceDAO<Prodotto, Long> {

	@Override
	public Prodotto doRetrieveByKey(Long key) throws SQLException {
		String query = "SELECT * FROM prodotto WHERE id_prodotto = ?";
		Prodotto prodotto = null;
		
		try(Connection connection = ConnectionPool.getConnection()){
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			
			preparedStatement.setLong(1, key);
			
			try(ResultSet result = preparedStatement.executeQuery()){
				if(result.next()) {
					prodotto = new Prodotto();
					prodotto.setIdProdotto(result.getLong("id_prodotto"));
					prodotto.setCategoria(result.getLong("categoria"));
					prodotto.setNome(result.getString("nome"));
					prodotto.setDescrizione(result.getString("descrizione"));
					prodotto.setTaglia(result.getString("taglia"));
					prodotto.setColore(result.getString("colore"));
					prodotto.setPrezzo(result.getFloat("prezzo"));
					prodotto.setIva(result.getInt("iva"));
					prodotto.setDisponibilita(result.getInt("disponibilita"));
					prodotto.setActive(result.getBoolean("attivo"));
				}
			}
		}
		return prodotto;
	}

	@Override
	public List<Prodotto> doRetrieveAll() throws SQLException {
		String query = "SELECT * FROM prodotto";
		List<Prodotto> list = new ArrayList<Prodotto>();
		Prodotto prodotto = null;
		
		try(Connection connection = ConnectionPool.getConnection()){
			Statement statement = connection.createStatement();
			
			try(ResultSet result = statement.executeQuery(query)){
				while(result.next()) {
					prodotto = new Prodotto();
					prodotto.setIdProdotto(result.getLong("id_prodotto"));
					prodotto.setCategoria(result.getLong("categoria"));
					prodotto.setNome(result.getString("nome"));
					prodotto.setDescrizione(result.getString("descrizione"));
					prodotto.setTaglia(result.getString("taglia"));
					prodotto.setColore(result.getString("colore"));
					prodotto.setPrezzo(result.getFloat("prezzo"));
					prodotto.setIva(result.getInt("iva"));
					prodotto.setDisponibilita(result.getInt("disponibilita"));
					prodotto.setActive(result.getBoolean("attivo"));
					
					list.add(prodotto);
				}
			}
		}
		return list;
	}

	@Override
	public void doSave(Prodotto item) throws SQLException {
		String query = "INSERT INTO prodotto (categoria, nome, descrizione, taglia, colore, prezzo, iva, disponibilita, attivo) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
		
		try(Connection connection = ConnectionPool.getConnection()){
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			
			preparedStatement.setLong(1, item.getCategoria());
			preparedStatement.setString(2, item.getNome());
			preparedStatement.setString(3, item.getDescrizione());
			preparedStatement.setString(4, item.getTaglia());
			preparedStatement.setString(5, item.getColore());
			preparedStatement.setFloat(6, item.getPrezzo());
			preparedStatement.setInt(7, item.getIva());
			preparedStatement.setInt(8, item.getDisponibilita());
			preparedStatement.setBoolean(9, item.isActive());
			
			preparedStatement.executeUpdate();
		}
	}

	@Override
	public void doUpdate(Prodotto item) throws SQLException {
		String query = "UPDATE prodotto SET categoria = ?, nome = ?, descrizione = ?, taglia = ?, colore = ?, prezzo = ?, iva = ?, disponibilita = ?, attivo = ? WHERE id_prodotto = ?";
		
		try(Connection connection = ConnectionPool.getConnection()){
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			
			preparedStatement.setLong(1, item.getCategoria());
			preparedStatement.setString(2, item.getNome());
			preparedStatement.setString(3, item.getDescrizione());
			preparedStatement.setString(4, item.getTaglia());
			preparedStatement.setString(5, item.getColore());
			preparedStatement.setFloat(6, item.getPrezzo());
			preparedStatement.setInt(7, item.getIva());
			preparedStatement.setInt(8, item.getDisponibilita());
			preparedStatement.setBoolean(9, item.isActive());
			preparedStatement.setLong(10, item.getIdProdotto());
			
			preparedStatement.executeUpdate();
		}
	}

	@Override
	public void doDelete(Long key) throws SQLException {
		String query = "DELETE FROM prodotto WHERE id_prodotto = ?";
		
		try(Connection connection = ConnectionPool.getConnection()){
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			
			preparedStatement.setLong(1, key);
			
			preparedStatement.executeUpdate();
		}
	}
}
