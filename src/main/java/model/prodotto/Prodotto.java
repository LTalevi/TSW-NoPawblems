package model.prodotto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import model.categoria.Categoria;
import model.immagine.Immagine;
import model.varianteprodotto.VarianteProdotto;

public class Prodotto implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private long idProdotto;
	private Categoria categoria;
	private String nome;
	private String descrizione;
	private boolean isActive;
	private List<Immagine> immagini;
	private List<VarianteProdotto> varianti; 
	
	public Prodotto() {
		immagini = new ArrayList<>();
		varianti = new ArrayList<>();
	}
	
	public Prodotto(long idProdotto, Categoria categoria, String nome, String descrizione, boolean isActive, 
			List<Immagine> immagini, List<VarianteProdotto> varianti) {
		super();
		this.idProdotto = idProdotto;
		this.categoria = categoria;
		this.nome = nome;
		this.descrizione = descrizione;
		this.isActive = isActive;
		this.immagini = immagini != null ? immagini : new ArrayList<>();
		this.varianti = varianti != null ? varianti : new ArrayList<>();
	}

	public long getIdProdotto() {
		return idProdotto;
	}

	public void setIdProdotto(long idProdotto) {
		this.idProdotto = idProdotto;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
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

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public List<Immagine> getImmagini() {
		return immagini;
	}

	public void setImmagini(List<Immagine> immagini) {
		this.immagini = immagini;
	}

	public List<VarianteProdotto> getVarianti() {
		return varianti;
	}

	public void setVarianti(List<VarianteProdotto> varianti) {
		this.varianti = varianti;
	}

	@Override
	public String toString() {
		return "Prodotto [idProdotto=" + idProdotto + ", categoria=" + categoria + ", nome=" + nome + ", descrizione="
				+ descrizione + ", isActive=" + isActive + ", immagini=" + immagini + ", varianti=" + varianti + "]";
	}
}