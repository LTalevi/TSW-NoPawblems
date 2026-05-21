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

	public Categoria(long id_categoria, long id_padre, String nome, String descrizione) {
		super();
		this.idCategoria = id_categoria;
		this.idPadre = id_padre;
		this.nome = nome;
		this.descrizione = descrizione;
	}

	public long getId_categoria() {
		return idCategoria;
	}

	public void setId_categoria(long id_categoria) {
		this.idCategoria = id_categoria;
	}

	public long getId_padre() {
		return idPadre;
	}

	public void setId_padre(long id_padre) {
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
		return "Categoria [id_categoria=" + idCategoria + ", id_padre=" + idPadre + ", nome=" + nome
				+ ", descrizione=" + descrizione + "]";
	}
}
