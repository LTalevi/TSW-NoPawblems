package model.dettaglioordine;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.ConnectionPool;
import model.InterfaceDAO;
import model.prodotto.Prodotto;

public class DettaglioOrdineDAO implements InterfaceDAO<DettaglioOrdine, Long> {

	@Override
	public DettaglioOrdine doRetrieveByKey(Long key) throws SQLException {
		String query = "SELECT d.*, p.nome AS nome_prodotto, p.descrizione AS descrizione_prodotto, p.taglia, p.colore "
				+ "FROM dettaglio_ordine d "
				+ "JOIN prodotto p "
				+ "ON d.prodotto = p.id_prodotto "
				+ "WHERE d.id_dettaglio_ordine = ?";
		DettaglioOrdine dettaglioOrdine = null;
		
		try(Connection connection = ConnectionPool.getConnection()){
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			
			preparedStatement.setLong(1, key);
			
			try(ResultSet result = preparedStatement.executeQuery()){
				if(result.next()) {
					dettaglioOrdine = new DettaglioOrdine();
					dettaglioOrdine.setIdDettaglioOrdine(result.getLong("id_dettaglio_ordine"));
					dettaglioOrdine.setOrdine(result.getLong("ordine"));
					
					Prodotto prodotto = new Prodotto();
					prodotto.setIdProdotto(result.getLong("prodotto"));
					prodotto.setNome(result.getString("nome_prodotto"));
					prodotto.setDescrizione(result.getString("descrizione_prodotto"));
					prodotto.setTaglia(result.getString("taglia"));
					prodotto.setColore(result.getString("colore"));
					
					dettaglioOrdine.setProdotto(prodotto);
					dettaglioOrdine.setQuantita(result.getInt("quantita"));
					dettaglioOrdine.setPrezzoAcquisto(result.getFloat("prezzo_acquisto"));
					dettaglioOrdine.setIvaAcquisto(result.getInt("iva_acquisto"));
				}
			}
		}
		return dettaglioOrdine;
	}

	@Override
	public List<DettaglioOrdine> doRetrieveAll() throws SQLException {
		String query = "SELECT d.*, p.nome AS nome_prodotto, p.descrizione AS descrizione_prodotto, p.taglia, p.colore "
				+ "FROM dettaglio_ordine d "
				+ "JOIN prodotto p ON d.prodotto = p.id_prodotto";
		List<DettaglioOrdine> list = new ArrayList<DettaglioOrdine>();
		DettaglioOrdine dettaglioOrdine = null;
		
		try(Connection connection = ConnectionPool.getConnection()){
			Statement statement = connection.createStatement();
			
			try(ResultSet result = statement.executeQuery(query)){
				while(result.next()) {
					dettaglioOrdine = new DettaglioOrdine();
					dettaglioOrdine.setIdDettaglioOrdine(result.getLong("id_dettaglio_ordine"));
					dettaglioOrdine.setOrdine(result.getLong("ordine"));
					
					Prodotto prodotto = new Prodotto();
					prodotto.setIdProdotto(result.getLong("prodotto"));
					prodotto.setNome(result.getString("nome_prodotto"));
					prodotto.setDescrizione(result.getString("descrizione_prodotto"));
					prodotto.setTaglia(result.getString("taglia"));
					prodotto.setColore(result.getString("colore"));
					
					dettaglioOrdine.setProdotto(prodotto);
					dettaglioOrdine.setQuantita(result.getInt("quantita"));
					dettaglioOrdine.setPrezzoAcquisto(result.getFloat("prezzo_acquisto"));
					dettaglioOrdine.setIvaAcquisto(result.getInt("iva_acquisto"));
					
					list.add(dettaglioOrdine);
				}
			}
		}
		return list;
	}

	@Override
	public void doSave(DettaglioOrdine item) throws SQLException {
		String query = "INSERT INTO dettaglio_ordine (ordine, prodotto, quantita, prezzo_acquisto, iva_acquisto) VALUES (?, ?, ?, ?, ?)";
		
		try(Connection connection = ConnectionPool.getConnection()){
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			
			preparedStatement.setLong(1, item.getOrdine());
			preparedStatement.setLong(2, item.getProdotto().getIdProdotto());
			preparedStatement.setInt(3, item.getQuantita());
			preparedStatement.setFloat(4, item.getPrezzoAcquisto());
			preparedStatement.setInt(5, item.getIvaAcquisto());
			
			preparedStatement.executeUpdate();
		}
	}

	@Override
	public void doUpdate(DettaglioOrdine item) throws SQLException {
		String query = "UPDATE dettaglio_ordine SET	ordine = ?, prodotto = ?, quantita = ?, prezzo_acquisto = ?, iva_acquisto = ? WHERE id_dettaglio_ordine = ?";
		
		try(Connection connection = ConnectionPool.getConnection()){
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			
			preparedStatement.setLong(1, item.getOrdine());
			preparedStatement.setLong(2, item.getProdotto().getIdProdotto());
			preparedStatement.setInt(3, item.getQuantita());
			preparedStatement.setFloat(4, item.getPrezzoAcquisto());
			preparedStatement.setInt(5, item.getIvaAcquisto());
			preparedStatement.setLong(6, item.getIdDettaglioOrdine());
			
			preparedStatement.executeUpdate();
		}
	}

	@Override
	public void doDelete(Long key) throws SQLException {
		String query = "DELETE FROM dettaglio_ordine WHERE id_dettaglio_ordine = ?";
		
		try(Connection connection = ConnectionPool.getConnection()){
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			
			preparedStatement.setLong(1, key);
			
			preparedStatement.executeUpdate();
		}
	}
}
