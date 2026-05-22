package model.immagine;

import java.io.Serializable;

public class Immagine implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private long idImmagine;
	private long prodotto;
	private String url;
	private String alt;
	
	public Immagine() {
		
	}

	public Immagine(long idImmagine, long prodotto, String url, String alt) {
		super();
		this.idImmagine = idImmagine;
		this.prodotto = prodotto;
		this.url = url;
		this.alt = alt;
	}

	public long getIdImmagine() {
		return idImmagine;
	}

	public void setIdImmagine(long id_immagine) {
		this.idImmagine = id_immagine;
	}

	public long getProdotto() {
		return prodotto;
	}

	public void setProdotto(long prodotto) {
		this.prodotto = prodotto;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getAlt() {
		return alt;
	}

	public void setAlt(String alt) {
		this.alt = alt;
	}

	@Override
	public String toString() {
		return "Immagine [idImmagine=" + idImmagine + ", prodotto=" + prodotto + ", url=" + url + ", alt=" + alt
				+ "]";
	}
}
