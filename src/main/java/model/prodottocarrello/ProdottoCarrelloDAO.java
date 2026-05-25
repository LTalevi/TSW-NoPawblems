package model.prodottocarrello;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.ConnectionPool;
import model.prodotto.Prodotto;

public class ProdottoCarrelloDAO {

	public List<ProdottoCarrello> doRetrieveByUtente(Long idUtente) throws SQLException {
		String query = "SELECT c.*, p.nome, p.descrizione, p.prezzo, p.taglia, p.colore "
				+ "FROM prodotto_carrello c "
				+ "JOIN prodotto p ON c.prodotto = p.id_prodotto "
				+ "WHERE c.utente = ?";
		List<ProdottoCarrello> lista = new ArrayList<>();
		
		try (Connection connection = ConnectionPool.getConnection()){
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			
			preparedStatement.setLong(1, idUtente);
			
			try (ResultSet result = preparedStatement.executeQuery()) {
				while (result.next()) {
					ProdottoCarrello prodottoCarrello = new ProdottoCarrello();
					prodottoCarrello.setUtente(idUtente);
					prodottoCarrello.setQuantita(result.getInt("quantita"));
					
					Prodotto prod = new Prodotto();
					prod.setIdProdotto(result.getLong("prodotto"));
					prod.setNome(result.getString("nome"));
					prod.setDescrizione(result.getString("descrizione"));
					prod.setPrezzo(result.getFloat("prezzo"));
					prod.setTaglia(result.getString("taglia"));
					prod.setColore(result.getString("colore"));
					
					prodottoCarrello.setProdotto(prod);
					lista.add(prodottoCarrello);
				}
			}
		}
		return lista;
	}

	public void doSave(ProdottoCarrello item) throws SQLException {
	    String query = "INSERT INTO prodotto_carrello (utente, prodotto, quantita) VALUES (?, ?, ?) "
	                 + "ON DUPLICATE KEY UPDATE quantita = quantita + VALUES(quantita)";
	    
	    try (Connection connection = ConnectionPool.getConnection()){
	         PreparedStatement preparedStatement = connection.prepareStatement(query);
	        
	        preparedStatement.setLong(1, item.getUtente());
	        preparedStatement.setLong(2, item.getProdotto().getIdProdotto());
	        preparedStatement.setInt(3, item.getQuantita()); 
	        
	        preparedStatement.executeUpdate();
	    }
	}

	public void doUpdate(Long idUtente, Long idProdotto, int nuovaQuantita) throws SQLException {
		String query = "UPDATE prodotto_carrello SET quantita = ? WHERE utente = ? AND prodotto = ?";
		
		try (Connection connection = ConnectionPool.getConnection()){
			 PreparedStatement preparedStatement = connection.prepareStatement(query);
			
			preparedStatement.setInt(1, nuovaQuantita);
			preparedStatement.setLong(2, idUtente);
			preparedStatement.setLong(3, idProdotto);
			
			preparedStatement.executeUpdate();
		}
	}

	public void doDelete(Long idUtente, Long idProdotto) throws SQLException {
		String query = "DELETE FROM prodotto_carrello WHERE utente = ? AND prodotto = ?";
		
		try (Connection connection = ConnectionPool.getConnection()) {
			 PreparedStatement preparedStatement = connection.prepareStatement(query);
			
			preparedStatement.setLong(1, idUtente);
			preparedStatement.setLong(2, idProdotto);
			
			preparedStatement.executeUpdate();
		}
	}

	public void doClearCarrello(Long idUtente) throws SQLException {
		String query = "DELETE FROM prodotto_carrello WHERE utente = ?";
		
		try (Connection connection = ConnectionPool.getConnection()) {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			
			preparedStatement.setLong(1, idUtente);
			preparedStatement.executeUpdate();
		}
	}
}