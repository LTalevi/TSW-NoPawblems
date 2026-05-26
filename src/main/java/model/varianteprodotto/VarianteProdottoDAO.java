package model.varianteprodotto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.ConnectionPool;
import model.InterfaceDAO;

public class VarianteProdottoDAO implements InterfaceDAO<VarianteProdotto, Long> {

    @Override
    public VarianteProdotto doRetrieveByKey(Long key) throws SQLException {
        String query = "SELECT * FROM variante_prodotto WHERE id_variante = ?";
        
        VarianteProdotto varianteProdotto = null;
        try (Connection connection = ConnectionPool.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, key);
            try (ResultSet result = preparedStatement.executeQuery()) {
                if (result.next()) {
                    varianteProdotto = new VarianteProdotto();
                    varianteProdotto.setIdVariante(result.getLong("id_variante"));
                    varianteProdotto.setProdottoPadre(result.getLong("prodotto_padre"));
                    varianteProdotto.setTaglia(result.getString("taglia"));
                    varianteProdotto.setColore(result.getString("colore"));
                    varianteProdotto.setColoreHex(result.getString("colore_hex"));
                    varianteProdotto.setPrezzo(result.getFloat("prezzo"));
                    varianteProdotto.setIva(result.getInt("iva"));
                    varianteProdotto.setDisponibilita(result.getInt("disponibilita"));
                }
            }
        }
        return varianteProdotto;
    }

    @Override
    public List<VarianteProdotto> doRetrieveAll() throws SQLException {
        String query = "SELECT * FROM variante_prodotto";
        
        VarianteProdotto varianteProdotto;
        List<VarianteProdotto> lista = new ArrayList<>();
        try (Connection connection = ConnectionPool.getConnection();
             Statement statement = connection.createStatement();
             ResultSet result = statement.executeQuery(query)) {
            while (result.next()) {
                varianteProdotto = new VarianteProdotto();
                varianteProdotto.setIdVariante(result.getLong("id_variante"));
                varianteProdotto.setProdottoPadre(result.getLong("prodotto_padre"));
                varianteProdotto.setTaglia(result.getString("taglia"));
                varianteProdotto.setColore(result.getString("colore"));
                varianteProdotto.setColoreHex(result.getString("colore_hex"));
                varianteProdotto.setPrezzo(result.getFloat("prezzo"));
                varianteProdotto.setIva(result.getInt("iva"));
                varianteProdotto.setDisponibilita(result.getInt("disponibilita"));
                
                lista.add(varianteProdotto);
            }
        }
        return lista;
    }

    public List<VarianteProdotto> doRetrieveByProdotto(Long idProdottoPadre) throws SQLException {
        String query = "SELECT * FROM variante_prodotto WHERE prodotto_padre = ?";
        
        VarianteProdotto varianteProdotto;
        List<VarianteProdotto> lista = new ArrayList<>();
        try (Connection connection = ConnectionPool.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, idProdottoPadre);
            try (ResultSet result = preparedStatement.executeQuery()) {
                while (result.next()) {
                    varianteProdotto = new VarianteProdotto();
                    varianteProdotto.setIdVariante(result.getLong("id_variante"));
                    varianteProdotto.setProdottoPadre(result.getLong("prodotto_padre"));
                    varianteProdotto.setTaglia(result.getString("taglia"));
                    varianteProdotto.setColore(result.getString("colore"));
                    varianteProdotto.setColoreHex(result.getString("colore_hex"));
                    varianteProdotto.setPrezzo(result.getFloat("prezzo"));
                    varianteProdotto.setIva(result.getInt("iva"));
                    varianteProdotto.setDisponibilita(result.getInt("disponibilita"));
                    
                    lista.add(varianteProdotto);
                }
            }
        }
        return lista;
    }

    @Override
    public void doSave(VarianteProdotto item) throws SQLException {
        String query = "INSERT INTO variante_prodotto (prodotto_padre, taglia, colore, colore_hex, prezzo, iva, disponibilita) VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection connection = ConnectionPool.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setLong(1, item.getProdottoPadre());
            preparedStatement.setString(2, item.getTaglia());
            preparedStatement.setString(3, item.getColore());
            preparedStatement.setString(4, item.getColoreHex());
            preparedStatement.setFloat(5, item.getPrezzo());
            preparedStatement.setInt(6, item.getIva());
            preparedStatement.setInt(7, item.getDisponibilita());
            
            preparedStatement.executeUpdate();
            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    item.setIdVariante(generatedKeys.getLong(1));
                }
            }
        }
    }

    @Override
    public void doUpdate(VarianteProdotto item) throws SQLException {
        String query = "UPDATE variante_prodotto SET taglia = ?, colore = ?, colore_hex = ?, prezzo = ?, iva = ?, disponibilita = ? WHERE id_variante = ?";
        
        try (Connection connection = ConnectionPool.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, item.getTaglia());
            preparedStatement.setString(2, item.getColore());
            preparedStatement.setString(3, item.getColoreHex());
            preparedStatement.setFloat(4, item.getPrezzo());
            preparedStatement.setInt(5, item.getIva());
            preparedStatement.setInt(6, item.getDisponibilita());
            preparedStatement.setLong(7, item.getIdVariante());
            
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public void doDelete(Long key) throws SQLException {
        String query = "DELETE FROM variante_prodotto WHERE id_variante = ?";
        
        try (Connection connection = ConnectionPool.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, key);
            preparedStatement.executeUpdate();
        }
    }
}