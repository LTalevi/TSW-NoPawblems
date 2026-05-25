package model.prodotto;

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
import model.categoria.Categoria;
import model.immagine.Immagine;

public class ProdottoDAO implements InterfaceDAO<Prodotto, Long> {

	@Override
	public Prodotto doRetrieveByKey(Long key) throws SQLException {
		String query = "SELECT p.*, "
				+ "c.id_categoria, c.id_padre, c.nome AS nome_categoria, c.descrizione AS descrizione_categoria, "
				+ "i.id_immagine, i.url, i.alt "
				+ "FROM prodotto p "
				+ "JOIN categoria c "
				+ "ON p.categoria = c.id_categoria "
				+ "LEFT JOIN immagine i "
				+ "ON p.id_prodotto = i.prodotto "
                + "WHERE p.id_prodotto = ?";
		
		Prodotto prodotto = null;
		Categoria categoria = null;
		
		try(Connection connection = ConnectionPool.getConnection()){
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			
			preparedStatement.setLong(1, key);
			
			try(ResultSet result = preparedStatement.executeQuery()){
				while(result.next()) {			
					if (prodotto == null) {
						prodotto = new Prodotto();
						prodotto.setIdProdotto(result.getLong("id_prodotto"));
						prodotto.setNome(result.getString("nome"));
						prodotto.setDescrizione(result.getString("descrizione"));
						prodotto.setTaglia(result.getString("taglia"));
						prodotto.setColore(result.getString("colore"));
						prodotto.setPrezzo(result.getFloat("prezzo"));
						prodotto.setIva(result.getInt("iva"));
						prodotto.setDisponibilita(result.getInt("disponibilita"));
						prodotto.setActive(result.getBoolean("attivo"));
					
						categoria = new Categoria();
						categoria.setIdCategoria(result.getLong("id_categoria"));
						categoria.setIdPadre(result.getLong("id_padre"));
						categoria.setNome(result.getString("nome_categoria"));
						categoria.setDescrizione(result.getString("descrizione_categoria"));
                    
						prodotto.setCategoria(categoria);
					}
					
					long idImmmagine = result.getLong("id_immagine");
	                if (idImmmagine != 0 && !result.wasNull()) {
	                    Immagine immagine = new Immagine();
	                    immagine.setIdImmagine(idImmmagine);
	                    immagine.setUrl(result.getString("url"));
	                    immagine.setAlt(result.getString("alt"));
	                    
	                    prodotto.getImmagini().add(immagine);
	                }
				}
			}
		}
		return prodotto;
	}

	@Override
	public List<Prodotto> doRetrieveAll() throws SQLException {
		String query = "SELECT p.*, "
				+ "c.id_categoria, c.id_padre, c.nome AS nome_categoria, c.descrizione AS descrizione_categoria, "
				+ "i.id_immagine, i.url, i.alt "
				+ "FROM prodotto p "
				+ "JOIN categoria c "
				+ "ON p.categoria = c.id_categoria "
				+ "LEFT JOIN immagine i "
				+ "ON p.id_prodotto = i.prodotto "
				+ "ORDER BY p.id_prodotto";
		
		Map<Long, Prodotto> mappaProdotti = new LinkedHashMap<>();
		
		try(Connection connection = ConnectionPool.getConnection()){
			Statement statement = connection.createStatement();
			
			try(ResultSet result = statement.executeQuery(query)){
				while(result.next()) {
					long idProdotto = result.getLong("id_prodotto");

		            Prodotto prodotto = mappaProdotti.get(idProdotto);
		            
		            if (prodotto == null) {
		                prodotto = new Prodotto();
		                prodotto.setIdProdotto(idProdotto);
		                prodotto.setNome(result.getString("nome"));
		                prodotto.setDescrizione(result.getString("descrizione"));
		                prodotto.setTaglia(result.getString("taglia"));
		                prodotto.setColore(result.getString("colore"));
		                prodotto.setPrezzo(result.getFloat("prezzo"));
		                prodotto.setIva(result.getInt("iva"));
		                prodotto.setDisponibilita(result.getInt("disponibilita"));
		                prodotto.setActive(result.getBoolean("attivo"));
		            
		                Categoria categoria = new Categoria();
		                categoria.setIdCategoria(result.getLong("id_categoria"));
		                categoria.setIdPadre(result.getLong("id_padre")); 
		                categoria.setNome(result.getString("nome_categoria"));
		                categoria.setDescrizione(result.getString("descrizione_categoria")); 
		            
		                prodotto.setCategoria(categoria);
		
		                mappaProdotti.put(idProdotto, prodotto);
		            }

		            long idImmagine = result.getLong("id_immagine");
		            if (idImmagine != 0 && !result.wasNull()) {
		                Immagine immagine = new Immagine();
		                immagine.setIdImmagine(idImmagine);
		                immagine.setUrl(result.getString("url"));
		                immagine.setAlt(result.getString("alt"));
		                
		                prodotto.getImmagini().add(immagine);
		            }
				}
			}
		}
		return new ArrayList<>(mappaProdotti.values());
	}

	@Override
	public void doSave(Prodotto item) throws SQLException {
		String query = "INSERT INTO prodotto (categoria, nome, descrizione, taglia, colore, prezzo, iva, disponibilita, attivo) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
		
		try(Connection connection = ConnectionPool.getConnection()){
			PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			
			preparedStatement.setLong(1, item.getCategoria().getIdCategoria());
			preparedStatement.setString(2, item.getNome());
			preparedStatement.setString(3, item.getDescrizione());
			preparedStatement.setString(4, item.getTaglia());
			preparedStatement.setString(5, item.getColore());
			preparedStatement.setFloat(6, item.getPrezzo());
			preparedStatement.setInt(7, item.getIva());
			preparedStatement.setInt(8, item.getDisponibilita());
			preparedStatement.setBoolean(9, item.isActive());
			
			preparedStatement.executeUpdate();
			
			try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
	            if (generatedKeys.next()) {
	                item.setIdProdotto(generatedKeys.getLong(1));
	            } else {
	                throw new SQLException("Creazione prodotto fallita");
	            }
	        }
		}
	}

	@Override
	public void doUpdate(Prodotto item) throws SQLException {
		String query = "UPDATE prodotto SET categoria = ?, nome = ?, descrizione = ?, taglia = ?, colore = ?, prezzo = ?, iva = ?, disponibilita = ?, attivo = ? WHERE id_prodotto = ?";
		
		try(Connection connection = ConnectionPool.getConnection()){
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			
			preparedStatement.setLong(1, item.getCategoria().getIdCategoria());
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
