package model.dettaglioordine;

import java.io.Serializable;

public class DettaglioOrdine implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private long idDettaglioOrdine;
	private long ordine;
	private long prodotto;
	private int quantita;
	private float prezzoAcquisto;
	private int ivaAcquisto;
	
	public DettaglioOrdine() {
		
	}

	public DettaglioOrdine(long idDettaglioOrdine, long ordine, long prodotto, int quantita, float prezzoAcquisto,
			int ivaAcquisto) {
		super();
		this.idDettaglioOrdine = idDettaglioOrdine;
		this.ordine = ordine;
		this.prodotto = prodotto;
		this.quantita = quantita;
		this.prezzoAcquisto = prezzoAcquisto;
		this.ivaAcquisto = ivaAcquisto;
	}

	public long getIdDettaglioOrdine() {
		return idDettaglioOrdine;
	}

	public void setIdDettaglioOrdine(long idDettaglioOrdine) {
		this.idDettaglioOrdine = idDettaglioOrdine;
	}

	public long getOrdine() {
		return ordine;
	}

	public void setOrdine(long ordine) {
		this.ordine = ordine;
	}

	public long getProdotto() {
		return prodotto;
	}

	public void setProdotto(long prodotto) {
		this.prodotto = prodotto;
	}

	public int getQuantita() {
		return quantita;
	}

	public void setQuantita(int quantita) {
		this.quantita = quantita;
	}

	public float getPrezzoAcquisto() {
		return prezzoAcquisto;
	}

	public void setPrezzoAcquisto(float prezzoAcquisto) {
		this.prezzoAcquisto = prezzoAcquisto;
	}

	public int getIvaAcquisto() {
		return ivaAcquisto;
	}

	public void setIvaAcquisto(int ivaAcquisto) {
		this.ivaAcquisto = ivaAcquisto;
	}

	@Override
	public String toString() {
		return "DettaglioOrdine [idDettaglioOrdine=" + idDettaglioOrdine + ", ordine=" + ordine + ", prodotto="
				+ prodotto + ", quantita=" + quantita + ", prezzoAcquisto=" + prezzoAcquisto + ", ivaAcquisto="
				+ ivaAcquisto + "]";
	}
}
