package model.indirizzo;

import java.io.Serializable;

public class Indirizzo implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private long idIndirizzo;
	private long utetnte;
	private String via;
	private String citta;
	private String cap;
	private String provincia;
	private String nazione;
	private boolean isActive;
	
	public Indirizzo() {
		
	}
	
	public Indirizzo(long id_indirizzo, long utetnte, String via, String citta, String cap, String provincia,
			String nazione, boolean isActive) {
		super();
		this.idIndirizzo = id_indirizzo;
		this.utetnte = utetnte;
		this.via = via;
		this.citta = citta;
		this.cap = cap;
		this.provincia = provincia;
		this.nazione = nazione;
		this.isActive = isActive;
	}

	public long getId_indirizzo() {
		return idIndirizzo;
	}

	public void setId_indirizzo(long id_indirizzo) {
		this.idIndirizzo = id_indirizzo;
	}

	public long getUtetnte() {
		return utetnte;
	}

	public void setUtetnte(long utetnte) {
		this.utetnte = utetnte;
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

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	@Override
	public String toString() {
		return "Indirizzo [id_indirizzo=" + idIndirizzo + ", utetnte=" + utetnte + ", via=" + via + ", citta=" + citta
				+ ", cap=" + cap + ", provincia=" + provincia + ", nazione=" + nazione + ", isActive=" + isActive + "]";
	}
}
