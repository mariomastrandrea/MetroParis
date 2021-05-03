package it.polito.tdp.metroparis.model;

public class Linea 
{
	private int idLinea;
	private String nome;
	private double velocita;
	private double intervallo;

	
	public Linea(int idLinea, String nome, double velocita, double intervallo) 
	{
		this.idLinea = idLinea;
		this.nome = nome;
		this.velocita = velocita;
		this.intervallo = intervallo;
	}

	public int getIdLinea() 
	{
		return this.idLinea;
	}
	
	public String getNome() 
	{
		return this.nome;
	}

	public double getVelocita() 
	{
		return this.velocita;
	}

	public double getIntervallo() 
	{
		return this.intervallo;
	}

	@Override
	public String toString() 
	{
		return this.nome;
	}

	@Override
	public int hashCode() 
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + idLinea;
		return result;
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
		Linea other = (Linea) obj;
		if (idLinea != other.idLinea)
			return false;
		return true;
	}
}
