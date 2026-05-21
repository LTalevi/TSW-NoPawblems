package model.prodotto;

import java.io.Serializable;

public class Prodotto implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private long idProdotto;
	private long categoria;
	private String nome;
	private String descrizione;
	private String taglia;
	private String colore;
	private float prezzo;
	private int iva;
	private int disponibilita;
	private boolean isActive;
	
	public Prodotto() {
		
	}
	
	public Prodotto(long idProdotto, long categoria, String nome, String descrizione, String taglia, String colore,
			float prezzo, int iva, int disponibilita, boolean isActive) {
		super();
		this.idProdotto = idProdotto;
		this.categoria = categoria;
		this.nome = nome;
		this.descrizione = descrizione;
		this.taglia = taglia;
		this.colore = colore;
		this.prezzo = prezzo;
		this.iva = iva;
		this.disponibilita = disponibilita;
		this.isActive = isActive;
	}

	public long getIdProdotto() {
		return idProdotto;
	}

	public void setIdProdotto(long id_prodotto) {
		this.idProdotto = id_prodotto;
	}

	public long getCategoria() {
		return categoria;
	}

	public void setCategoria(long categoria) {
		this.categoria = categoria;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public String getTaglia() {
		return taglia;
	}

	public void setTaglia(String tagli) {
		this.taglia = tagli;
	}

	public String getColore() {
		return colore;
	}

	public void setColore(String colore) {
		this.colore = colore;
	}

	public float getPrezzo() {
		return prezzo;
	}

	public void setPrezzo(float prezzo) {
		this.prezzo = prezzo;
	}

	public int getIva() {
		return iva;
	}

	public void setIva(int iva) {
		this.iva = iva;
	}

	public int getDisponibilita() {
		return disponibilita;
	}

	public void setDisponibilita(int disponibilita) {
		this.disponibilita = disponibilita;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	@Override
	public String toString() {
		return "Prodotto [idProdotto=" + idProdotto + ", categoria=" + categoria + ", nome=" + nome + ", descrizione="
				+ descrizione + ", taglia=" + taglia + ", colore=" + colore + ", prezzo=" + prezzo + ", iva=" + iva
				+ ", disponibilita=" + disponibilita + ", isActive=" + isActive + "]";
	}
}
