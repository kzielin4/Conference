package Zielinski.Kamil.Controler;

import java.sql.SQLException;


import Zielinski.Kamil.Model.Config;
import Zielinski.Kamil.Model.DBConnector;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class DBExporterControler
{
	@FXML
	private Button submitButton;
	@FXML
	private TextField nameFiled;
	@FXML
	private Label idLabel;
	
	public void writeToDB()
	{
		String name = nameFiled.getText();
		if(name.equals(""))
		{
			idLabel.setText("");
			return;
		}
		try
		{
	        int id = Config.getConference().writeToDB(name);
	        submitButton.setDisable(true);
	        idLabel.setText(String.valueOf(id));
		}
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
