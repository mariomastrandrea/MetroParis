package it.polito.tdp.metroparis.model;

public class TestModel
{

	public static void main(String[] args)
	{
		MetroParisModel model = new MetroParisModel();
		
		model.creaGrafo();
		System.out.println(model.getGrafo());
	}

}
