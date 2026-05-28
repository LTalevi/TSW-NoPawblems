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
import model.varianteprodotto.VarianteProdotto;

public class ProdottoDAO implements InterfaceDAO<Prodotto, Long> {

	@Override
	public Prodotto doRetrieveByKey(Long key) throws SQLException {
		String query = "SELECT p.*, "
				+ "c.id_categoria, c.id_padre, c.nome AS nome_categoria, c.descrizione AS descrizione_categoria, "
				+ "v.id_variante, v.taglia, v.colore, v.colore_hex, v.prezzo, v.iva, v.disponibilita, "
				+ "i.id_immagine, i.url, i.alt "
				+ "FROM prodotto p "
				+ "JOIN categoria c ON p.categoria = c.id_categoria "
				+ "LEFT JOIN variante_prodotto v ON p.id_prodotto = v.prodotto_padre "
				+ "LEFT JOIN immagine i ON p.id_prodotto = i.prodotto "
				+ "WHERE p.id_prodotto = ?";
		
		Prodotto prodotto = null;
		Map<Long, VarianteProdotto> mappaVarianti = new LinkedHashMap<>();
		Map<Long, Immagine> mappaImmagini = new LinkedHashMap<>();
		
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
						prodotto.setActive(result.getBoolean("attivo"));
					
						Categoria categoria = new Categoria();
						categoria.setIdCategoria(result.getLong("id_categoria"));
						categoria.setIdPadre(result.getLong("id_padre"));
						categoria.setNome(result.getString("nome_categoria"));
						categoria.setDescrizione(result.getString("descrizione_categoria"));
					
						prodotto.setCategoria(categoria);
					}

					long idVariante = result.getLong("id_variante");
					if (idVariante != 0 && !result.wasNull() && !mappaVarianti.containsKey(idVariante)) {
						VarianteProdotto variante = new VarianteProdotto();
						variante.setIdVariante(idVariante);
						variante.setProdottoPadre(prodotto.getIdProdotto());
						variante.setTaglia(result.getString("taglia"));
						variante.setColore(result.getString("colore"));
						variante.setColoreHex(result.getString("colore_hex"));
						variante.setPrezzo(result.getFloat("prezzo"));
						variante.setIva(result.getInt("iva"));
						variante.setDisponibilita(result.getInt("disponibilita"));
						
						mappaVarianti.put(idVariante, variante);
						prodotto.getVarianti().add(variante);
					}

					long idImmagine = result.getLong("id_immagine");
					if (idImmagine != 0 && !result.wasNull() && !mappaImmagini.containsKey(idImmagine)) {
						Immagine immagine = new Immagine();
						immagine.setIdImmagine(idImmagine);
						immagine.setUrl(result.getString("url"));
						immagine.setAlt(result.getString("alt"));
						
						mappaImmagini.put(idImmagine, immagine);
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
				+ "v.id_variante, v.taglia, v.colore, v.colore_hex, v.prezzo, v.iva, v.disponibilita, "
				+ "i.id_immagine, i.url, i.alt "
				+ "FROM prodotto p "
				+ "JOIN categoria c ON p.categoria = c.id_categoria "
				+ "LEFT JOIN variante_prodotto v ON p.id_prodotto = v.prodotto_padre "
				+ "LEFT JOIN immagine i ON p.id_prodotto = i.prodotto "
				+ "ORDER BY p.id_prodotto";
		
		Map<Long, Prodotto> mappaProdotti = new LinkedHashMap<>();
		Map<Long, Map<Long, VarianteProdotto>> variantiPerProdotto = new LinkedHashMap<>();
		Map<Long, Map<Long, Immagine>> immaginiPerProdotto = new LinkedHashMap<>();
		
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
						prodotto.setActive(result.getBoolean("attivo"));
					
						Categoria categoria = new Categoria();
						categoria.setIdCategoria(result.getLong("id_categoria"));
						categoria.setIdPadre(result.getLong("id_padre")); 
						categoria.setNome(result.getString("nome_categoria"));
						categoria.setDescrizione(result.getString("descrizione_categoria")); 
					
						prodotto.setCategoria(categoria);
						mappaProdotti.put(idProdotto, prodotto);
						
						variantiPerProdotto.put(idProdotto, new LinkedHashMap<>());
						immaginiPerProdotto.put(idProdotto, new LinkedHashMap<>());
					}

					long idVariante = result.getLong("id_variante");
					if (idVariante != 0 && !result.wasNull()) {
						Map<Long, VarianteProdotto> mappaVar = variantiPerProdotto.get(idProdotto);
						if(!mappaVar.containsKey(idVariante)) {
							VarianteProdotto variante = new VarianteProdotto();
							variante.setIdVariante(idVariante);
							variante.setProdottoPadre(idProdotto);
							variante.setTaglia(result.getString("taglia"));
							variante.setColore(result.getString("colore"));
							variante.setColoreHex(result.getString("colore_hex"));
							variante.setPrezzo(result.getFloat("prezzo"));
							variante.setIva(result.getInt("iva"));
							variante.setDisponibilita(result.getInt("disponibilita"));
							
							mappaVar.put(idVariante, variante);
							prodotto.getVarianti().add(variante);
						}
					}

					long idImmagine = result.getLong("id_immagine");
					if (idImmagine != 0 && !result.wasNull()) {
						Map<Long, Immagine> mappaImg = immaginiPerProdotto.get(idProdotto);
						if(!mappaImg.containsKey(idImmagine)) {
							Immagine immagine = new Immagine();
							immagine.setIdImmagine(idImmagine);
							immagine.setUrl(result.getString("url"));
							immagine.setAlt(result.getString("alt"));
							
							mappaImg.put(idImmagine, immagine);
							prodotto.getImmagini().add(immagine);
						}
					}
				}
			}
		}
		return new ArrayList<>(mappaProdotti.values());
	}

	@Override
	public void doSave(Prodotto item) throws SQLException {
	    String query = "INSERT INTO prodotto (categoria, nome, descrizione, attivo) VALUES (?, ?, ?, ?)";
	    
	    try(Connection connection = ConnectionPool.getConnection()){
	        PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
	        
	        preparedStatement.setLong(1, item.getCategoria().getIdCategoria());
	        preparedStatement.setString(2, item.getNome());
	        preparedStatement.setString(3, item.getDescrizione());
	        preparedStatement.setBoolean(4, item.isActive());
	        
	        preparedStatement.executeUpdate();
	        
	        try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
	            if (generatedKeys.next()) {
	                item.setIdProdotto(generatedKeys.getLong(1));
	            } else {
	                throw new SQLException("Creazione prodotto fallita, ID non generato.");
	            }
	        }
	    }
	}

	@Override
	public void doUpdate(Prodotto item) throws SQLException {
		String query = "UPDATE prodotto SET categoria = ?, nome = ?, descrizione = ?, attivo = ? WHERE id_prodotto = ?";
		
		try(Connection connection = ConnectionPool.getConnection()){
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			
			preparedStatement.setLong(1, item.getCategoria().getIdCategoria());
			preparedStatement.setString(2, item.getNome());
			preparedStatement.setString(3, item.getDescrizione());
			preparedStatement.setBoolean(4, item.isActive());
			preparedStatement.setLong(5, item.getIdProdotto());
			
			preparedStatement.executeUpdate();
		}
	}

	@Override
	public void doDelete(Long key) throws SQLException {
		String query = "UPDATE prodotto SET attivo = false WHERE id_prodotto = ?";
		
		try(Connection connection = ConnectionPool.getConnection()){
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			
			preparedStatement.setLong(1, key);
			
			preparedStatement.executeUpdate();
		}
	}
	
	public List<Prodotto> doRetrieveByFilter(Long idCategoria, Long idPadre, Float prezzoMin, Float prezzoMax, String ricerca, String ordinamento) throws SQLException {
		String query = "SELECT DISTINCT p.*, "
				+ "c.id_categoria, c.id_padre, c.nome AS nome_categoria, c.descrizione AS descrizione_categoria, "
				+ "v.id_variante, v.taglia, v.colore, v.colore_hex, v.prezzo, v.iva, v.disponibilita, "
				+ "i.id_immagine, i.url, i.alt "
				+ "FROM prodotto p "
				+ "JOIN categoria c ON p.categoria = c.id_categoria "
				+ "LEFT JOIN variante_prodotto v ON p.id_prodotto = v.prodotto_padre "
				+ "LEFT JOIN immagine i ON p.id_prodotto = i.prodotto "
				+ "WHERE p.attivo = true";
		
		List<Object> parametri = new ArrayList<>();
		
		if (idCategoria != null && idCategoria > 0) {
			query += " AND p.categoria = ?";
			parametri.add(idCategoria);
		}
		
		if (idPadre != null && idPadre > 0) {
			query += " AND c.id_padre = ?";
			parametri.add(idPadre);
		}
		
		if (prezzoMin != null && prezzoMin >= 0) {
			query += " AND v.prezzo >= ?";
			parametri.add(prezzoMin);
		}
		
		if (prezzoMax != null && prezzoMax >= 0) {
			query += " AND v.prezzo <= ?";
			parametri.add(prezzoMax);
		}
		
		if (ricerca != null && !ricerca.trim().isEmpty()) {
			query += " AND p.nome LIKE ?";
			parametri.add("%" + ricerca.trim() + "%"); 
		}
		
		if (ordinamento != null && !ordinamento.trim().isEmpty()) {
			switch (ordinamento) {
				case "prezzoCrescente":
					query += " ORDER BY v.prezzo ASC, p.id_prodotto ASC";
					break;
				case "prezzoDecrescente":
					query += " ORDER BY v.prezzo DESC, p.id_prodotto ASC";
					break;
				case "nomeAZ":
					query += " ORDER BY p.nome ASC";
					break;
				case "nomeZA":
					query += " ORDER BY p.nome DESC";
					break;
				default:
					query += " ORDER BY p.id_prodotto ASC"; 
					break;
			}
		} else {
			query += " ORDER BY p.id_prodotto ASC";
		}
		
		Map<Long, Prodotto> mappaProdotti = new LinkedHashMap<>();
		Map<Long, Map<Long, VarianteProdotto>> variantiPerProdotto = new LinkedHashMap<>();
		Map<Long, Map<Long, Immagine>> immaginiPerProdotto = new LinkedHashMap<>();

		try (Connection connection = ConnectionPool.getConnection()) {
			PreparedStatement preparedStatement = connection.prepareStatement(query);

			for (int i = 0; i < parametri.size(); i++) {
				preparedStatement.setObject(i + 1, parametri.get(i));
			}

			try (ResultSet result = preparedStatement.executeQuery()) {
				while (result.next()) {
					long idProdotto = result.getLong("id_prodotto");
					Prodotto prodotto = mappaProdotti.get(idProdotto);

					if (prodotto == null) {
						prodotto = new Prodotto();
						prodotto.setIdProdotto(idProdotto);
						prodotto.setNome(result.getString("nome"));
						prodotto.setDescrizione(result.getString("descrizione"));
						prodotto.setActive(result.getBoolean("attivo"));

						Categoria categoria = new Categoria();
						categoria.setIdCategoria(result.getLong("id_categoria"));
						categoria.setIdPadre(result.getLong("id_padre")); 
						categoria.setNome(result.getString("nome_categoria"));
						categoria.setDescrizione(result.getString("descrizione_categoria")); 

						prodotto.setCategoria(categoria);
						mappaProdotti.put(idProdotto, prodotto);
						
						variantiPerProdotto.put(idProdotto, new LinkedHashMap<>());
						immaginiPerProdotto.put(idProdotto, new LinkedHashMap<>());
					}

					long idVariante = result.getLong("id_variante");
					if (idVariante != 0 && !result.wasNull()) {
						Map<Long, VarianteProdotto> mappaVar = variantiPerProdotto.get(idProdotto);
						if(!mappaVar.containsKey(idVariante)) {
							VarianteProdotto variante = new VarianteProdotto();
							variante.setIdVariante(idVariante);
							variante.setProdottoPadre(idProdotto);
							variante.setTaglia(result.getString("taglia"));
							variante.setColore(result.getString("colore"));
							variante.setColoreHex(result.getString("colore_hex"));
							variante.setPrezzo(result.getFloat("prezzo"));
							variante.setIva(result.getInt("iva"));
							variante.setDisponibilita(result.getInt("disponibilita"));
							
							mappaVar.put(idVariante, variante);
							prodotto.getVarianti().add(variante);
						}
					}

					long idImmagine = result.getLong("id_immagine");
					if (idImmagine != 0 && !result.wasNull()) {
						Map<Long, Immagine> mappaImg = immaginiPerProdotto.get(idProdotto);
						if(!mappaImg.containsKey(idImmagine)) {
							Immagine immagine = new Immagine();
							immagine.setIdImmagine(idImmagine);
							immagine.setUrl(result.getString("url"));
							immagine.setAlt(result.getString("alt"));
							
							mappaImg.put(idImmagine, immagine);
							prodotto.getImmagini().add(immagine);
						}
					}
				}
			}
		}
		return new ArrayList<>(mappaProdotti.values());
	}
}