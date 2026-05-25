package model.prodottocarrello;

import java.io.Serializable;

import model.prodotto.Prodotto;

public class ProdottoCarrello implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long utente;
	private Prodotto prodotto;
	private int quantita;
	
	public ProdottoCarrello() {
		
	}
	
	public ProdottoCarrello(Long utente, Prodotto prodotto, int quantita) {
		super();
		this.utente = utente;
		this.prodotto = prodotto;
		this.quantita = quantita;
	}

	public Long getUtente() {
		return utente;
	}

	public void setUtente(Long utente) {
		this.utente = utente;
	}

	public Prodotto getProdotto() {
		return prodotto;
	}

	public void setProdotto(Prodotto prodotto) {
		this.prodotto = prodotto;
	}

	public int getQuantita() {
		return quantita;
	}

	public void setQuantita(int quantita) {
		this.quantita = quantita;
	}

	@Override
	public String toString() {
		return "ProdottoCarrello [utente=" + utente + ", prodotto=" + prodotto + ", quantita=" + quantita + "]";
	}
}
