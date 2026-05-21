package model.ordine;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Ordine implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private long idOrdine;
	private long utente;
	private long indirizzo;
	private LocalDateTime dataOrdine;
	private String stato;
	private float totale;
	private String numerFattura;
	
	public Ordine() {
		
	}
	
	public Ordine(long id_ordine, long utente, long indirizzo, LocalDateTime dataOrdine, String stato, float totale,
			String numerFattura) {
		super();
		this.idOrdine = id_ordine;
		this.utente = utente;
		this.indirizzo = indirizzo;
		this.dataOrdine = dataOrdine;
		this.stato = stato;
		this.totale = totale;
		this.numerFattura = numerFattura;
	}

	public long getIdOrdine() {
		return idOrdine;
	}

	public void setIdOrdine(long idOrdine) {
		this.idOrdine = idOrdine;
	}

	public long getUtente() {
		return utente;
	}

	public void setUtente(long utente) {
		this.utente = utente;
	}

	public long getIndirizzo() {
		return indirizzo;
	}

	public void setIndirizzo(long indirizzo) {
		this.indirizzo = indirizzo;
	}

	public LocalDateTime getDataOrdine() {
		return dataOrdine;
	}

	public void setDataOrdine(LocalDateTime dataOrdine) {
		this.dataOrdine = dataOrdine;
	}

	public String getStato() {
		return stato;
	}

	public void setStato(String stato) {
		this.stato = stato;
	}

	public float getTotale() {
		return totale;
	}

	public void setTotale(float totale) {
		this.totale = totale;
	}

	public String getNumerFattura() {
		return numerFattura;
	}

	public void setNumerFattura(String numerFattura) {
		this.numerFattura = numerFattura;
	}

	@Override
	public String toString() {
		return "Ordine [idOrdine=" + idOrdine + ", utente=" + utente + ", indirizzo=" + indirizzo + ", dataOrdine="
				+ dataOrdine + ", stato=" + stato + ", totale=" + totale + ", numerFattura=" + numerFattura + "]";
	}
}
