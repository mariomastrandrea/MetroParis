package it.polito.tdp.metroparis.model;

import java.util.List;

public class TestModel
{

	public static void main(String[] args)
	{
		MetroParisModel model = new MetroParisModel();
		
		model.creaGrafo();
		
		Fermata p = model.trovaFermata("La Fourche");
		if(p == null)
			System.out.println("Fermata non trovata");
		else
		{
			/*
			List<Fermata> fermateRaggungibili = model.fermateRaggiungibiliDa(p);
			
			System.out.println("Fermata raggiungibili da " + p + ":\n");
			
			for(Fermata f : fermateRaggungibili)
				System.out.println("- "+f);
			 */
		}	
		
		System.out.println();
		
		Fermata a = model.trovaFermata("Temple");
		
		List<Fermata> percorso = model.trovaCammino(p, a);
		
		int count = 1;
		for(Fermata f : percorso)
			System.out.println(count++ +") "+f);
		
	}

}
