package it.polito.tdp.metroparis.model;

import java.util.List;

public class TestModel
{
	public static void main(String[] args)
	{
		MetroParisModel model = new MetroParisModel();
		model.creaGrafo();
		System.out.println();
		
		Fermata partenza = model.trovaFermata("La Fourche");
		if(partenza == null)
			System.out.println("Fermata non trovata");
		/*
		else
		{		
			List<Fermata> fermateRaggungibili = model.fermateRaggiungibiliDa(p);
			
			System.out.println("Fermata raggiungibili da " + p + ":\n");
			
			for(Fermata f : fermateRaggungibili)
				System.out.println("- "+f);			 
		}*/
				
		Fermata arrivo = model.trovaFermata("Temple");
		
		List<Fermata> percorso = model.trovaCammino(partenza, arrivo);
		
		if(percorso.isEmpty())
			System.out.println("Non esiste nessun percorso da " + partenza + "a " + arrivo);
		else 
			System.out.println("Percorso da '" + partenza + "' a '" + arrivo + "':\n");

		int count = 1;
		
		for(Fermata f : percorso)
			System.out.println((count++) + ") " + f);
	}

}
