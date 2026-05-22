package model.ordine;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Ordine implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private long idOrdine;
	private long utente;
	private String viaSpedizione;
    private String cittaSpedizione;
    private String capSpedizione;
    private String provinciaSpedizione;
    private String nazioneSpedizione;
	private LocalDateTime dataOrdine;
	private String stato;
	private float totale;
	private String numeroFattura;
	
	public Ordine() {
		
	}

	public Ordine(long idOrdine, long utente, String viaSpedizione, String cittaSpedizione, String capSpedizione,
			String provinciaSpedizione, String nazioneSpedizione, LocalDateTime dataOrdine, String stato, float totale,
			String numeroFattura) {
		super();
		this.idOrdine = idOrdine;
		this.utente = utente;
		this.viaSpedizione = viaSpedizione;
		this.cittaSpedizione = cittaSpedizione;
		this.capSpedizione = capSpedizione;
		this.provinciaSpedizione = provinciaSpedizione;
		this.nazioneSpedizione = nazioneSpedizione;
		this.dataOrdine = dataOrdine;
		this.stato = stato;
		this.totale = totale;
		this.numeroFattura = numeroFattura;
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

	public String getViaSpedizione() {
		return viaSpedizione;
	}

	public void setViaSpedizione(String viaSpedizione) {
		this.viaSpedizione = viaSpedizione;
	}

	public String getCittaSpedizione() {
		return cittaSpedizione;
	}

	public void setCittaSpedizione(String cittaSpedizione) {
		this.cittaSpedizione = cittaSpedizione;
	}

	public String getCapSpedizione() {
		return capSpedizione;
	}

	public void setCapSpedizione(String capSpedizione) {
		this.capSpedizione = capSpedizione;
	}

	public String getProvinciaSpedizione() {
		return provinciaSpedizione;
	}

	public void setProvinciaSpedizione(String provinciaSpedizione) {
		this.provinciaSpedizione = provinciaSpedizione;
	}

	public String getNazioneSpedizione() {
		return nazioneSpedizione;
	}

	public void setNazioneSpedizione(String nazioneSpedizione) {
		this.nazioneSpedizione = nazioneSpedizione;
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

	public String getNumeroFattura() {
		return numeroFattura;
	}

	public void setNumeroFattura(String numerFattura) {
		this.numeroFattura = numerFattura;
	}

	@Override
	public String toString() {
		return "Ordine [idOrdine=" + idOrdine + ", utente=" + utente + ", viaSpedizione=" + viaSpedizione
				+ ", cittaSpedizione=" + cittaSpedizione + ", capSpedizione=" + capSpedizione + ", provinciaSpedizione="
				+ provinciaSpedizione + ", nazioneSpedizione=" + nazioneSpedizione + ", dataOrdine=" + dataOrdine
				+ ", stato=" + stato + ", totale=" + totale + ", numeroFattura=" + numeroFattura + "]";
	}
}
