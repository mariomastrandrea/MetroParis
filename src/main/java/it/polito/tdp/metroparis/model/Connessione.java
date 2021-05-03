package it.polito.tdp.metroparis.model;

public class Connessione 
{
	private int idConnessione;
	private Linea linea;
	private Fermata stazPartenza;
	private Fermata stazArrivo;

	
	public Connessione(int idConnessione, Linea linea, Fermata stazPartenza, Fermata stazArrivo) 
	{
		this.idConnessione = idConnessione;
		this.linea = linea;
		this.stazPartenza = stazPartenza;
		this.stazArrivo = stazArrivo;
	}

	public int getIdConnessione() 
	{
		return this.idConnessione;
	}

	public Linea getLinea() 
	{
		return this.linea;
	}

	public Fermata getStazP() 
	{
		return this.stazPartenza;
	}

	public Fermata getStazA() 
	{
		return this.stazArrivo;
	}
	
}
