package Zielinski.Kamil.Controler;

import java.sql.SQLException;
import java.util.ArrayList;

import Zielinski.Kamil.Model.Config;
import Zielinski.Kamil.Model.DBConnector;
import Zielinski.Kamil.Model.MailSender;
import Zielinski.Kamil.Model.MyLogger;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class MailSenderControler
{
	@FXML
	private ChoiceBox<String> choiseUserBox;
	@FXML
	private Button sentMailButton;
	@FXML
	private Button returnButton;

	public void initialize()
	{
		addUsers();
	}

	private void addUsers()
	{
		DBConnector con = new DBConnector();
		choiseUserBox.getItems().clear();
		try
		{
			ArrayList<String> listUsers = con.getUsersList();
			int number = 0;
			for (String name : listUsers)
			{
				choiseUserBox.getItems().add(name);
				++number;
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}

	}

	public void quit()
	{
		Stage stage = (Stage) returnButton.getScene().getWindow();
		stage.close();
	}

	public void sent()
	{
		MailSender mailSender = new MailSender();
		if (choiseUserBox.getValue() == null)
			return;
		DBConnector con = new DBConnector();
		try
		{
			String mail = con.getUserMail(choiseUserBox.getValue());
			mailSender.sentMail(mail, Config.getFileName());
			setInfoDialog("Mail sent", "Mail with attachment was correct sent to recipient");
			MyLogger logger = new MyLogger();
			logger.writeInfo("File "+ Config.getFileName()+" was correct sent to "+mail);	
		}
		catch (SQLException e)
		{
			MyLogger logger = new MyLogger();
			logger.writeError("Email of user: "+Config.getUsername()+" is invalid");	
			setErrorDialog("Mail is uncorrect");
		}
	}

	public void setErrorDialog(String message)
	{
		javafx.scene.control.Alert alert = new javafx.scene.control.Alert(AlertType.ERROR);
		alert.setTitle("Error Window");
		alert.setHeaderText(message);
		alert.setContentText("Look in to log file to get more details");
		alert.showAndWait();
		return;
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
