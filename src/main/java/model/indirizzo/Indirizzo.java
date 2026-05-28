package model.indirizzo;

import java.io.Serializable;

public class Indirizzo implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private long idIndirizzo;
	private long utente;
	private String via;
	private String citta;
	private String cap;
	private String provincia;
	private String nazione;
	
	public Indirizzo() {
		
	}
	
	public Indirizzo(long idIndirizzo, long utente, String via, String citta, String cap, String provincia,
			String nazione) {
		super();
		this.idIndirizzo = idIndirizzo;
		this.utente = utente;
		this.via = via;
		this.citta = citta;
		this.cap = cap;
		this.provincia = provincia;
		this.nazione = nazione;
	}

	public long getIdIndirizzo() {
		return idIndirizzo;
	}

	public void setIdIndirizzo(long id_indirizzo) {
		this.idIndirizzo = id_indirizzo;
	}

	public long getUtente() {
		return utente;
	}

	public void setUtente(long utetnte) {
		this.utente = utetnte;
	}

	public String getVia() {
		return via;
	}

	public void setVia(String via) {
		this.via = via;
	}

	public String getCitta() {
		return citta;
	}

	public void setCitta(String citta) {
		this.citta = citta;
	}

	public String getCap() {
		return cap;
	}

	public void setCap(String cap) {
		this.cap = cap;
	}

	public String getProvincia() {
		return provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	public String getNazione() {
		return nazione;
	}

	public void setNazione(String nazione) {
		this.nazione = nazione;
	}

	@Override
	public String toString() {
		return "Indirizzo [idIndirizzo=" + idIndirizzo + ", utente=" + utente + ", via=" + via + ", citta=" + citta
				+ ", cap=" + cap + ", provincia=" + provincia + ", nazione=" + nazione + "]";
	}
}
