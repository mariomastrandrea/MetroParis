package it.polito.tdp.metroparis;

import java.net.URL;
import java.util.ResourceBundle;

import it.polito.tdp.metroparis.model.MetroParisModel;
import javafx.fxml.FXML;

public class MetroController 
{
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @SuppressWarnings("unused")
	private MetroParisModel model;
	
    
	public void setModel(MetroParisModel model)
	{
		this.model = model;
	}
	
    @FXML
    void initialize() 
    {

    }
}
