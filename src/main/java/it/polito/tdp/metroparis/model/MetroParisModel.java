package it.polito.tdp.metroparis.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.event.ConnectedComponentTraversalEvent;
import org.jgrapht.event.EdgeTraversalEvent;
import org.jgrapht.event.TraversalListener;
import org.jgrapht.event.VertexTraversalEvent;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.traverse.BreadthFirstIterator;

import it.polito.tdp.metroparis.db.MetroDAO;

public class MetroParisModel
{
	private Graph<Fermata, DefaultEdge> grafo;
	private final MetroDAO metroDao;
	private Map<Integer, Fermata> fermateById;
	private Map<Fermata, Fermata> predecessore;

	
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
		}
			>> meglio usare la classe Graphs
		*/
		
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
		
		Set<Connessione> connessioni = this.metroDao.getAllConnessioni(fermateById);
		
		for(Connessione c : connessioni)
		{
			Fermata partenza = c.getStazP();
			Fermata arrivo = c.getStazA();
			this.grafo.addEdge(partenza, arrivo);
		}
		
		System.out.println("Grafo creato!");
		
		////////////////////////////////
		
		/*
		Fermata f = null;
		
		Set<DefaultEdge> archiAdiacenti = this.grafo.edgesOf(f);
		
		for(DefaultEdge e : archiAdiacenti)
		{
			// (essendo un grafo non orientato, non sappiamo se un arco è stato
			//	memorizzato in una direzione o nell'altra
			Fermata f1 = this.grafo.getEdgeSource(e);
			//oppure
			Fermata f2 = this.grafo.getEdgeTarget(e);
					
			if(f1.equals(f))
			{
				//f2 è quello che mi serve
			}
			else 
			{
				//f1 è quello che mi serve
			}
			
			//oppure...
			f1 = Graphs.getOppositeVertex(this.grafo, e, f);		
		}
		
		//oppure, più semplicemente...
		List<Fermata> fermateAdiacenti = Graphs.successorListOf(this.grafo, f);
		*/
		
	}

	public String stampaGrafo()
	{
		return this.grafo.toString();
	}
	
	public Fermata trovaFermata(String nome)
	{
		for(Fermata f : this.grafo.vertexSet())
			if(f.getNome().equals(nome))
				return f;
		
		return null;
	}
	
	public List<Fermata> fermateRaggiungibiliDa(Fermata partenza)
	{
		BreadthFirstIterator<Fermata, DefaultEdge> bfv = new BreadthFirstIterator<>(this.grafo, partenza);
		//DepthFirstIterator<Fermata, DefaultEdge> dfv = new DepthFirstIterator<>(this.grafo, partenza);
		
		this.predecessore = new HashMap<>();
		//this.predecessore.put(partenza, null);
		
		bfv.addTraversalListener(new TraversalListener<Fermata, DefaultEdge>()
		{	
			@Override
			public void vertexTraversed(VertexTraversalEvent<Fermata> e)
			{
				/*
				Fermata nuova = e.getVertex();
				Fermata precedente = vertice adiacente a 'nuova' che sia già raggiunto
						
				predecessore.put(nuova, precedente);
				*/
				//meglio analizzare gli archi attraversati 
			}
			
			@Override
			public void vertexFinished(VertexTraversalEvent<Fermata> e) { }
			
			@Override
			public void edgeTraversed(EdgeTraversalEvent<DefaultEdge> evento) 
			{
				/*
				DefaultEdge arcoAttraversato = evento.getEdge();
				
				Fermata a = grafo.getEdgeSource(arcoAttraversato);
				Fermata b = grafo.getEdgeTarget(arcoAttraversato);
				
				// 1) ho scoperto 'a' arrivando da 'b' (se 'b' lo conoscevo già)
				if(predecessore.containsKey(b) && !predecessore.containsKey(a))
				{
					predecessore.put(a,b);
				}
				else if(predecessore.containsKey(a) && !predecessore.containsKey(b))	// 2) ho scoperto 'b' arrivando da 'a' (se 'a' lo conoscevo già)
				{
					predecessore.put(b,a);
				}
				*/
				//in realtà basta usare il metodo getParent() del BreadthFirstIterator 
				// (ma che non è presente in DepthFirstIterator)
			}
			
			@Override
			public void connectedComponentStarted(ConnectedComponentTraversalEvent e) { }
						
			@Override
			public void connectedComponentFinished(ConnectedComponentTraversalEvent e) { }
		});
		
		List<Fermata> result = new ArrayList<>();
		
		while(bfv.hasNext())
		{
			Fermata f = bfv.next();
			result.add(f);
			
			Fermata parent = bfv.getParent(f);
			this.predecessore.put(f, parent);
		}
		
		return result;
	} 
	
	public List<Fermata> trovaCammino(Fermata partenza, Fermata arrivo)
	{				
		LinkedList<Fermata> result = new LinkedList<>();
		List<Fermata> fermateRaggiungibili = this.fermateRaggiungibiliDa(partenza);
		
		if(!fermateRaggiungibili.contains(arrivo))
			return result;
		
		//parto dall'arrivo e vado a ritroso
		Fermata f = arrivo;
		
		while(f != null) 
		{	
			result.addFirst(f);
			f = this.predecessore.get(f);
		}
		
		return result;
	}
}
