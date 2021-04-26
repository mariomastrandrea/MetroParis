package it.polito.tdp.metroparis.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import it.polito.tdp.metroparis.db.MetroDAO;

public class MetroParisModel
{
	private Graph<Fermata, DefaultEdge> grafo;
	private MetroDAO metroDao;
	private Map<Integer, Fermata> fermateById;

	
	public MetroParisModel()
	{
		this.metroDao = new MetroDAO();
		this.fermateById = new HashMap<>();
	}
	
	public void creaGrafo() 
	{
		this.grafo = new SimpleGraph<>(DefaultEdge.class);
		List<Fermata> fermate = this.metroDao.getAllFermate();
		
		for(Fermata f : fermate)
			this.fermateById.put(f.getIdFermata(), f);
		
		/*
		for(Fermata fermata : fermate)
		{
			this.grafo.addVertex(fermata);
		}*/
		
		Graphs.addAllVertices(this.grafo, fermate);
		
		//aggiungiamo gli archi
		
		// 1 - soluzione non efficiente (troppe query al DB, alcune persino ripetitive)
		/*
		for(Fermata f1 : this.grafo.vertexSet())
		{
			for(Fermata f2 : this.grafo.vertexSet())
			{
				if(!f1.equals(f2) && metroDao.sonoFermateCollegate(f1,f2))
				{
					this.grafo.addEdge(f1, f2);
				}
			}
		}*/
		
		Set<Connessione> connessioni = this.metroDao.getAllConnessioni(fermateById.values());
		
		for(Connessione c : connessioni)
		{
			Fermata partenza = c.getStazP();
			Fermata arrivo = c.getStazA();
			this.grafo.addEdge(partenza, arrivo);
		}
	}

	public String getGrafo()
	{
		return this.grafo.toString();
	}
}
