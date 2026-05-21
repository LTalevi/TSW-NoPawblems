package model.utente;

import java.io.Serializable;

public class Utente implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private long idUtente;
	private String nome;
	private String cognome;
	private String email;
	private String telefono;
	private String password;
	private boolean isAdmin;
	
	public Utente() {
		
	}

	public Utente(long id_utente, String nome, String cognome, String email, String telefono, String password, boolean isAdmin) {
		this.idUtente = id_utente;
		this.nome = nome;
		this.cognome = cognome;
		this.email = email;
		this.telefono = telefono;
		this.password = password;
		this.isAdmin = isAdmin;
	}

	public long getId_utente() {
		return idUtente;
	}

	public void setId_utente(long id_utente) {
		this.idUtente = id_utente;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isAdmin() {
		return isAdmin;
	}

	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	@Override
	public String toString() {
		return "Utente [id_utente=" + idUtente + ", nome=" + nome + ", cognome=" + cognome + ", email=" + email
				+ ", telefono=" + telefono + ", password=" + password + ", isAdmin=" + isAdmin + "]";
	}
}
