package it.polito.tdp.metroparis.model;

import com.javadocmd.simplelatlng.LatLng;

public class Fermata 
{
	private int idFermata;
	private String nome;
	private LatLng coords;

	
	public Fermata(int idFermata, String nome, LatLng coords) 
	{
		this.idFermata = idFermata;
		this.nome = nome;
		this.coords = coords;
	}

	public int getIdFermata() 
	{
		return this.idFermata;
	}

	public String getNome() 
	{
		return this.nome;
	}

	public LatLng getCoords() 
	{
		return this.coords;
	}

	@Override
	public String toString() 
	{
		return this.nome;
	}

	@Override
	public int hashCode() 
	{
		return ((Integer) idFermata).hashCode();
	}

	@Override
	public boolean equals(Object obj) 
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Fermata other = (Fermata) obj;
		if (idFermata != other.idFermata)
			return false;
		return true;
	}

}
