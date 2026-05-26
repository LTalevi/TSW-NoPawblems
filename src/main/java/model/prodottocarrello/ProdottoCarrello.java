package model.prodottocarrello;

import java.io.Serializable;

import model.varianteprodotto.VarianteProdotto;

public class ProdottoCarrello implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long utente;
	private VarianteProdotto variante;
	private int quantita;
	
	public ProdottoCarrello() {
	}
	
	public ProdottoCarrello(Long utente, VarianteProdotto variante, int quantita) {
		super();
		this.utente = utente;
		this.variante = variante;
		this.quantita = quantita;
	}

	public Long getUtente() {
		return utente;
	}

	public void setUtente(Long utente) {
		this.utente = utente;
	}

	public VarianteProdotto getVariante() {
		return variante;
	}

	public void setVariante(VarianteProdotto variante) {
		this.variante = variante;
	}

	public int getQuantita() {
		return quantita;
	}

	public void setQuantita(int quantita) {
		this.quantita = quantita;
	}

	@Override
	public String toString() {
		return "ProdottoCarrello [utente=" + utente + ", variante=" + variante + ", quantita=" + quantita + "]";
	}
}