package model.varianteprodotto;

import java.io.Serializable;

public class VarianteProdotto implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private long idVariante;
	private long prodottoPadre; 
	private String taglia;
	private String colore;
	private String coloreHex;   
	private float prezzo;
	private int iva;
	private int disponibilita;
	
	public VarianteProdotto() {
	}

	public VarianteProdotto(long idVariante, long prodottoPadre, String taglia, String colore, String coloreHex,
			float prezzo, int iva, int disponibilita) {
		super();
		this.idVariante = idVariante;
		this.prodottoPadre = prodottoPadre;
		this.taglia = taglia;
		this.colore = colore;
		this.coloreHex = coloreHex;
		this.prezzo = prezzo;
		this.iva = iva;
		this.disponibilita = disponibilita;
	}

	public long getIdVariante() {
		return idVariante;
	}

	public void setIdVariante(long idVariante) {
		this.idVariante = idVariante;
	}

	public long getProdottoPadre() {
		return prodottoPadre;
	}

	public void setProdottoPadre(long prodottoPadre) {
		this.prodottoPadre = prodottoPadre;
	}

	public String getTaglia() {
		return taglia;
	}

	public void setTaglia(String taglia) {
		this.taglia = taglia;
	}

	public String getColore() {
		return colore;
	}

	public void setColore(String colore) {
		this.colore = colore;
	}

	public String getColoreHex() {
		return coloreHex;
	}

	public void setColoreHex(String coloreHex) {
		this.coloreHex = coloreHex;
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

	@Override
	public String toString() {
		return "VarianteProdotto [idVariante=" + idVariante + ", prodottoPadre=" + prodottoPadre + ", taglia=" + taglia
				+ ", colore=" + colore + ", coloreHex=" + coloreHex + ", prezzo=" + prezzo + ", iva=" + iva
				+ ", disponibilita=" + disponibilita + "]";
	}
}