package Zielinski.Kamil.Controler;

import java.sql.SQLException;
import java.util.Optional;

import Zielinski.Kamil.Model.Config;
import Zielinski.Kamil.Model.DBConnector;
import Zielinski.Kamil.Model.MyLogger;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Dialogs;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;


public class DBExporterControler
{
	@FXML
	private Button submitButton;
	@FXML
	private TextField nameFiled;
	@FXML
	private Label idLabel;
	private Stage stage;
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
	        idLabel.setText(String.valueOf(id));
	        setStage();
	        setInfoDialog("Conference added", "Conference "+name +" was correct added to database with id "+id);
			MyLogger logger = new MyLogger();
			logger.writeInfo("Conference "+name +" was saved to Database correct with id "+id);	
			submitButton.setDisable(true);
		}
		catch (SQLException e)
		{
			
		}
	}
	public void setStage()
	{
		stage = (Stage) submitButton.getScene().getWindow();
	}
	public void setInfoDialog(String message, String detials)
	{
		javafx.scene.control.Alert alert = new javafx.scene.control.Alert(AlertType.INFORMATION);
		alert.setTitle("INFO");
		alert.setHeaderText(message);
		alert.setContentText(detials);
		alert.showAndWait();
		return;
	}
}
