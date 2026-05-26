package model.prodottocarrello;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.ConnectionPool;
import model.varianteprodotto.VarianteProdotto;

public class ProdottoCarrelloDAO {

	public List<ProdottoCarrello> doRetrieveByUtente(Long idUtente) throws SQLException {
		String query = "SELECT c.utente, c.variante, c.quantita, "
				+ "v.prodotto_padre, v.taglia, v.colore, v.colore_hex, v.prezzo, v.iva, v.disponibilita "
				+ "FROM prodotto_carrello c "
				+ "JOIN variante_prodotto v ON c.variante = v.id_variante "
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
					
					VarianteProdotto variante = new VarianteProdotto();
					variante.setIdVariante(result.getLong("variante"));
					variante.setProdottoPadre(result.getLong("prodotto_padre"));
					variante.setTaglia(result.getString("taglia"));
					variante.setColore(result.getString("colore"));
					variante.setColoreHex(result.getString("colore_hex"));
					variante.setPrezzo(result.getFloat("prezzo"));
					variante.setIva(result.getInt("iva"));
					variante.setDisponibilita(result.getInt("disponibilita"));
					
					prodottoCarrello.setVariante(variante);
					lista.add(prodottoCarrello);
				}
			}
		}
		return lista;
	}

	public void doSave(ProdottoCarrello item) throws SQLException {
	    String query = "INSERT INTO prodotto_carrello (utente, variante, quantita) VALUES (?, ?, ?) "
	                 + "ON DUPLICATE KEY quantita = quantita + VALUES(quantita)";
	    
	    try (Connection connection = ConnectionPool.getConnection()){
	         PreparedStatement preparedStatement = connection.prepareStatement(query);
	        
	        preparedStatement.setLong(1, item.getUtente());
	        preparedStatement.setLong(2, item.getVariante().getIdVariante());
	        preparedStatement.setInt(3, item.getQuantita()); 
	        
	        preparedStatement.executeUpdate();
	    }
	}

	public void doUpdate(Long idUtente, Long idVariante, int nuovaQuantita) throws SQLException {
		String query = "UPDATE prodotto_carrello SET quantita = ? WHERE utente = ? AND variante = ?";
		
		try (Connection connection = ConnectionPool.getConnection()){
			 PreparedStatement preparedStatement = connection.prepareStatement(query);
			
			preparedStatement.setInt(1, nuovaQuantita);
			preparedStatement.setLong(2, idUtente);
			preparedStatement.setLong(3, idVariante);
			
			preparedStatement.executeUpdate();
		}
	}

	public void doDelete(Long idUtente, Long idVariante) throws SQLException {
		String query = "DELETE FROM prodotto_carrello WHERE utente = ? AND variante = ?";
		
		try (Connection connection = ConnectionPool.getConnection()) {
			 PreparedStatement preparedStatement = connection.prepareStatement(query);
			
			preparedStatement.setLong(1, idUtente);
			preparedStatement.setLong(2, idVariante);
			
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