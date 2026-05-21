package model.categoria;

import java.io.Serializable;

public class Categoria implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private long idCategoria;
	private long idPadre;
	private String nome;
	private String descrizione;
	
	public Categoria() {
		
	}

	public Categoria(long idCategoria, long idPadre, String nome, String descrizione) {
		super();
		this.idCategoria = idCategoria;
		this.idPadre = idPadre;
		this.nome = nome;
		this.descrizione = descrizione;
	}

	public long getIdCategoria() {
		return idCategoria;
	}

	public void setIdCategoria(long id_categoria) {
		this.idCategoria = id_categoria;
	}

	public long getIdPadre() {
		return idPadre;
	}

	public void setIdPadre(long id_padre) {
		this.idPadre = id_padre;
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

	@Override
	public String toString() {
		return "Categoria [idCategoria=" + idCategoria + ", idPadre=" + idPadre + ", nome=" + nome
				+ ", descrizione=" + descrizione + "]";
	}
}
