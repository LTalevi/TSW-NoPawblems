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
		 	String queryProdotto = "SELECT p.*, c.id_categoria, c.id_padre, c.nome AS nome_categoria, c.descrizione AS descrizione_categoria "
			        + "FROM prodotto p "
			        + "JOIN categoria c ON p.categoria = c.id_categoria "
			        + "WHERE p.id_prodotto = ? AND p.attivo = true";
	        String queryVarianti = "SELECT * FROM variante_prodotto WHERE prodotto_padre = ?";
	        String queryImmagini = "SELECT * FROM immagine WHERE prodotto = ?";
	        Prodotto prodotto = null;
	        

	        try (Connection connection = ConnectionPool.getConnection()){
	            PreparedStatement preparedStatement = connection.prepareStatement(queryProdotto);
	             
	            preparedStatement.setLong(1, key);
	            try (ResultSet result = preparedStatement.executeQuery()) {
	                if (result.next()) {
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
	            }
	        }

	        if (prodotto == null) {
	            return null; 
	        }
	        
	        try (Connection connection = ConnectionPool.getConnection()){
	            PreparedStatement preparedStatement = connection.prepareStatement(queryVarianti);
	             
	            preparedStatement.setLong(1, key);
	            try (ResultSet result = preparedStatement.executeQuery()) {
	                while (result.next()) {
	                    VarianteProdotto variante = new VarianteProdotto();
	                    variante.setIdVariante(result.getLong("id_variante"));
	                    variante.setProdottoPadre(key);
	                    variante.setTaglia(result.getString("taglia"));
	                    variante.setColore(result.getString("colore"));
	                    variante.setColoreHex(result.getString("colore_hex"));
	                    variante.setPrezzo(result.getFloat("prezzo"));
	                    variante.setIva(result.getInt("iva"));
	                    variante.setDisponibilita(result.getInt("disponibilita"));
	                    
	                    prodotto.getVarianti().add(variante);
	                }
	            }
	        }

	        try (Connection connection = ConnectionPool.getConnection()){
	            PreparedStatement preparedStatement = connection.prepareStatement(queryImmagini);
	             
	            preparedStatement.setLong(1, key);
	            try (ResultSet result = preparedStatement.executeQuery()) {
	                while (result.next()) {
	                    Immagine immagine = new Immagine();
	                    immagine.setIdImmagine(result.getLong("id_immagine"));
	                    immagine.setUrl(result.getString("url"));
	                    immagine.setAlt(result.getString("alt"));
	                    
	                    prodotto.getImmagini().add(immagine);
	                }
	            }
	        }

	        return prodotto;
	    }

	 @Override
	 public List<Prodotto> doRetrieveAll() throws SQLException {
	     String queryProdotti = "SELECT p.*, "
	             + "c.id_categoria, c.id_padre, c.nome AS nome_categoria, c.descrizione AS descrizione_categoria, "
	             + "v.id_variante, v.taglia, v.colore, v.colore_hex, v.prezzo, v.iva, v.disponibilita "
	             + "FROM prodotto p "
	             + "JOIN categoria c ON p.categoria = c.id_categoria "
	             + "LEFT JOIN variante_prodotto v ON p.id_prodotto = v.prodotto_padre "
	             + "WHERE p.attivo = true " 
	             + "ORDER BY p.id_prodotto";
	     String queryImmagini = "SELECT img.* "
	             + "FROM immagine img "
	             + "INNER JOIN prodotto p ON img.prodotto = p.id_prodotto "
	             + "WHERE p.attivo = true"; 
	     Map<Long, Prodotto> mappaProdotti = new LinkedHashMap<>();
	     Map<Long, Map<Long, VarianteProdotto>> variantiPerProdotto = new LinkedHashMap<>();
	     
	     try (Connection connection = ConnectionPool.getConnection();
	          Statement statement = connection.createStatement();
	          ResultSet result = statement.executeQuery(queryProdotti)) {
	         
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
	             }

	             long idVariante = result.getLong("id_variante");
	             if (idVariante != 0 && !result.wasNull()) {
	                 Map<Long, VarianteProdotto> mappaVar = variantiPerProdotto.get(idProdotto);
	                 if (!mappaVar.containsKey(idVariante)) {
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
	         }
	     }

	     if (mappaProdotti.isEmpty()) {
	    	 return new ArrayList<>();
	     }
	     
	     try (Connection connection = ConnectionPool.getConnection();
	          Statement statement = connection.createStatement();
	          ResultSet result = statement.executeQuery(queryImmagini)) {
	          
	         while (result.next()) {
	             long idProdotto = result.getLong("prodotto");
	             Prodotto p = mappaProdotti.get(idProdotto);

	             if (p != null) {
	                 Immagine immagine = new Immagine();
	                 immagine.setIdImmagine(result.getLong("id_immagine"));
	                 immagine.setUrl(result.getString("url"));
	                 immagine.setAlt(result.getString("alt"));
	                 
	                 p.getImmagini().add(immagine);
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
	
	public List<Prodotto> doRetrieveByFilter(Long idCategoria, Long idPadre, Float prezzoMin, Float prezzoMax, String ricerca, String ordinamento, Boolean attivo) throws SQLException {
	    String queryProdotti = "SELECT p.*, "
	            + "c.id_categoria, c.id_padre, c.nome AS nome_categoria, c.descrizione AS descrizione_categoria, "
	            + "v.id_variante, v.taglia, v.colore, v.colore_hex, v.prezzo, v.iva, v.disponibilita "
	            + "FROM prodotto p "
	            + "JOIN categoria c ON p.categoria = c.id_categoria "
	            + "LEFT JOIN variante_prodotto v ON p.id_prodotto = v.prodotto_padre "
	            + "WHERE 1=1"; 
	            
	    String queryFiltri = "";
	    List<Object> parametri = new ArrayList<>();

	    if (attivo != null) {
	        queryFiltri += " AND p.attivo = ?";
	        parametri.add(attivo);
	    }
	    
	    if (idCategoria != null && idCategoria > 0) {
	        queryFiltri += " AND p.categoria = ?";
	        parametri.add(idCategoria);
	    }
	    
	    if (idPadre != null && idPadre > 0) {
	        queryFiltri += " AND (c.id_categoria = ? OR c.id_padre = ?)";
	        parametri.add(idPadre);
	        parametri.add(idPadre);
	    }
	    
	    if (prezzoMin != null && prezzoMin >= 0) {
	        queryFiltri += " AND v.prezzo >= ?";
	        parametri.add(prezzoMin);
	    }
	    
	    if (prezzoMax != null && prezzoMax >= 0) {
	        queryFiltri += " AND v.prezzo <= ?";
	        parametri.add(prezzoMax);
	    }
	    
	    if (ricerca != null && !ricerca.trim().isEmpty()) {
	        queryFiltri += " AND p.nome LIKE ?";
	        parametri.add("%" + ricerca.trim() + "%");
	    }
	    
	    queryProdotti += queryFiltri;
	    
	    if (ordinamento != null && !ordinamento.trim().isEmpty()) {
	        switch (ordinamento) {
	            case "prezzoCrescente":   queryProdotti += " ORDER BY v.prezzo ASC, p.id_prodotto ASC"; break;
	            case "prezzoDecrescente": queryProdotti += " ORDER BY v.prezzo DESC, p.id_prodotto ASC"; break;
	            case "nomeAZ":            queryProdotti += " ORDER BY p.nome ASC"; break;
	            case "nomeZA":            queryProdotti += " ORDER BY p.nome DESC"; break;
	            default:                  queryProdotti += " ORDER BY p.id_prodotto ASC"; break;
	        }
	    } else {
	        queryProdotti += " ORDER BY p.id_prodotto ASC";
	    }
	    
	    Map<Long, Prodotto> mappaProdotti = new LinkedHashMap<>();
	    Map<Long, Map<Long, VarianteProdotto>> variantiPerProdotto = new LinkedHashMap<>();

	    try (Connection connection = ConnectionPool.getConnection()){
	        PreparedStatement psProdotti = connection.prepareStatement(queryProdotti);

	        for (int i = 0; i < parametri.size(); i++) {
	            psProdotti.setObject(i + 1, parametri.get(i));
	        }

	        try (ResultSet result = psProdotti.executeQuery()) {
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
	                }

	                long idVariante = result.getLong("id_variante");
	                if (idVariante != 0 && !result.wasNull()) {
	                    Map<Long, VarianteProdotto> mappaVar = variantiPerProdotto.get(idProdotto);
	                    if (!mappaVar.containsKey(idVariante)) {
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
	            }
	        }
	    }

	    if (mappaProdotti.isEmpty()) return new ArrayList<>();

	    String queryImmagini = "SELECT img.* "
	            + "FROM immagine img "
	            + "INNER JOIN ("
	            + "    SELECT DISTINCT p.id_prodotto "
	            + "    FROM prodotto p "
	            + "    JOIN categoria c ON p.categoria = c.id_categoria "
	            + "    LEFT JOIN variante_prodotto v ON p.id_prodotto = v.prodotto_padre "
	            + "    WHERE 1=1" + queryFiltri 
	            + ") AS p_filtrati ON img.prodotto = p_filtrati.id_prodotto";

	    try (Connection connection = ConnectionPool.getConnection()){
	         PreparedStatement psImmagini = connection.prepareStatement(queryImmagini);

	        for (int i = 0; i < parametri.size(); i++) {
	            psImmagini.setObject(i + 1, parametri.get(i));
	        }

	        try (ResultSet result = psImmagini.executeQuery()) {
	            while (result.next()) {
	                long idProdotto = result.getLong("prodotto");
	                Prodotto p = mappaProdotti.get(idProdotto);
	                
	                if (p != null) {
	                    Immagine immagine = new Immagine();
	                    immagine.setIdImmagine(result.getLong("id_immagine"));
	                    immagine.setUrl(result.getString("url"));
	                    immagine.setAlt(result.getString("alt"));
	                    
	                    p.getImmagini().add(immagine);
	                }
	            }
	        }
	    }

	    return new ArrayList<>(mappaProdotti.values());
	}
}