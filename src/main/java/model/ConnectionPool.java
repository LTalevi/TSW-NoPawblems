package model;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource; 

public class ConnectionPool {
	
	private static DataSource ds;
		
	public static Connection getConnection() throws SQLException{
		if (ds == null) {
			try {
				InitialContext ctx = new InitialContext();
				ds = (DataSource) ctx.lookup("java:comp/env/jdbc/db_no_pawblems");
			
			} catch (NamingException e) {
				System.err.println("Errore nell'inizializzazione del DataSource JNDI: " + e.getMessage());
			}
		}
		
		return ds.getConnection();	
	}
}
