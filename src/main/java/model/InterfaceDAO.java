package model;

import java.sql.SQLException;
import java.util.List;

public interface InterfaceDAO<T, K> {
	public T doRetrieveByKey(K key) throws SQLException;
	public List<T> doRetrieveAll() throws SQLException;
	public void doSave(T item) throws SQLException;
	public void doUpdate(T item) throws SQLException;
	public void doDelete(K key) throws SQLException;
}
